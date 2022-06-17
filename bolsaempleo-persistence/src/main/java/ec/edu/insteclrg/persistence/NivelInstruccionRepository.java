package ec.edu.insteclrg.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.NivelInstruccion;

@Repository
public interface NivelInstruccionRepository extends JpaRepository<NivelInstruccion, Long> {

}
