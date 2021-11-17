package ec.edu.insteclrg.webapp.enums;

public enum MensajeError {
	NO_GUARDADO("No se pudo guardar"), 
	NO_ELIMINADO("No se pudo eliminar"), 
	NO_ENCONTRADO("No encontrado"),
	DUPLICADO("Datos duplicados"),
	ERROR_GENERAL("Error general");

	private String description;

	private MensajeError(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}
}
