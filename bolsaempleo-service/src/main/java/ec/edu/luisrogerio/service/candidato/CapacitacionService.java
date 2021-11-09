package ec.edu.luisrogerio.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.domain.candidato.Capacitacion;
import ec.edu.luisrogerio.persistence.candidato.CapacitacionRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

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
