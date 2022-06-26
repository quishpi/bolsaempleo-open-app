package ec.edu.insteclrg.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OfertaAplicacionDTO {

	private long id;
	private long idOferta;
	//Oferta
	private String rucEmpleador;
	private String tituloOferta;
	private String fechaPublicacion;
	private String fechaLimiteAplicacion;
	private String provincia;
	private String ciudad;
	private String remuneracion;
	private byte[] banner;
	private String tipoContrato;
	private String descripcion;
	
	
	//Candidato
	private String cedulaCandidato;
	private String nombreCandidato;
	private String fechaPostulacion;
	private String cv;

}
