package ec.edu.insteclrg.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordDTO {

	@NotEmpty
	private String oldPassword;

	@NotEmpty
	private String newPassword;
}
