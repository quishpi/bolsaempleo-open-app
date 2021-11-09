package ec.edu.luisrogerio.domain.candidato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ec.edu.luisrogerio.domain.NivelInstruccion;
import ec.edu.luisrogerio.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Instruccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "nivelInstruccion_id")
	private NivelInstruccion nivelInstruccion;

	@Column(nullable = false)
	private String institucion;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	private String numeroRegistro;

}
