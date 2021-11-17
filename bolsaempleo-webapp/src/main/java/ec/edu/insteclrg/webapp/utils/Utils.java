package ec.edu.insteclrg.webapp.utils;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Utils {
	public static void redirectToPage(String page) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		// FacesContext.getCurrentInstance().getExternalContext().redirect("../escritorio/index.xhtml");
		try {
			context.redirect(context.getRequestContextPath() + page);
		} catch (IOException e) {
			try {
				context.redirect(context.getRequestContextPath() + Constants.URI_WEB_404);
			} catch (IOException e1) {
				System.out.println("Error Redirect: " + e1.getMessage());
			}

		}
	}

}
