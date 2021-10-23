package ec.edu.luisrogerio.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Data
@ToString(exclude = "password")
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(min = 6, max = 20)
	private String username;

	@NotEmpty
	@Size(min = 6, max = 15)
	private String password;

	@Email
	private String email;

	private String role;

	private boolean active;
}