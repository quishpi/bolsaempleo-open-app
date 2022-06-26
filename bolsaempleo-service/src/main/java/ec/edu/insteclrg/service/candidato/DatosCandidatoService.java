package ec.edu.insteclrg.service.candidato;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.persistence.candidato.DatosCandidatoRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class DatosCandidatoService extends GenericCRUDServiceImpl<DatosCandidato, Long> {

	@Autowired
	private DatosCandidatoRepository entityRepository;

	@Override
	public Optional<DatosCandidato> buscar(DatosCandidato entity) {
		return entityRepository.findById(entity.getId());
	}

	public Optional<DatosCandidato> buscarPorCedula(String cedula) {
		return entityRepository.findByCedula(cedula);
	}

	public List<DatosCandidato> buscarPorPatron(String patron) {
		return entityRepository.findEmployeeByPatron(patron);
	}

}
