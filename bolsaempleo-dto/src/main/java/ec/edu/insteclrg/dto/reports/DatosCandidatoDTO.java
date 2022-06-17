package ec.edu.insteclrg.dto.reports;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DatosCandidatoDTO {

	private String userName;

	private String cedula;

	private String nombre;

	private String apellido;

	private byte[] foto;

	private String ciudad;
	
	private String provincia;

	private String direccion;

	private String telefono;

	private String celular;

	private String email;

	private LocalDate fechaNacimiento;

}
