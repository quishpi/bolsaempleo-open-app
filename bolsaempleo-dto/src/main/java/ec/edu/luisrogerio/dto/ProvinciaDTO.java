package ec.edu.luisrogerio.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProvinciaDTO {

	private Long id;
	
	@NotEmpty
	@Size(max = 50)
	private String nombre;
}
