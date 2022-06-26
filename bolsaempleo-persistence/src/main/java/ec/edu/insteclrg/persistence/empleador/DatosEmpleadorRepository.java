package ec.edu.insteclrg.persistence.empleador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.empleador.DatosEmpleador;

@Repository
public interface DatosEmpleadorRepository extends JpaRepository<DatosEmpleador, Long> {

	Optional<DatosEmpleador> findByRuc(String ruc);

	@Query("Select t from DatosEmpleador t where t.nombreEmpresa LIKE %:patron% or t.nombreRepresentante LIKE %:patron%  or t.apellidoRepresentante LIKE %:patron% or t.ruc LIKE %:patron%")
	List<DatosEmpleador> findEmployeeByNombreOrApellido(@Param("patron") String patron);

}
