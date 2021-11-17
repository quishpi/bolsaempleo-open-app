package ec.edu.insteclrg.persistence.candidato;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.candidato.DatosCandidato;

@Repository
public interface DatosCandidatoRepository extends JpaRepository<DatosCandidato, Long> {
	
	Optional<DatosCandidato> findByCedula(String cedula);
}
