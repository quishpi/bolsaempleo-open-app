package ec.edu.insteclrg.webapp.view.bean.candidato;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import ec.edu.insteclrg.dto.reports.DatosCandidatoDTO;
import ec.edu.insteclrg.service.candidato.CapacitacionService;
import ec.edu.insteclrg.service.candidato.DatosCandidatoService;
import ec.edu.insteclrg.service.candidato.ExperienciaService;
import ec.edu.insteclrg.service.candidato.InstruccionService;
import ec.edu.insteclrg.service.candidato.ReferenciaPersonalService;
import ec.edu.insteclrg.service.candidato.ReferenciaProfesionalService;
import ec.edu.insteclrg.service.crud.UserService;
import ec.edu.insteclrg.webapp.utils.Constants;
import ec.edu.insteclrg.webapp.utils.Utils;
import ec.edu.insteclrg.webapp.view.bean.LoginBean;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Getter
@Setter
@Component
@Scope("view")
public class CvBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DatosCandidatoService candidatoService;

	@Autowired
	private ExperienciaService experienciaService;
	@Autowired
	private InstruccionService instruccionService;
	@Autowired
	private CapacitacionService capacitacionService;
	@Autowired
	private ReferenciaPersonalService referenciaPersonalService;
	@Autowired
	private ReferenciaProfesionalService referenciaProfesionalService;
	
	@Autowired
	private UserService userService;

	@Autowired
	LoginBean loginBean;

	@PostConstruct
	public void init() {
	}

	public void openPdf() throws JRException, IOException {
		openPdf(loginBean.getUser().getUsername());
	}

	public void openPdf(String cedula) throws JRException, IOException {
		Optional<DatosCandidato> candidatoOptional = Optional.empty();
		candidatoOptional = candidatoService.buscarPorCedula(cedula);
		if (!candidatoOptional.isPresent()) {
			Utils.redirectToPage(Constants.URI_WEB_404);
		}
		Optional<DatosCandidato> candidato = candidatoService.buscarPorCedula(cedula);
		DatosCandidatoDTO candidatoDto = candidatoToDTO(candidato.get());

		List<DatosCandidatoDTO> candidatos = new ArrayList<DatosCandidatoDTO>();
		candidatos.add(candidatoDto);

		User usuarioTmp=new User();
		usuarioTmp.setUsername(cedula);
		
		Optional<User> usuario;
		usuario=userService.buscar(usuarioTmp);
		
		List<Experiencia> experiencias = experienciaService.buscarPorUsuario(usuario.get());
		List<Instruccion> instrucciones = instruccionService.buscarPorUsuario(usuario.get());
		List<Capacitacion> capacitaciones = capacitacionService.buscarPorUsuario(usuario.get());
		List<ReferenciaPersonal> referenciasPersonales = referenciaPersonalService
				.buscarPorUsuario(usuario.get());
		List<ReferenciaProfesional> referenciasProfesionales = referenciaProfesionalService
				.buscarPorUsuario(usuario.get());

		String masterRptPath = "src/main/webapp/resources/reportes/cv.jrxml";
		String experienciaRptPath = "src/main/webapp/resources/reportes/sbrExperiencia.jrxml";
		String instruccionRptPath = "src/main/webapp/resources/reportes/sbrInstruccion.jrxml";
		String capacitacionRptPath = "src/main/webapp/resources/reportes/sbrCapacitacion.jrxml";
		String referenciaPersonalRptPath = "src/main/webapp/resources/reportes/sbrReferenciaPersonal.jrxml";
		String referenciaProfesionalRptPath = "src/main/webapp/resources/reportes/sbrReferenciaProfesional.jrxml";

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sbrExperiencia", JasperCompileManager.compileReport(experienciaRptPath));
		parameters.put("experiencias", experiencias);

		parameters.put("sbrInstruccion", JasperCompileManager.compileReport(instruccionRptPath));
		parameters.put("instrucciones", instrucciones);

		parameters.put("sbrCapacitacion", JasperCompileManager.compileReport(capacitacionRptPath));
		parameters.put("capacitaciones", capacitaciones);

		parameters.put("sbrReferenciaPersonal", JasperCompileManager.compileReport(referenciaPersonalRptPath));
		parameters.put("referenciasPersonales", referenciasPersonales);

		parameters.put("sbrReferenciaProfesional", JasperCompileManager.compileReport(referenciaProfesionalRptPath));
		parameters.put("referenciasProfesionales", referenciasProfesionales);

		if (candidato.get().getFoto() != null)
			parameters.put("foto", new ByteArrayInputStream(candidato.get().getFoto()));

		byte[] image = extractBytes2("src/main/webapp/resources/images/bkg_cv.png");
		InputStream logo = new ByteArrayInputStream(image);
		parameters.put("bkg_cv", logo);

		toPDF(parameters, masterRptPath, candidatos, "cv_" + candidato.get().getCedula());
	}

	private DatosCandidatoDTO candidatoToDTO(DatosCandidato candidato) {
		DatosCandidatoDTO candidatoDto = new DatosCandidatoDTO();
		candidatoDto.setUserName(candidato.getUser().getUsername());
		candidatoDto.setNombre(candidato.getNombre());
		candidatoDto.setApellido(candidato.getApellido());
		candidatoDto.setCedula(candidato.getCedula());
		candidatoDto.setFoto(candidato.getFoto());
		candidatoDto.setCiudad(candidato.getCiudad().getNombre());
		candidatoDto.setProvincia(candidato.getCiudad().getProvincia().getNombre());
		candidatoDto.setDireccion(candidato.getDireccion());
		candidatoDto.setTelefono(candidato.getTelefono());
		candidatoDto.setCelular(candidato.getCelular());
		candidatoDto.setEmail(candidato.getEmail());
		candidatoDto.setFechaNacimiento(candidato.getFechaNacimiento());
		return candidatoDto;
	}

	public static byte[] extractBytes2(String ImageName) throws IOException {
		File imgPath = new File(ImageName);
		String ext = "png";
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, ext, baos);

		return baos.toByteArray();
	}

	public void toPDF(HashMap<String, Object> parameters, String masterRptPath, List<?> masterDataSet,
			String outputFileName) throws JRException, IOException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(masterDataSet);
		JasperReport masterReport = JasperCompileManager.compileReport(new FileInputStream(masterRptPath));
		JasperPrint report = JasperFillManager.fillReport(masterReport, parameters, beanCollectionDataSource);
		// Guarda en un archivo
		// JasperExportManager.exportReportToPdfFile(report,"cv.pdf");

		// Muestra un diálogo para abrir o guardar
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.addHeader("Content-disposition", "attachment;filename=" + outputFileName + ".pdf");
		ServletOutputStream stream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(report, stream);
		FacesContext.getCurrentInstance().responseComplete();

	}

}
