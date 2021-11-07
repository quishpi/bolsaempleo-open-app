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
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.service.candidato.DatosCandidatoService;
import ec.edu.luisrogerio.service.empleador.DatosEmpleadorService;
import ec.edu.luisrogerio.webapp.handler.AuthenticationHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer time;
	private String roleName;
	private String fullName;
	private String menu;
	private User user;

	AuthenticationHandler authenticationHandler = new AuthenticationHandler();

	@Autowired
	DatosCandidatoService candidatoService;

	@Autowired
	DatosEmpleadorService empleadorService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	RedirectBean redirectBean;

	@PostConstruct
	public void init() {
		fullName = authenticationHandler.getFullName();
		user = authenticationHandler.getUser();

		time = 1800;
		HttpSession session = httpServletRequest.getSession();
		if (session.getMaxInactiveInterval() > 1800) {
			session.setMaxInactiveInterval(time);
		}
		roleName = redirectBean.getAuthority().getAuthority().split("_")[1];
		if (redirectBean.getAuthority().getAuthority().equals(UserRole.ROLE_CANDIDATO.toString()))
			menu = "menu/mnu-candidato.xhtml";
		else if (redirectBean.getAuthority().getAuthority().equals(UserRole.ROLE_EMPLEADOR.toString()))
			menu = "menu/mnu-empleador.xhtml";
		else if (redirectBean.getAuthority().getAuthority().equals(UserRole.ROLE_ADMIN.toString()))
			menu = "menu/mnu-admin.xhtml";

		System.err.println(">>" + user.getUsername());
		System.err.println(">>" + roleName);
		System.err.println(">>" + fullName);
		System.err.println(">>" + new Date());
		System.err.println(">>" + session.getMaxInactiveInterval());

	}

}
