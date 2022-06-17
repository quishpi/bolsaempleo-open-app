package ec.edu.insteclrg.persistence.candidato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.ReferenciaPersonal;

@Repository
public interface ReferenciaPersonalRepository extends JpaRepository<ReferenciaPersonal, Long> {

	List<ReferenciaPersonal> findByUser(User user);
}
