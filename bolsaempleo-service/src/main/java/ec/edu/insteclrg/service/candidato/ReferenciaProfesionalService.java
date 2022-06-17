package ec.edu.insteclrg.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.ReferenciaProfesional;
import ec.edu.insteclrg.persistence.candidato.ReferenciaProfesionalRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class ReferenciaProfesionalService extends GenericCRUDServiceImpl<ReferenciaProfesional, Long> {

	@Autowired
	private ReferenciaProfesionalRepository entityRepository;

	@Override
	public Optional<ReferenciaProfesional> buscar(ReferenciaProfesional entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<ReferenciaProfesional> buscarPorUsuario(User user) {
		return entityRepository.findByUser(user);
	}

}
