package ec.edu.insteclrg.webapp.view.bean.candidato;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.insteclrg.common.DateUtils;
import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.OfertaAplicacion;
import ec.edu.insteclrg.domain.empleador.DatosEmpleador;
import ec.edu.insteclrg.domain.empleador.Oferta;
import ec.edu.insteclrg.dto.OfertaDTO;
import ec.edu.insteclrg.service.candidato.OfertaAplicacionServicio;
import ec.edu.insteclrg.service.empleador.DatosEmpleadorService;
import ec.edu.insteclrg.service.empleador.OfertaServicio;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import ec.edu.insteclrg.webapp.view.bean.LoginBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class OfertaAplicacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<OfertaDTO> elements;
	private List<OfertaDTO> elementsAplicados;
	private List<OfertaDTO> elementsExpirados;
	private OfertaDTO selectedElement;

	private User user;
	private OfertaAplicacion ofertaAplicacion;

	@Autowired
	private LoginBean loginBean;
	@Autowired
	private OfertaServicio OfertaService;
	@Autowired
	private OfertaAplicacionServicio ofertaAplicacionService;
	@Autowired
	DatosEmpleadorService empleadorService;
	@Autowired
	OfertaServicio ofertaService;

	private boolean disponible;

	@PostConstruct
	public void init() {
		elements = new ArrayList<OfertaDTO>();

		this.user = loginBean.getUser();
		OfertaService.buscarTodo(new Oferta());
		List<Oferta> ofertas = OfertaService.buscarPorFechaLimiteAplicacionActivo(LocalDate.now());
		for (Oferta oferta : ofertas) {
			OfertaDTO ofertaDto = new OfertaDTO();
			ofertaDto = cargarOfertaDto(oferta);
			elements.add(ofertaDto);
		}

		elementsAplicados = new ArrayList<OfertaDTO>();
		List<OfertaAplicacion> ofertasAplicados = ofertaAplicacionService.buscarPorCandidato(user);
		for (OfertaAplicacion ofertaAplicacion : ofertasAplicados) {
			OfertaDTO ofertaDto = new OfertaDTO();
			ofertaDto = cargarOfertaDto(ofertaAplicacion.getOferta());
			elementsAplicados.add(ofertaDto);
		}
		
		elementsExpirados = new ArrayList<OfertaDTO>();
		List<Oferta> ofertasExpirados = OfertaService.buscarPorFechaLimiteAplicacionExpirado(LocalDate.now());
		for (Oferta oferta : ofertasExpirados) {
			OfertaDTO ofertaDto = new OfertaDTO();
			ofertaDto = cargarOfertaDto(oferta);
			elementsExpirados.add(ofertaDto);
		}

		
	}

	private OfertaDTO cargarOfertaDto(Oferta oferta) {
		OfertaDTO ofertaDto = new OfertaDTO();
		ofertaDto.setId(oferta.getId());
		ofertaDto.setRucEmpleador(oferta.getUser().getUsername());
		Optional<DatosEmpleador> datosEmpleador;
		datosEmpleador = empleadorService.buscarPorRuc(ofertaDto.getRucEmpleador());
		ofertaDto.setNombreEmpresa(datosEmpleador.get().getNombreEmpresa());
		ofertaDto.setLogo(datosEmpleador.get().getLogo());
		ofertaDto.setTituloOferta(oferta.getTituloOferta());
		ofertaDto.setFechaPublicacion(DateUtils.getFechaFromLocalDate(oferta.getFechaPublicacion()));
		ofertaDto.setFechaLimiteAplicacion(DateUtils.getFechaFromLocalDate(oferta.getFechaLimiteAplicacion()));
		ofertaDto.setProvincia(oferta.getCiudad().getProvincia().getNombre());
		ofertaDto.setCiudad(oferta.getCiudad().getNombre());
		ofertaDto.setRemuneracion(oferta.getRemuneracion());
		ofertaDto.setBanner(oferta.getBanner());
		ofertaDto.setTipoContrato(oferta.getTipoContrato().getNombre());
		ofertaDto.setDescripcion(oferta.getDescripcion());
		return ofertaDto;
	}

	public void enviarSolicitud() {
		this.ofertaAplicacion = new OfertaAplicacion();
		Optional<Oferta> oferta;
		oferta = ofertaService.buscarPorId(selectedElement.getId());
		ofertaAplicacion.setOferta(oferta.get());
		ofertaAplicacion.setUser(user);
		ofertaAplicacion.setFechaRegistro(LocalDate.now());

		ofertaAplicacionService.guardar(ofertaAplicacion);
		Mensajes.addMsg(MensajesTipo.INFORMACION, "Has aplicado a la oferta: \"" + selectedElement.getTituloOferta()
				+ "\" en \"" + selectedElement.getNombreEmpresa() + "\"");
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements", ":frm:dt-elements2", ":frm:dt-elements3");
	}

	public String logo(byte[] banner) {
		if (banner != null)
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(banner);
		else
			return "";// resources/images/logo_lrg_200.png
	}

	public String banner() {
		if (selectedElement.getBanner() != null)
			return "data:image/png;base64," + Base64.getEncoder().encodeToString(selectedElement.getBanner());
		else
			return "";
	}

	public void changeDisponible() {
		disponible = true;
		Optional<Oferta> oferta;
		oferta = ofertaService.buscarPorId(selectedElement.getId());

		Optional<OfertaAplicacion> ofApp;
		ofApp = ofertaAplicacionService.buscarPorOfertaCandidato(oferta.get(), user);
		if (ofApp.isPresent())
			disponible = false;
		PrimeFaces.current().ajax().update(":frm:dialog1");
	}
}
