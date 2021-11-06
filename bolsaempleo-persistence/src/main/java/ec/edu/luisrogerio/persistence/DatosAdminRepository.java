package ec.edu.luisrogerio.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.luisrogerio.domain.DatosAdmin;


@Repository
public interface DatosAdminRepository extends JpaRepository<DatosAdmin, Long> {
	
	Optional<DatosAdmin> findByCedula(String cedula);
}
