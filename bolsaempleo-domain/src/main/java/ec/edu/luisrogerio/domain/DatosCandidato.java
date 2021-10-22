package ec.edu.luisrogerio.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DatosCandidato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private long id;

	@OneToOne
	// @MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String cedula;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellido;

	@Column
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
	private byte[] cvArchivo;

	@Column
	private LocalDate fechaRegistro;

}
