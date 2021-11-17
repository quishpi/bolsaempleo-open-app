package ec.edu.insteclrg.webapp.view.bean.candidato;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.insteclrg.domain.Ciudad;
import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.crud.CiudadService;
import ec.edu.insteclrg.service.crud.ProvinciaService;
import ec.edu.insteclrg.service.crud.UserService;
import ec.edu.insteclrg.webapp.enums.MensajeError;
import ec.edu.insteclrg.webapp.enums.MensajeOk;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Constants;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import ec.edu.insteclrg.webapp.utils.Utils;
import ec.edu.insteclrg.webapp.view.bean.LoginBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class DatosPersonalesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Provincia> provincias;
	private Provincia selectedProvincia;

	private List<Ciudad> ciudades;
	private Ciudad selectedCiudad;

	private DatosCandidato candidato;
	private String fechaNacimiento;
	private String foto;
	private String fotoTmp;
	private String cv;
	private String cvTmp;

	@Autowired
	private ProvinciaService provinciaService;

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private DatosCandidatoService candidatoService;

	@Autowired
	private UserService userService;

	@Autowired
	LoginBean loginBean;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@PostConstruct
	public void init() {
		this.ciudades = new ArrayList<Ciudad>();
		provincias = this.provinciaService.buscarTodo(new Provincia());
		Optional<DatosCandidato> candidatoOptional = Optional.empty();
		candidatoOptional = candidatoService.buscarPorCedula(loginBean.getUser().getUsername());
		if (!candidatoOptional.isPresent()) {
			Utils.redirectToPage(Constants.URI_WEB_404);
		}
		candidato = candidatoOptional.get();
		Map<Long, Provincia> provinciasMap = provincias.stream()
				.collect(Collectors.toMap(Provincia::getId, student -> student));
		selectedProvincia = provinciasMap.get(candidato.getCiudad().getProvincia().getId());
		if (!provincias.isEmpty()) {
			this.ciudades = ciudadService.buscarPorProvincia(this.selectedProvincia);
		}
		Map<Long, Ciudad> ciudadesMap = ciudades.stream().collect(Collectors.toMap(Ciudad::getId, ciudad -> ciudad));
		selectedCiudad = ciudadesMap.get(candidato.getCiudad().getId());

		if (candidato.getFoto() != null) {
			foto = Base64.getEncoder().encodeToString(candidato.getFoto());
			fotoTmp = foto;
		}
		if (candidato.getCvArchivo() != null) {
			cv = Base64.getEncoder().encodeToString(candidato.getCvArchivo());
			cvTmp = cv;
		}
	}

	public void guardar() {
		byte[] fotoByte = null;
		byte[] cvByte = null;
		if (foto != null)
			if (!foto.isEmpty())
				fotoByte = Base64.getDecoder().decode(foto);
		if (cv != null)
			if (!cv.isEmpty())
				cvByte = Base64.getDecoder().decode(cv);

		candidato.setFoto(fotoByte);
		candidato.setCvArchivo(cvByte);

		candidato.setCiudad(selectedCiudad);
		try {
			if (candidatoService.actualizar(candidato) != null) {
				Mensajes.addMsg(MensajesTipo.INFORMACION, MensajeOk.ACTUALIZADO_OK.toString()
						+ ". Cierre la sesión e ingrese de nuevo para ver los cambios");
			} else {
				Mensajes.addMsg(MensajesTipo.ERROR, MensajeError.NO_GUARDADO.toString());
			}
		} catch (Exception e) {
			Mensajes.addMsg(MensajesTipo.ERROR, e.getMessage());
		}

	}

	public void onProvinciaChange() {
		if (selectedProvincia != null)
			ciudades = ciudadService.buscarPorProvincia(selectedProvincia);
		else
			ciudades.clear();
	}

	public void changePic() {
		foto = fotoTmp;
		// if (foto.isEmpty() || foto == null)
		// foto = SINFOTO;
		// PrimeFaces.current().ajax().update("frm");
	}

	public void changeCv() {
		cv = cvTmp;
		// if (cv.isEmpty() || cv == null)
		// cv = SINCV;
	}

	public void returnDesktop() {
		Utils.redirectToPage("index.xhtml");
	}
}
