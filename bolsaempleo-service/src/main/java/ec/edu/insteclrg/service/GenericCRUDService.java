package ec.edu.insteclrg.service;

import java.util.List;
import java.util.Optional;

public interface GenericCRUDService<ENTITY, TYPE> {

	public ENTITY guardar(ENTITY entity);

	public ENTITY actualizar(ENTITY entity);

	public void eliminar(ENTITY entity);

	public List<ENTITY> buscarTodo(ENTITY entity);

	public abstract Optional<ENTITY> buscar(ENTITY entity);
}