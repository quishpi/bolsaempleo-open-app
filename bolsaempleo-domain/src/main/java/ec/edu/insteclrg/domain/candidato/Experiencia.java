package ec.edu.insteclrg.domain.candidato;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ec.edu.insteclrg.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Experiencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private LocalDate fechaInicio;

	@Column(nullable = false)
	private String fechaFin;

	@Column(nullable = false)
	private String puesto;

	@Column(nullable = false)
	private String cargo;

	@Column(nullable = false)
	private String institucion;

}
