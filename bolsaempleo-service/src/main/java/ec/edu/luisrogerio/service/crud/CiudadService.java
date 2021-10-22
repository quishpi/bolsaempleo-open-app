package ec.edu.luisrogerio.service.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.Ciudad;
import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.persistence.CiudadRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class CiudadService extends GenericCRUDServiceImpl<Ciudad, Long> {

	@Autowired
	private CiudadRepository entityRepository;

	@Override
	public Optional<Ciudad> buscar(Ciudad domainObject) {
		return entityRepository.findById(domainObject.getId());
	}

	public List<Ciudad> buscarPorProvincia(Provincia provincia) {
		return entityRepository.findByProvincia(provincia);
	}
}
