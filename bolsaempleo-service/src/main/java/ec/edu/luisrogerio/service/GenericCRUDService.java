package ec.edu.luisrogerio.service;

import java.util.List;
import java.util.Optional;

public interface GenericCRUDService<ENTITY, TYPE> {

	public void guardar(ENTITY entity);

	public void actualizar(ENTITY entity);

	//public void eliminar(ENTITY entity);

	public List<ENTITY> buscarTodo(ENTITY entity);

	public abstract Optional<ENTITY> buscar(ENTITY entity);
}