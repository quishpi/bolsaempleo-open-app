package ec.edu.luisrogerio.webapp.view.bean;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ec.edu.luisrogerio.webapp.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class CambiarClaveBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String clave;
	private String claveRepite;
	private String claveActual;

	public void changePassword() {
		Utils.redirectToPage("/successpassword.xhtml");
	}
}
