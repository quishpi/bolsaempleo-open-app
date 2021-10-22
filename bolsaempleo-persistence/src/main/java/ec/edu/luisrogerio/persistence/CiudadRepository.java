package ec.edu.luisrogerio.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.luisrogerio.domain.Ciudad;
import ec.edu.luisrogerio.domain.Provincia;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
	
	List<Ciudad> findByProvincia(Provincia provincia);
}
