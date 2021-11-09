package ec.edu.luisrogerio.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.domain.candidato.Instruccion;
import ec.edu.luisrogerio.persistence.candidato.InstruccionRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class InstruccionService extends GenericCRUDServiceImpl<Instruccion, Long> {

	@Autowired
	private InstruccionRepository entityRepository;

	@Override
	public Optional<Instruccion> buscar(Instruccion entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<Instruccion> buscarPorUsuario(User user) {
		return entityRepository.findByUser(user);
	}

}
