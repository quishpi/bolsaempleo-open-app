package ec.edu.insteclrg.webapp.view.bean;

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

import ec.edu.insteclrg.common.enums.UserRole;
import ec.edu.insteclrg.domain.Authority;
import ec.edu.insteclrg.domain.Ciudad;
import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.admin.DatosAdmin;
import ec.edu.insteclrg.service.admin.DatosAdminService;
import ec.edu.insteclrg.service.crud.CiudadService;
import ec.edu.insteclrg.service.crud.ProvinciaService;
import ec.edu.insteclrg.service.crud.UserService;
import ec.edu.insteclrg.webapp.enums.MensajeError;
import ec.edu.insteclrg.webapp.enums.MensajeOk;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Constants;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import ec.edu.insteclrg.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class AdminRegistroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Provincia> provincias;
	private Provincia selectedProvincia;

	private List<Ciudad> ciudades;
	private Ciudad selectedCiudad;

	private DatosAdmin admin;

	private String password;
	private String repitePassword;

	@Autowired
	private ProvinciaService provinciaService;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private DatosAdminService adminService;

	@Autowired
	private UserService userService;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@PostConstruct
	public void init() {
		List<DatosAdmin> admins = new ArrayList<DatosAdmin>();
		admins = adminService.buscarTodo(new DatosAdmin());
		if (!admins.isEmpty())
			Utils.redirectToPage(Constants.URI_WEB_LOGIN);

		this.ciudades = new ArrayList<Ciudad>();
		admin = new DatosAdmin();
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
		admin.setFechaRegistro(localDate);
		admin.setCiudad(selectedCiudad);
		User user = new User();
		user.setUsername(admin.getEmail());
		user.setPassword(password);
		user.setEnabled(true);
		Authority aut = new Authority();
		aut.setId(UserRole.ROLE_ADMIN.getId());
		aut.setAuthority(UserRole.ROLE_ADMIN.toString());
		Set<Authority> auts = new HashSet<Authority>();
		auts.add(aut);
		user.setAuthority(auts);
		try {
			if (userService.guardar(user) != null) {
				admin.setUser(user);
				if (adminService.guardar(admin) != null) {
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
		admin = new DatosAdmin();
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
