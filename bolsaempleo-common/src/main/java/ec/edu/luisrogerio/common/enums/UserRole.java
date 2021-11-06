package ec.edu.luisrogerio.common.enums;

public enum UserRole {
	ROLE_ADMIN(1L,"Administrador"), 
	ROLE_CANDIDATO(2L,"Candidato"), 
	ROLE_EMPLEADOR(3L,"Empleador");

	private Long id;
	private String description;

	public Long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}

	private UserRole(Long id, String descripcion) {
		this.id=id;
		this.description = descripcion;
	}

	/*@Override
	public String toString() {
		return description.toString();
	}*/
}
