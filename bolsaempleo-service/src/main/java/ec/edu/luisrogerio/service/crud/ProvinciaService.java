package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.persistence.ProvinciaRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class ProvinciaService extends GenericCRUDServiceImpl<Provincia, Long> {

	@Autowired
	private ProvinciaRepository entityRepository;

	@Override
	public Optional<Provincia> buscar(Provincia domainObject) {
		return entityRepository.findById(domainObject.getId());
	}

}
