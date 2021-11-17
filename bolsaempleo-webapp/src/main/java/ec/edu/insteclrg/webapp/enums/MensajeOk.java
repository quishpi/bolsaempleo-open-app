package ec.edu.insteclrg.webapp.enums;

public enum MensajeOk {
	GUARDADO_OK("Guardado correctamente"), 
	ACTUALIZADO_OK("Actualizado correctamente"), 
	ELIMINADO_OK("Eliminado Correctamente");

	private String description;

	private MensajeOk(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}
}
