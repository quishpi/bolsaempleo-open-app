package ec.edu.luisrogerio.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponseDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;

	@NotNull
	private T result;

}