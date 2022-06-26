package ec.edu.insteclrg.persistence.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.candidato.DatosCandidato;

@Repository
public interface DatosCandidatoRepository extends JpaRepository<DatosCandidato, Long> {

	Optional<DatosCandidato> findByCedula(String cedula);

	@Query("Select t from DatosCandidato t where t.nombre LIKE %:patron% or t.apellido LIKE %:patron% or t.cedula LIKE %:patron%")
	List<DatosCandidato> findEmployeeByPatron(@Param("patron") String patron);

}
