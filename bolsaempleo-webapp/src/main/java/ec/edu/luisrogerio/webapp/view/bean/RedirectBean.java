package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.domain.Authority;
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.webapp.handler.AuthenticationHandler;
import ec.edu.luisrogerio.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class RedirectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	AuthenticationHandler authenticationHandler;
	private Authority authority;
	private User user;

	@PostConstruct
	public void init() {
		authenticationHandler = new AuthenticationHandler();
		user = authenticationHandler.getUser();
		if (user.getAuthority().size() == 1) {
			// Tiene un solo ROLE
			Iterator<Authority> iter = user.getAuthority().iterator();
			authority = new Authority();
			authority = (Authority) iter.next();
		} else // Tiene mas de 1 ROLE
			Utils.redirectToPage("/login.aspx"); // debe redireccionar para escoger el escritorio
	}

	public void redirect() {
		if (authority.getAuthority().equals(UserRole.ROLE_CANDIDATO.toString())) {
			Utils.redirectToPage("/wp-admin/candidato/index.xhtml");
		} else if (authority.getAuthority().equals(UserRole.ROLE_EMPLEADOR.toString())) {
			Utils.redirectToPage("/wp-admin/empleador/index.xhtml");
		} else if (authority.getAuthority().equals(UserRole.ROLE_ADMIN.toString())) {
			Utils.redirectToPage("/wp-admin/admin/index.xhtml");
		} else
			Utils.redirectToPage("/login.xhtml");

	}
}
