package ec.edu.insteclrg.webapp.view.bean.admin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.service.crud.ProvinciaService;
import ec.edu.insteclrg.webapp.enums.MensajesTipo;
import ec.edu.insteclrg.webapp.utils.Mensajes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class ProvinciasDatosAdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Provincia> elements;
	private Provincia selectedElement;
	private List<Provincia> selectedElements;
	@Autowired
	private ProvinciaService entityService;

	@PostConstruct
	public void init() {
		this.elements = entityService.buscarTodo(new Provincia());

	}

	public void openNew() {
		this.selectedElement = new Provincia();
		this.selectedElement.setId(0L);
	}

	public void saveElement() {
		if (this.selectedElement.getId() == 0L) {
			selectedElement=entityService.guardar(selectedElement);
			this.elements.add(this.selectedElement);
			Mensajes.addMsg(MensajesTipo.INFORMACION, "Registro agregado");
		} else {
			entityService.actualizar(selectedElement);
			Mensajes.addMsg(MensajesTipo.INFORMACION, "Registro actualizado");
		}

		PrimeFaces.current().executeScript("PF('manageElementDialog').hide()");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-elements");
	}

	public void deleteElement() {
		entityService.eliminar(selectedElement);
		this.elements.remove(this.selectedElement);
		this.selectedElement = null;
		Mensajes.addMsg(MensajesTipo.INFORMACION, "Registro eliminado");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-elements");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedElements()) {
			int size = this.selectedElements.size();
			return size > 1 ? size + " registros seleccionados" : "1 registro seleccionado";
		}
		return "Eliminar";
	}

	public boolean hasSelectedElements() {
		return this.selectedElements != null && !this.selectedElements.isEmpty();
	}

	public void deleteSelectedElements() {
		for (int i = 0; i < this.selectedElements.size(); i++) {
			entityService.eliminar(selectedElements.get(i));
		}
		this.elements.removeAll(this.selectedElements);
		this.selectedElements = null;
		Mensajes.addMsg(MensajesTipo.INFORMACION, "Registros eliminados");
		PrimeFaces.current().ajax().update("frm:growl", "frm:dt-elements");
		PrimeFaces.current().executeScript("PF('dtElementos').clearFilters()");
	}

}
