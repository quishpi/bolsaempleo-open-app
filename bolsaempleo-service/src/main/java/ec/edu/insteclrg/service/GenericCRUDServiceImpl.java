package ec.edu.insteclrg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ec.edu.insteclrg.common.AppException;

import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericCRUDServiceImpl<ENTITY, TYPE> implements GenericCRUDService<ENTITY, TYPE> {

	@Autowired
	private JpaRepository<ENTITY, Long> repository;

	@Override
	public ENTITY guardar(ENTITY entity) {
		Optional<ENTITY> optional = buscar(entity);
		if (optional.isPresent()) {
			throw new AppException(String.format("Datos ya registrados en el sistema", entity));
		} else {
			return repository.save(entity);
		}
	}

	@Override
	public ENTITY actualizar(ENTITY entity) {
		Optional<ENTITY> optional = buscar(entity);
		if (optional.isPresent()) {
			return repository.save(entity);
		} else {
			throw new AppException(String.format("Registro no existe en la base de datos", entity));
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
	public List<ENTITY> buscarTodo(ENTITY entity) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("id");
		List<ENTITY> lstObjs = repository.findAll(Example.of(entity, matcher));
		//if (lstObjs.isEmpty())
		//	throw new AppException(String.format("Registro no existe en la base de datos", entity));
		return lstObjs;
	}

}