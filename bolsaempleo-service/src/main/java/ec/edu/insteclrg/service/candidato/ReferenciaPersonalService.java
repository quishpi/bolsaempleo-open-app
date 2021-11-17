package ec.edu.insteclrg.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.ReferenciaPersonal;
import ec.edu.insteclrg.persistence.candidato.ReferenciaPersonalRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class ReferenciaPersonalService extends GenericCRUDServiceImpl<ReferenciaPersonal, Long> {

	@Autowired
	private ReferenciaPersonalRepository entityRepository;

	@Override
	public Optional<ReferenciaPersonal> buscar(ReferenciaPersonal entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<ReferenciaPersonal> buscarPorUsuario(User user) {
		return entityRepository.findByUser(user);
	}

}
