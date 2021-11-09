package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.NivelInstruccion;
import ec.edu.luisrogerio.persistence.NivelInstruccionRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class NivelInstruccionService extends GenericCRUDServiceImpl<NivelInstruccion, Long> {

	@Autowired
	private NivelInstruccionRepository entityRepository;

	@Override
	public Optional<NivelInstruccion> buscar(NivelInstruccion entity) {
		return entityRepository.findById(entity.getId());
	}

}
