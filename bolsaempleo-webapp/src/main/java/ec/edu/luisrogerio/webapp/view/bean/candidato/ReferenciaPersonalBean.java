package ec.edu.luisrogerio.webapp.view.bean.candidato;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.domain.User;
import ec.edu.luisrogerio.domain.candidato.ReferenciaPersonal;
import ec.edu.luisrogerio.service.candidato.ReferenciaPersonalService;
import ec.edu.luisrogerio.webapp.enums.MensajesTipo;
import ec.edu.luisrogerio.webapp.utils.Mensajes;
import ec.edu.luisrogerio.webapp.view.bean.LoginBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class ReferenciaPersonalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ReferenciaPersonal> elements;
	private ReferenciaPersonal selectedElement;
	private List<ReferenciaPersonal> selectedElements;

	private User user;

	@Autowired
	private LoginBean loginBean;
	@Autowired
	private ReferenciaPersonalService entityService;

	@PostConstruct
	public void init() {
		this.user = loginBean.getUser();
		this.elements = entityService.buscarPorUsuario(user);
	}

	public void openNew() {
		this.selectedElement = new ReferenciaPersonal();
	}

	public void saveElement() {
		if (this.selectedElement.getId() == 0L) {
			selectedElement.setUser(user);
			this.elements.add(this.selectedElement);
			entityService.guardar(selectedElement);
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
