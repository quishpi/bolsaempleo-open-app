package ec.edu.luisrogerio.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CiudadDTO {

	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	private String nombre;
	
	@NotEmpty
	private Long provincia_id;
}
