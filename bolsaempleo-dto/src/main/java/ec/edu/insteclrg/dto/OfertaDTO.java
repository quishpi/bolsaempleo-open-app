package ec.edu.insteclrg.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OfertaDTO {

	private long id;
	private String rucEmpleador;
	private String nombreEmpresa;
	private byte[] logo;
	
	private String tituloOferta;
	private String fechaPublicacion;
	private String fechaLimiteAplicacion;
	private String provincia;
	private String ciudad;
	private String remuneracion;
	private byte[] banner;
	private String tipoContrato;
	private String descripcion;

}
