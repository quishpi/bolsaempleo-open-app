package ec.edu.luisrogerio.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.luisrogerio.domain.NivelInstruccion;

@Repository
public interface NivelInstruccionRepository extends JpaRepository<NivelInstruccion, Long> {

}
