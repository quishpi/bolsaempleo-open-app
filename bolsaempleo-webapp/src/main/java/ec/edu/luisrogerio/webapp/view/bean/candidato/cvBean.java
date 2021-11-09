package ec.edu.luisrogerio.webapp.view.bean.candidato;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.domain.candidato.DatosCandidato;
import ec.edu.luisrogerio.domain.candidato.Experiencia;
import ec.edu.luisrogerio.domain.candidato.Instruccion;
import ec.edu.luisrogerio.service.candidato.DatosCandidatoService;
import ec.edu.luisrogerio.service.candidato.ExperienciaService;
import ec.edu.luisrogerio.service.candidato.InstruccionService;
import ec.edu.luisrogerio.webapp.utils.Constants;
import ec.edu.luisrogerio.webapp.utils.Utils;
import ec.edu.luisrogerio.webapp.view.bean.LoginBean;
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
public class cvBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DatosCandidato candidato;

	@Autowired
	private DatosCandidatoService candidatoService;

	@Autowired
	private ExperienciaService experienciaService;
	@Autowired
	private InstruccionService instruccionService;

	@Autowired
	LoginBean loginBean;

	@PostConstruct
	public void init() {
	}

	public void openPdf() throws JRException, IOException {
		Optional<DatosCandidato> candidatoOptional = Optional.empty();
		candidatoOptional = candidatoService.buscarPorCedula(loginBean.getUser().getUsername());
		if (!candidatoOptional.isPresent()) {
			Utils.redirectToPage(Constants.URI_WEB_404);
		}
		candidato = candidatoOptional.get();
		Optional<DatosCandidato> candidato = candidatoService.buscarPorCedula(loginBean.getUser().getUsername());
		List<DatosCandidato> candidatos = new ArrayList<DatosCandidato>();
		candidatos.add(candidato.get());

		List<Experiencia> experiencias = experienciaService.buscarPorUsuario(loginBean.getUser());
		List<Instruccion> instrucciones = instruccionService.buscarPorUsuario(loginBean.getUser());

		String masterRptPath = "src/main/webapp/resources/reportes/cv.jrxml";
		String experienciaRptPath = "src/main/webapp/resources/reportes/sbrExperiencia.jrxml";
		String instruccionRptPath = "src/main/webapp/resources/reportes/sbrInstruccion.jrxml";

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("sbrExperiencia", JasperCompileManager.compileReport(experienciaRptPath));
		parameters.put("experiencias", experiencias);

		parameters.put("sbrInstruccion", JasperCompileManager.compileReport(instruccionRptPath));
		parameters.put("instrucciones", instrucciones);

		toPDF(parameters, masterRptPath, candidatos, "cv_" + candidato.get().getCedula());
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
