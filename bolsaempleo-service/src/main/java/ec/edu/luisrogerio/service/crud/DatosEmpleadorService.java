package ec.edu.luisrogerio.service.crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.domain.DatosEmpleador;
import ec.edu.luisrogerio.persistence.DatosEmpleadorRepository;
import ec.edu.luisrogerio.service.GenericCRUDServiceImpl;

@Service
public class DatosEmpleadorService extends GenericCRUDServiceImpl<DatosEmpleador, Long> {

	@Autowired
	private DatosEmpleadorRepository entityRepository;

	@Override
	public Optional<DatosEmpleador> buscar(DatosEmpleador entity) {
		return entityRepository.findById(entity.getId());
	}

}
