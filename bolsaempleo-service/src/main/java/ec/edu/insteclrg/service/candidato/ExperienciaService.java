package ec.edu.insteclrg.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.Experiencia;
import ec.edu.insteclrg.persistence.candidato.ExperienciaRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class ExperienciaService extends GenericCRUDServiceImpl<Experiencia, Long> {

	@Autowired
	private ExperienciaRepository entityRepository;

	@Override
	public Optional<Experiencia> buscar(Experiencia entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<Experiencia> buscarPorUsuario(User user) {
		return entityRepository.findByUser(user);
	}

}
