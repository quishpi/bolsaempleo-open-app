package ec.edu.luisrogerio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ec.edu.luisrogerio.common.AppException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class GenericCRUDServiceImpl<ENTITY, DTO> implements GenericCRUDService<ENTITY, DTO> {

	@Autowired
	private JpaRepository<ENTITY, Long> repository;

	@Override
	public void guardar(DTO dto) {
		Optional<ENTITY> optional = buscar(dto);
		if (optional.isPresent()) {
			throw new AppException(String.format("Ya existe en la base de datos", dto));
		} else {
			ENTITY entity = mapTo(dto);
			repository.save(entity);
		}
	}

	@Override
	public void actualizar(DTO dto) {
		Optional<ENTITY> optional = buscar(dto);
		if (optional.isPresent()) {
			ENTITY entity = mapTo(dto);
			repository.save(entity);
		} else {
			throw new AppException(String.format("No existe en la base de datos", dto));
		}
	}

	/*
	 * @Override public void eliminar(ENTITY dtoObject) { Optional<ENTITY> optional
	 * = buscar(dtoObject); if (optional.isPresent()) {
	 * repository.delete(dtoObject); } else { throw new
	 * AppException(String.format("El objeto %s no existe en base de datos",
	 * dtoObject)); } }
	 */
	@Override
	public List<DTO> buscarTodo(DTO dto) {
		ENTITY domainObject = mapTo(dto);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withIgnorePaths("id");
		List<ENTITY> lstObjs = repository.findAll(Example.of(domainObject, matcher));
		List<DTO> dtoList=lstObjs.stream()
				.map(obj -> build(obj))
				.collect(Collectors.toList());
		if(dtoList.isEmpty())
			throw new AppException(String.format("No existe en la base de datos", dto));
		return dtoList;
	}

	@Override
	public abstract Optional<ENTITY> buscar(DTO dto);

	@Override
	public abstract ENTITY mapTo(DTO dto);

}