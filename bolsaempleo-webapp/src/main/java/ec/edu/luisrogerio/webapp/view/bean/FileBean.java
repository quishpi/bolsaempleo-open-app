package ec.edu.luisrogerio.webapp.view.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.util.EscapeUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("view")
public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String folder = "C:\\jsf-upload\\";

	private Part uploadedFile;

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void saveFile() {

		System.out.println("saveFile method invoked..");
		System.out.println("content-type:{0}" + uploadedFile.getContentType());
		System.out.println("filename:{0}" + uploadedFile.getName());
		System.out.println("submitted filename:{0}" + uploadedFile.getSubmittedFileName());
		System.out.println("size:{0}" + uploadedFile.getSize());
		String fileName = "";

		try {

			fileName = getFilename(uploadedFile);

			System.out.println("fileName  " + fileName);

			uploadedFile.write(folder + fileName);

		} catch (IOException ex) {
			System.out.println(ex);

		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File " + fileName + " Uploaded!"));

	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}

}
