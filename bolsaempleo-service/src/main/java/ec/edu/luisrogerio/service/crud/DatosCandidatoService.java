package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.DatosCandidato;
import ec.edu.luisrogerio.persistence.DatosCandidatoRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

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

}
