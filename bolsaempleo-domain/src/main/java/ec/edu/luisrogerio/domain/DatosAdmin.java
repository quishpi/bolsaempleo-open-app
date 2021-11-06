package ec.edu.luisrogerio.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class DatosAdmin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private long id;

	@OneToOne
	// @MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false, unique = true)
	private String cedula;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellido;

	// @Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] foto;

	@ManyToOne
	@JoinColumn(name = "ciudad_id")
	private Ciudad ciudad;

	@Column
	private String direccion;

	@Column
	private String telefono;

	@Column
	private String celular;

	@Column(nullable = false)
	private String email;

	@Column
	private LocalDate fechaNacimiento;

	
	@Column
	private LocalDate fechaRegistro;

}
