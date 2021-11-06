package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.common.enums.UserRole;
import ec.edu.luisrogerio.domain.Authority;
import ec.edu.luisrogerio.domain.Ciudad;
import ec.edu.luisrogerio.domain.DatosCandidato;
import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.service.crud.CiudadService;
import ec.edu.luisrogerio.service.crud.DatosCandidatoService;
import ec.edu.luisrogerio.service.crud.ProvinciaService;
import ec.edu.luisrogerio.service.crud.UserService;
import ec.edu.luisrogerio.webapp.enums.MensajesTipo;
import ec.edu.luisrogerio.webapp.utils.Constants;
import ec.edu.luisrogerio.webapp.utils.MensajeError;
import ec.edu.luisrogerio.webapp.utils.MensajeOk;
import ec.edu.luisrogerio.webapp.utils.Mensajes;
import ec.edu.luisrogerio.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class CandidatoRegistroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Provincia> provincias;
	private Provincia selectedProvincia;

	private List<Ciudad> ciudades;
	private Ciudad selectedCiudad;
	private DatosCandidato candidato;
	private String fechaNacimiento;
	private String password;
	private String repitePassword;

	@Autowired
	private ProvinciaService provinciaService;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private DatosCandidatoService candidatoService;

	@Autowired
	private UserService userService;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@PostConstruct
	public void init() {
		this.ciudades = new ArrayList<Ciudad>();
		candidato = new DatosCandidato();
		LocalDate localDate = LocalDate.now();
		candidato.setFechaNacimiento(localDate);
		fechaNacimiento = localDate.toString();
		provincias = this.provinciaService.buscarTodo(new Provincia());
		if (provincias.size() > 0) {
			selectedProvincia = provincias.get(0);
			this.ciudades = ciudadService.buscarPorProvincia(this.selectedProvincia);
			if (ciudades.size() > 0) {
				selectedCiudad = ciudades.get(0);
			}
		}
	}

	public void guardar() {
		if (!password.equals(repitePassword)) {
			Mensajes.addMsg(MensajesTipo.ERROR, "Contraseña y confirme contraseña no coinciden");
			return;
		}
		LocalDate localDate = LocalDate.now();
		candidato.setFechaRegistro(localDate);
		candidato.setCiudad(selectedCiudad);
		// localDate = LocalDate.parse(fechaNacimiento, formatter);
		User user = new User();
		user.setUsername(candidato.getCedula());
		user.setPassword(password);
		user.setEnabled(true);
		Authority aut = new Authority();
		aut.setId(UserRole.ROLE_CANDIDATO.getId());
		aut.setAuthority(UserRole.ROLE_CANDIDATO.toString());
		Set<Authority> auts = new HashSet<Authority>();
		auts.add(aut);
		user.setAuthority(auts);
		try {
			if (userService.guardar(user) != null) {
				candidato.setUser(user);
				if (candidatoService.guardar(candidato) != null) {
					resetEntity();
					Mensajes.addMsg(MensajesTipo.INFORMACION, MensajeOk.GUARDADO_OK.toString());
					Utils.redirectToPage(Constants.URI_WEB_SUCCESS);
				} else {
					Mensajes.addMsg(MensajesTipo.ERROR, MensajeError.NO_GUARDADO.toString());
				}
			} else {
				Mensajes.addMsg(MensajesTipo.ERROR, MensajeError.NO_GUARDADO.toString());
			}
		} catch (Exception e) {
			Mensajes.addMsg(MensajesTipo.ERROR, e.getMessage());
		}

	}

	private void resetEntity() {
		candidato = new DatosCandidato();
		this.password = "";
		this.repitePassword = "";
	}

	public void onProvinciaChange() {
		if (selectedProvincia != null)
			ciudades = ciudadService.buscarPorProvincia(selectedProvincia);
		else
			ciudades.clear();
	}

}
