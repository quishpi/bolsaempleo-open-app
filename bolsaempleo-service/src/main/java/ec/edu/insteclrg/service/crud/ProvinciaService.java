package ec.edu.insteclrg.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.persistence.ProvinciaRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class ProvinciaService extends GenericCRUDServiceImpl<Provincia, Long> {

	@Autowired
	private ProvinciaRepository entityRepository;

	@Override
	public Optional<Provincia> buscar(Provincia entity) {
		return entityRepository.findById(entity.getId());
	}


}
