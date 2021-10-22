package ec.edu.luisrogerio.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Provincia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column
	private String nombre;

	/*
	 * Unidireccional
	 * No necesita columna provincia en la entidad Ciudad
	 * 
	// @JsonIgnore
	@OneToMany(targetEntity = Ciudad.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "provincia_id", referencedColumnName = "id")
	private List<Ciudad> ciudades;
	*/
	
	/*
	 * redundancia de datos
	//@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "provincia", fetch = FetchType.EAGER)
	List<Ciudad> ciudades=new ArrayList<Ciudad>();
	*/
}
