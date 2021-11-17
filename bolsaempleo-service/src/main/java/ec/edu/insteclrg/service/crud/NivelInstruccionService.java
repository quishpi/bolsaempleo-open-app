package ec.edu.insteclrg.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.NivelInstruccion;
import ec.edu.insteclrg.persistence.NivelInstruccionRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class NivelInstruccionService extends GenericCRUDServiceImpl<NivelInstruccion, Long> {

	@Autowired
	private NivelInstruccionRepository entityRepository;

	@Override
	public Optional<NivelInstruccion> buscar(NivelInstruccion entity) {
		return entityRepository.findById(entity.getId());
	}

}
