package ec.edu.luisrogerio.webapp.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.dto.AppWebUser;
import ec.edu.luisrogerio.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationHandler {

	private String username;
	private Integer time;
	private String authority;
	private String roleName;
	private String fullName;

	public AuthenticationHandler() {
		/*
		 * Object principal =
		 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
		 * (principal instanceof UserDetails) { username = ((UserDetails)
		 * principal).getUsername(); } else { username = principal.toString(); }
		 */

		List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			AppWebUser appWebUser = (AppWebUser) authentication.getPrincipal();
			username = appWebUser.getUsername();
			fullName = appWebUser.getFullName();
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			listAuthorities.addAll(authorities);
		}
		if (!listAuthorities.isEmpty()) {
			authority = listAuthorities.get(0).getAuthority();
			roleName = UserRole.valueOf(listAuthorities.get(0).getAuthority()).getDescription();
		} else
			Utils.redirectToPage("/login.xhtml");
	}
}
