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
public class ReturnBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public void returnDesktop() {
		Utils.redirectToPage("index.xhtml");
	}
}
