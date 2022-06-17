package ec.edu.insteclrg.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.Ciudad;
import ec.edu.insteclrg.domain.Provincia;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
	
	List<Ciudad> findByProvincia(Provincia provincia);
}
