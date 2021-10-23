package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.domain.Ciudad;
import ec.edu.luisrogerio.domain.DatosCandidato;
import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.service.crud.CiudadService;
import ec.edu.luisrogerio.service.crud.DatosCandidatoService;
import ec.edu.luisrogerio.service.crud.ProvinciaService;
import ec.edu.luisrogerio.service.crud.UserService;
import ec.edu.luisrogerio.webapp.enums.MensajesTipo;
import ec.edu.luisrogerio.webapp.utils.Mensajes;
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
		candidato.setCedula("0301584173");
		candidato.setNombre("Luis");
		candidato.setApellido("Quishpi");
		LocalDate localDate = LocalDate.now();
		candidato.setFechaNacimiento(localDate);
		candidato.setEmail("luis.quishpi@outlook.com");
		fechaNacimiento = localDate.toString();
		provincias = this.provinciaService.buscarTodo();
		if (provincias.size() > 0) {
			selectedProvincia = provincias.get(0);
			this.ciudades = ciudadService.buscarPorProvincia(this.selectedProvincia);
			if (ciudades.size() > 0) {
				selectedCiudad = ciudades.get(0);
			}
		}
	}

	public void guardar() {
		LocalDate localDate = LocalDate.now();
		candidato.setFechaRegistro(localDate);
		candidato.setCiudad(selectedCiudad);
		localDate = LocalDate.parse(fechaNacimiento, formatter);
		User user = new User();
		user.setUsername(candidato.getCedula());
		user.setPassword(password);
		user.setRole("CANDIDATO");
		user.setActive(true);
		if (userService.guardar(user) != null) {
			candidato.setUser(user);
			if (candidatoService.guardar(candidato) != null) {
				Mensajes.addMsg(MensajesTipo.INFORMACION, "Guardado correctamente");
			} else {
				Mensajes.addMsg(MensajesTipo.ERROR, "No se pudo guardar. Revise los datos");
			}
		} else {
			Mensajes.addMsg(MensajesTipo.ERROR, "No se pudo guardar. Revise los datos");
		}

	}

	public void onProvinciaChange() {
		if (selectedProvincia != null)
			ciudades = ciudadService.buscarPorProvincia(selectedProvincia);
		else
			ciudades.clear();
	}

}
