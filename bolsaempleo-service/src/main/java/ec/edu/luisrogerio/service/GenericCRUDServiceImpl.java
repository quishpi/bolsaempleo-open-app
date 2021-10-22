package ec.edu.luisrogerio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.common.AppException;

import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericCRUDServiceImpl<ENTITY, TYPE> implements GenericCRUDService<ENTITY, TYPE> {

	@Autowired
	private JpaRepository<ENTITY, TYPE> repository;

	@Override
	public ENTITY guardar(ENTITY dtoObject) {
		Optional<ENTITY> optional = buscar(dtoObject);
		if (optional.isPresent()) {
			throw new AppException(String.format("El objeto %s ya existe en base de datos", dtoObject));
		} else {
			return repository.save(dtoObject);
		}
	}

	@Override
	public ENTITY actualizar(ENTITY dtoObject) {
		Optional<ENTITY> optional = buscar(dtoObject);
		if (optional.isPresent()) {
			return repository.save(dtoObject);
		} else {
			throw new AppException(String.format("El objeto %s no existe en base de datos", dtoObject));
		}
	}

	@Override
	public void eliminar(ENTITY dtoObject) {
		Optional<ENTITY> optional = buscar(dtoObject);
		if (optional.isPresent()) {
			repository.delete(dtoObject);
		} else {
			throw new AppException(String.format("El objeto %s no existe en base de datos", dtoObject));
		}
	}

	@Override
	public List<ENTITY> buscarTodo() {
		List<ENTITY> lstObjs = repository.findAll();
		return lstObjs;
	}

	@Override
	public abstract Optional<ENTITY> buscar(ENTITY domainObject);

}