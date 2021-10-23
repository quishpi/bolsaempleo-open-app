package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.common.AppException;
import ec.edu.luisrogerio.domain.Ciudad;
import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.dto.CiudadDTO;
import ec.edu.luisrogerio.persistence.CiudadRepository;
import ec.edu.luisrogerio.persistence.ProvinciaRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class CiudadService extends GenericCRUDServiceImpl<Ciudad, CiudadDTO> {

	@Autowired
	private CiudadRepository entityRepository;
	
	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Override
	public Optional<Ciudad> buscar(Long id, CiudadDTO dto) {
		return entityRepository.findById(id);
	}

	@Override
	public Ciudad mapTo(CiudadDTO dto) {
		if(dto.getId()==null) return new Ciudad();
		Ciudad entity = new Ciudad();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		Optional<Provincia> provincia = Optional.ofNullable(new Provincia());
		provincia =provinciaRepository.findById(dto.getProvincia_id());
		if(!provincia.isPresent())
			throw new AppException(String.format("No existe la provincia en la base de datos", dto));
		entity.setProvincia(provincia.get());
		return entity;
	}

	@Override
	public CiudadDTO build(Ciudad entity) {
		CiudadDTO dto = new CiudadDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setProvincia_id(entity.getProvincia().getId());
		return dto;
	}

}
