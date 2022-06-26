package ec.edu.insteclrg.service.empleador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.domain.empleador.DatosEmpleador;
import ec.edu.insteclrg.persistence.empleador.DatosEmpleadorRepository;
import ec.edu.insteclrg.service.GenericCRUDServiceImpl;

@Service
public class DatosEmpleadorService extends GenericCRUDServiceImpl<DatosEmpleador, Long> {

	@Autowired
	private DatosEmpleadorRepository entityRepository;

	@Override
	public Optional<DatosEmpleador> buscar(DatosEmpleador entity) {
		return entityRepository.findById(entity.getId());
	}

	public Optional<DatosEmpleador> buscarPorRuc(String ruc) {
		return entityRepository.findByRuc(ruc);
	}
	public List<DatosEmpleador> buscarPorPatron(String patron) {
		return entityRepository.findEmployeeByNombreOrApellido(patron);
	}
}
