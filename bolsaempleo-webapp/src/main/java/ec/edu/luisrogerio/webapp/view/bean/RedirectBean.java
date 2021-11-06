package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.common.enums.UserRole;
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

	@PostConstruct
	public void init() {
		authenticationHandler = new AuthenticationHandler();
	}

	public void redirect() {
		if (authenticationHandler.getAuthority().equals(UserRole.ROLE_CANDIDATO.toString())) {
			Utils.redirectToPage("/wp-admin/candidato/index.xhtml");
		} else if (authenticationHandler.getAuthority().equals(UserRole.ROLE_EMPLEADOR.toString())) {
			Utils.redirectToPage("/wp-admin/empleador/index.xhtml");
		} else if (authenticationHandler.getAuthority().equals(UserRole.ROLE_ADMIN.toString())) {
			Utils.redirectToPage("/wp-admin/admin/index.xhtml");
		} else
			Utils.redirectToPage("/login.xhtml");

	}
}
