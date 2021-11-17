package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.dto.PasswordDTO;
import ec.edu.luisrogerio.service.crud.UserService;
import ec.edu.luisrogerio.webapp.enums.MensajeOk;
import ec.edu.luisrogerio.webapp.enums.MensajesTipo;
import ec.edu.luisrogerio.webapp.utils.Mensajes;
import ec.edu.luisrogerio.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class CambiarClaveBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String passwordRepite;
	private PasswordDTO passwordDto;
	private String username;

	@Autowired
	UserService userService;
	@Autowired
	LoginBean loginBean;
	@Autowired
	HttpServletRequest httpServletRequest;

	@PostConstruct
	public void init() {
		passwordDto = new PasswordDTO();
		username = loginBean.getUser().getUsername();
	}

	public void changePassword() {
		if (!passwordDto.getNewPassword().equals(passwordRepite)) {
			Mensajes.addMsg(MensajesTipo.ERROR, "Contraseña nueva y confirme contraseña no coinciden");
			return;
		}
		try {
			userService.actualizarPassword(username, passwordDto);
			Mensajes.addMsg(MensajesTipo.INFORMACION, MensajeOk.ACTUALIZADO_OK.toString());

			SecurityContextHolder.clearContext();
			httpServletRequest.getSession().invalidate();
			Utils.redirectToPage("/successpassword.xhtml");
		} catch (Exception ex) {
			Mensajes.addMsg(MensajesTipo.ERROR, ex.getMessage());
		}

	}
}
