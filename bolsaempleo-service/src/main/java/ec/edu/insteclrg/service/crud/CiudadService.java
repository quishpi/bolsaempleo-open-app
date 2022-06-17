package ec.edu.insteclrg.service.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.Ciudad;
import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.persistence.CiudadRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class CiudadService extends GenericCRUDServiceImpl<Ciudad, Long> {

	@Autowired
	private CiudadRepository entityRepository;

	@Override
	public Optional<Ciudad> buscar(Ciudad entity) {
		return entityRepository.findById(entity.getId());
	}

	public List<Ciudad> buscarPorProvincia(Provincia provincia){
		return entityRepository.findByProvincia(provincia);
	}
}
