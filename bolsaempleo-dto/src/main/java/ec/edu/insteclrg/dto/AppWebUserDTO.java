package ec.edu.insteclrg.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppWebUserDTO extends User {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String fullName;
	private byte[] foto;

	public AppWebUserDTO(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			Long id, String fullName, byte[] foto) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.id = id;
		this.fullName = fullName;
		this.foto=foto;

	}

}
