package ec.edu.insteclrg.webapp.view.bean.admin;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.domain.candidato.OfertaAplicacion;
import ec.edu.insteclrg.domain.empleador.DatosEmpleador;
import ec.edu.insteclrg.domain.empleador.Oferta;
import ec.edu.insteclrg.dto.OfertaDTO;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.candidato.OfertaAplicacionServicio;
import ec.edu.insteclrg.service.empleador.DatosEmpleadorService;
import ec.edu.insteclrg.service.empleador.OfertaServicio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class OfertasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<OfertaDTO> elements;
	private List<OfertaAplicacion> ofertaAplicados;
	private OfertaDTO selectedElement;
	private List<DatosCandidato> candidatos;

	private User user;
	private OfertaAplicacion ofertaAplicacion;

	@Autowired
	private OfertaServicio entityService;
	@Autowired
	DatosEmpleadorService empleadorService;
	@Autowired
	OfertaServicio ofertaService;
	@Autowired
	DatosCandidatoService candidatoService;
	@Autowired
	OfertaAplicacionServicio ofertaAplicacionService;

	private boolean disponible;
	private String textoBuscar;

	@PostConstruct
	public void init() {
		elements = new ArrayList<OfertaDTO>();
		listarToto();
	}

	public void listarToto() {
		elements.clear();
		entityService.buscarTodo(new Oferta());
		List<Oferta> ofertas = entityService.buscarTodo(new Oferta());
		for (Oferta oferta : ofertas) {
			OfertaDTO ofertaDto = new OfertaDTO();
			ofertaDto = cargarOfertaDto(oferta);
			elements.add(ofertaDto);
		}
		textoBuscar = "";
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements", ":frm:txtBuscar");
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

	public Boolean expirado(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaExpira = LocalDate.parse(fecha, formatter);
		return fechaExpira.isBefore(fechaHoy);
	}

	public void buscar() {
		elements.clear();
		List<Oferta> ofertas = entityService.buscarPorPatron(textoBuscar);
		for (Oferta oferta : ofertas) {
			OfertaDTO ofertaDto = new OfertaDTO();
			ofertaDto = cargarOfertaDto(oferta);
			elements.add(ofertaDto);
		}
		// elements = entityService.buscarPorPatron(textoBuscar);
		PrimeFaces.current().ajax().update(":frm:growl", ":frm:dt-elements");
	}

	public void cargarAplicaciones(Long idOferta) {
		Oferta oferta = new Oferta();
		oferta.setId(idOferta);

		Optional<Oferta> ofertaAplicado;
		ofertaAplicado = ofertaService.buscar(oferta);

		ofertaAplicados = new ArrayList<OfertaAplicacion>();
		ofertaAplicados = ofertaAplicacionService.buscarPorOferta(ofertaAplicado.get());

		candidatos = new ArrayList<DatosCandidato>();
		for (OfertaAplicacion aplicacion : ofertaAplicados) {
			Optional<DatosCandidato> candidato;
			candidato = candidatoService.buscarPorCedula(aplicacion.getUser().getUsername());
			if (candidato.isPresent())
				candidatos.add(candidato.get());
		}
	}
}
