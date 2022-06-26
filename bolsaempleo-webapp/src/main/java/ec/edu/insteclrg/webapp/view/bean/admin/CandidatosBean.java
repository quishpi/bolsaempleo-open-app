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
import ec.edu.insteclrg.domain.candidato.Capacitacion;
import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.domain.candidato.Experiencia;
import ec.edu.insteclrg.domain.candidato.Instruccion;
import ec.edu.insteclrg.domain.candidato.ReferenciaPersonal;
import ec.edu.insteclrg.domain.candidato.ReferenciaProfesional;
import ec.edu.insteclrg.dto.PasswordDTO;
import ec.edu.insteclrg.service.candidato.CapacitacionService;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.candidato.ExperienciaService;
import ec.edu.insteclrg.service.candidato.InstruccionService;
import ec.edu.insteclrg.service.candidato.ReferenciaPersonalService;
import ec.edu.insteclrg.service.candidato.ReferenciaProfesionalService;
import ec.edu.insteclrg.service.crud.UserService;
import ec.edu.insteclrg.webapp.enums.MensajeOk;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import ec.edu.insteclrg.webapp.view.bean.candidato.CvBean;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

@Getter
@Setter
@Component
@Scope("view")
public class CandidatosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DatosCandidato> elements;
	private DatosCandidato selectedElement;
	private List<DatosCandidato> selectedElements;
	private String password;
	private User user;
	private PasswordDTO passwordDto;
	private String textoBuscar;

	private List<DatosCandidato> filteredElements;

	@Autowired
	private DatosCandidatoService entityService;
	@Autowired
	private UserService userService;
	@Autowired
	private CvBean cvBean;

	@Autowired
	ExperienciaService experienciaService;
	@Autowired
	InstruccionService instruccionService;
	@Autowired
	CapacitacionService capacitacionService;
	@Autowired
	ReferenciaPersonalService refPersonalService;
	@Autowired
	ReferenciaProfesionalService refProfesionalService;

	@PostConstruct
	public void init() {
		selectedElement = new DatosCandidato();
		filteredElements = new ArrayList<DatosCandidato>();
		listarToto();
	}

	public void listarToto() {
		elements = entityService.buscarTodo(new DatosCandidato());
		textoBuscar = "";
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements", ":frm:txtBuscar");
	}

	public void saveElement() {
		user = new User();
		passwordDto = new PasswordDTO();
		user.setUsername(this.selectedElement.getCedula());
		Optional<User> existeUser = userService.buscar(user);
		if (existeUser.isPresent()) {
			try {
				passwordDto.setOldPassword(selectedElement.getUser().getPassword());
				passwordDto.setNewPassword(password);
				userService.actualizarPasswordAdmin(existeUser.get().getUsername(), passwordDto);
				Mensajes.addMsg(MensajesTipo.INFORMACION, "Password de " + selectedElement.getApellido() + " "
						+ selectedElement.getNombre() + " " + MensajeOk.ACTUALIZADO_OK.toString());
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

	public void openPdf(String username) throws JRException, IOException {
		cvBean.openPdf(username);
	}

	public void buscar() {
		elements = entityService.buscarPorPatron(textoBuscar);
		this.filteredElements = new ArrayList<DatosCandidato>();
		this.filteredElements.addAll(this.elements);
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements");
	}

	/*
	 * public boolean globalFilterFunction(Object value, Object filter, Locale
	 * locale) { String filterText = (filter == null) ? null :
	 * filter.toString().trim().toLowerCase(); if
	 * (LangUtils.isValueBlank(filterText)) { return true; } DatosCandidato customer
	 * = (DatosCandidato) value; return
	 * customer.getNombre().toLowerCase().contains(filterText) ||
	 * customer.getApellido().toLowerCase().contains(filterText); }
	 */
	public String porCompletar(User user) throws JRException, IOException {
		String completar = "";
		List<Experiencia> experiencias = new ArrayList<Experiencia>();
		experiencias = experienciaService.buscarPorUsuario(user);
		if (experiencias.size() < 1)
			completar += "<li>Experiencia</li>";

		List<Instruccion> instrucciones = new ArrayList<Instruccion>();
		instrucciones = instruccionService.buscarPorUsuario(user);
		if (instrucciones.size() < 1)
			completar += "<li>Instrucción</li>";

		List<Capacitacion> capacitaciones = new ArrayList<Capacitacion>();
		capacitaciones = capacitacionService.buscarPorUsuario(user);
		if (capacitaciones.size() < 1)
			completar += "<li>Experiencia</li>";

		List<ReferenciaPersonal> refPersonales = new ArrayList<ReferenciaPersonal>();
		refPersonales = refPersonalService.buscarPorUsuario(user);
		if (refPersonales.size() < 1)
			completar += "<li>Ref Personal</li>";

		List<ReferenciaProfesional> refProfesionales = new ArrayList<ReferenciaProfesional>();
		refProfesionales = refProfesionalService.buscarPorUsuario(user);
		if (refProfesionales.size() < 1)
			completar += "<li>Ref Profesional</li>";

		return completar;
	}

	public String foto(byte[] foto) {
		if (foto != null)
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);
		else
			return "";// resources/images/logo_lrg_200.png
	}

}
