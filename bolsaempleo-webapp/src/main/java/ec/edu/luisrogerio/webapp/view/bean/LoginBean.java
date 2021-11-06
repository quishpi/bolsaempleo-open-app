package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.service.crud.DatosCandidatoService;
import ec.edu.luisrogerio.service.crud.DatosEmpleadorService;
import ec.edu.luisrogerio.webapp.handler.AuthenticationHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private Integer time;
	private String authority;
	private String roleName;
	private String fullName;
	private String menu;

	AuthenticationHandler authenticationHandler = new AuthenticationHandler();

	@Autowired
	DatosCandidatoService candidatoService;

	@Autowired
	DatosEmpleadorService empleadorService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@PostConstruct
	public void init() {
		username = authenticationHandler.getUsername();
		roleName = authenticationHandler.getAuthority().split("_")[1];
		fullName = authenticationHandler.getFullName();

		time = 1800;
		HttpSession session = httpServletRequest.getSession();
		if (session.getMaxInactiveInterval() > 1800) {
			session.setMaxInactiveInterval(time);
		}
		System.err.println(">>" + username);
		System.err.println(">>" + fullName);
		System.err.println(">>" + new Date());
		System.err.println(">>" + session.getMaxInactiveInterval());

		if (authenticationHandler.getAuthority().equals(UserRole.ROLE_CANDIDATO.toString())) 
			menu = "menu/mnu-candidato.xhtml";
		else if (authenticationHandler.getAuthority().equals(UserRole.ROLE_EMPLEADOR.toString())) 
			menu = "menu/mnu-empleador.xhtml";
		else if (authenticationHandler.getAuthority().equals(UserRole.ROLE_ADMIN.toString())) 
			menu = "menu/mnu-admin.xhtml";
		
	}

}
