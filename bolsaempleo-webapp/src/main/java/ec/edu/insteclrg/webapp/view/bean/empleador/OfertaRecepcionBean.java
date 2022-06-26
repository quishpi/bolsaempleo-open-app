package ec.edu.insteclrg.webapp.view.bean.empleador;

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

import ec.edu.insteclrg.common.DateUtils;
import ec.edu.insteclrg.domain.User;
import ec.edu.insteclrg.domain.candidato.DatosCandidato;
import ec.edu.insteclrg.domain.candidato.OfertaAplicacion;
import ec.edu.insteclrg.domain.empleador.Oferta;
import ec.edu.insteclrg.dto.OfertaAplicacionDTO;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.candidato.OfertaAplicacionServicio;
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
public class OfertaRecepcionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<OfertaAplicacionDTO> elements;
	private OfertaAplicacionDTO selectedElement;
	private List<OfertaAplicacionDTO> selectedElements;

	@Autowired
	private OfertaServicio ofertaService;

	private User user;

	@Autowired
	private LoginBean loginBean;
	@Autowired
	private OfertaAplicacionServicio ofertaAplicacionService;

	@Autowired
	private DatosCandidatoService candidatoService;
	// private OfertaServicio ofertaServicio;

	@PostConstruct
	public void init() {
		elements = new ArrayList<OfertaAplicacionDTO>();
		
		this.user = loginBean.getUser();
		List<Oferta> ofertas = new ArrayList<Oferta>();
		ofertas = ofertaService.buscarPorUsuario(user);
		for (Oferta oferta : ofertas) {
			List<OfertaAplicacion> ofertaAplicacionList = new ArrayList<OfertaAplicacion>();
			ofertaAplicacionList = ofertaAplicacionService.buscarPorOferta(oferta);
			for (OfertaAplicacion ofertaAplicacion : ofertaAplicacionList) {
				OfertaAplicacionDTO ofertaApDto = new OfertaAplicacionDTO();
				ofertaApDto.setId(ofertaAplicacion.getId());
				ofertaApDto.setIdOferta(oferta.getId());
				
				//Oferta
				ofertaApDto.setRucEmpleador(oferta.getUser().getUsername());
				ofertaApDto.setTituloOferta(oferta.getTituloOferta());
				ofertaApDto.setFechaPublicacion(DateUtils.getFechaFromLocalDate(oferta.getFechaPublicacion()));
				ofertaApDto.setFechaLimiteAplicacion(DateUtils.getFechaFromLocalDate(oferta.getFechaLimiteAplicacion()));
				ofertaApDto.setProvincia(oferta.getCiudad().getProvincia().getNombre());
				ofertaApDto.setCiudad(oferta.getCiudad().getNombre());
				ofertaApDto.setRemuneracion(oferta.getRemuneracion());
				ofertaApDto.setTipoContrato(oferta.getTipoContrato().getNombre());
				ofertaApDto.setBanner(oferta.getBanner());
				ofertaApDto.setDescripcion(oferta.getDescripcion());
				
				//Candidato
				DatosCandidato candidato = new DatosCandidato();
				ofertaApDto.setCedulaCandidato(ofertaAplicacion.getUser().getUsername());
				ofertaApDto.setFechaPostulacion(DateUtils.getFechaFromLocalDate(ofertaAplicacion.getFechaRegistro()));
				candidato = postulante(ofertaApDto.getCedulaCandidato());
				ofertaApDto.setNombreCandidato(candidato.getNombre() + " " + candidato.getApellido());
				if (candidato.getCvArchivo() != null) {
					ofertaApDto.setCv(Base64.getEncoder().encodeToString(candidato.getCvArchivo()));
				}
				
				elements.add(ofertaApDto);
			}
		}
	}

	public void openNew() {
		this.selectedElement = new OfertaAplicacionDTO();
	}

	public void aceptarElemento() {

		Mensajes.addMsg(MensajesTipo.INFORMACION, "Registro eliminado");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-elements");
	}

	public boolean hasSelectedElements() {
		return this.selectedElements != null && !this.selectedElements.isEmpty();
	}

	public void deleteSelectedElements() {
		for (int i = 0; i < this.selectedElements.size(); i++) {
			// ofertaAplicacionService.eliminar(selectedElements.get(i));
		}
		this.elements.removeAll(this.selectedElements);
		this.selectedElements = null;

		Mensajes.addMsg(MensajesTipo.INFORMACION, "Registros eliminados");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-elements");
		PrimeFaces.current().executeScript("PF('dtElementos').clearFilters()");
	}

	public DatosCandidato postulante(String cedula) {
		Optional<DatosCandidato> candidato;
		candidato = candidatoService.buscarPorCedula(cedula);
		if (candidato.isPresent()) {
			return candidato.get();
		}
		return null;
	}
}
