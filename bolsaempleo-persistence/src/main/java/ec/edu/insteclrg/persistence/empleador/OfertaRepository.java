package ec.edu.insteclrg.persistence.empleador;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.empleador.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

	List<Oferta> findByUser(User user);

	@Query("Select t from Oferta t where t.fechaLimiteAplicacion >=:date")
	List<Oferta> findByFechaLimiteAplicacionGreater(@Param("date") LocalDate date);

	@Query("Select t from Oferta t where t.fechaLimiteAplicacion <:date")
	List<Oferta> findByFechaLimiteAplicacionLest(@Param("date") LocalDate date);

	@Query("Select t from Oferta t where t.tituloOferta LIKE %:patron% or t.tipoContrato LIKE %:patron%")
	List<Oferta> findEmployeeByNombreOrApellido(@Param("patron") String patron);
}
