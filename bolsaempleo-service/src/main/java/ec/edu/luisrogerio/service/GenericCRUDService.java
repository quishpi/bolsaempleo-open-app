package ec.edu.luisrogerio.service;

import java.util.List;
import java.util.Optional;

public interface GenericCRUDService<ENTITY, DTO> {

	public void guardar(DTO dto);

	public void actualizar(Long id, DTO dto);

	//public void eliminar(ENTITY entity);

	public List<DTO> buscarTodo(DTO dto);

	public abstract Optional<ENTITY> buscar(Long id, DTO dto);
	
	public ENTITY mapTo(DTO dto);
	
	public DTO build(ENTITY entity);

}