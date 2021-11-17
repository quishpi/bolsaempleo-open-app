package ec.edu.luisrogerio.webapp.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.domain.Authority;
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.dto.AppWebUserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationHandler {

	private User user;
	private String fullName;
	private byte[] foto;

	public AuthenticationHandler() {
		user = new User();
		/*
		 * Object principal =
		 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
		 * (principal instanceof UserDetails) { username = ((UserDetails)
		 * principal).getUsername(); } else { username = principal.toString(); }
		 */

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			AppWebUserDTO appWebUser = (AppWebUserDTO) authentication.getPrincipal();
			user.setId(appWebUser.getId());
			user.setUsername(appWebUser.getUsername());

			fullName = appWebUser.getFullName();
			foto = appWebUser.getFoto();
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			List<Authority> listAuthorities = new ArrayList<Authority>();
			for (GrantedAuthority gAut : authorities) {
				Authority aut = new Authority();
				aut.setId(UserRole.valueOf(gAut.getAuthority()).getId());
				aut.setAuthority(UserRole.valueOf(gAut.getAuthority()).toString());
				listAuthorities.add(aut);
			}
			user.setAuthority(new HashSet<Authority>(listAuthorities));
		}
	}
}
