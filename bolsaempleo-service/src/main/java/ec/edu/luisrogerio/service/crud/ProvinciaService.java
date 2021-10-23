package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.dto.ProvinciaDTO;
import ec.edu.luisrogerio.persistence.ProvinciaRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class ProvinciaService extends GenericCRUDServiceImpl<Provincia, ProvinciaDTO> {

	@Autowired
	private ProvinciaRepository entityRepository;

	@Override
	public Optional<Provincia> buscar(Long id, ProvinciaDTO dto) {
		return entityRepository.findById(id);
	}

	@Override
	public Provincia mapTo(ProvinciaDTO dto) {
		Provincia provincia = new Provincia();
		provincia.setId(dto.getId());
		provincia.setNombre(dto.getNombre());
		return provincia;
	}

	@Override
	public ProvinciaDTO build(Provincia entity) {
		ProvinciaDTO dto = new ProvinciaDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		return dto;
	}

}
