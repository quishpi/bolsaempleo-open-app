package ec.edu.insteclrg.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.Capacitacion;
import ec.edu.insteclrg.persistence.candidato.CapacitacionRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class CapacitacionService extends GenericCRUDServiceImpl<Capacitacion, Long> {

	@Autowired
	private CapacitacionRepository entityRepository;

	@Override
	public Optional<Capacitacion> buscar(Capacitacion entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<Capacitacion> buscarPorUsuario(User user) {
		return entityRepository.findByUser(user);
	}

}
