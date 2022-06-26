package ec.edu.insteclrg.webapp.view.bean.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.empleador.DatosEmpleador;
import ec.edu.insteclrg.domain.empleador.Oferta;
import ec.edu.insteclrg.dto.PasswordDTO;
import ec.edu.insteclrg.service.crud.UserService;
import ec.edu.insteclrg.service.empleador.DatosEmpleadorService;
import ec.edu.insteclrg.service.empleador.OfertaServicio;
import ec.edu.insteclrg.webapp.enums.MensajeOk;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

@Getter
@Setter
@Component
@Scope("view")
public class EmpleadoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DatosEmpleador> elements;
	private DatosEmpleador selectedElement;
	private List<DatosEmpleador> selectedElements;
	private String password;
	private User user;
	private PasswordDTO passwordDto;
	private String textoBuscar;

	@Autowired
	private DatosEmpleadorService entityService;
	@Autowired
	private UserService userService;
	@Autowired
	OfertaServicio ofertasService;

	@PostConstruct
	public void init() {
		selectedElement = new DatosEmpleador();
		listarToto();
	}

	public void listarToto() {
		elements = entityService.buscarTodo(new DatosEmpleador());
		textoBuscar = "";
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements", ":frm:txtBuscar");
	}

	public void saveElement() {
		user = new User();
		passwordDto = new PasswordDTO();
		user.setUsername(this.selectedElement.getRuc());
		Optional<User> existeUser = userService.buscar(user);
		if (existeUser.isPresent()) {
			try {
				passwordDto.setOldPassword(selectedElement.getUser().getPassword());
				passwordDto.setNewPassword(password);
				userService.actualizarPasswordAdmin(existeUser.get().getUsername(), passwordDto);
				Mensajes.addMsg(MensajesTipo.INFORMACION, "Password de " + selectedElement.getNombreEmpresa() + " "
						+ MensajeOk.ACTUALIZADO_OK.toString());
			} catch (Exception ex) {
				Mensajes.addMsg(MensajesTipo.ERROR, ex.getMessage());
			}
		}
		PrimeFaces.current().executeScript("PF('manageElementDialog').hide()");
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements");
	}

	public boolean hasSelectedElements() {
		return this.selectedElements != null && !this.selectedElements.isEmpty();
	}

	public void buscar() {
		elements = entityService.buscarPorPatron(textoBuscar);
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements");
	}

	public String porCompletar(User user) throws JRException, IOException {
		String completar = "";
		List<Oferta> experiencias = new ArrayList<Oferta>();
		experiencias = ofertasService.buscarPorUsuario(user);
		if (experiencias.size() < 1)
			completar += "<li>Aún no publica ofertas</li>";

		return completar;
	}

	public String foto(byte[] foto) {
		if (foto != null)
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);
		else
			return "";// resources/images/logo_lrg_200.png
	}

}
