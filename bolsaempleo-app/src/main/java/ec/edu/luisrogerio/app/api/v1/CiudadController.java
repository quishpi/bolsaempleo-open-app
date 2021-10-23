package ec.edu.luisrogerio.app.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.luisrogerio.dto.AppResponseDTO;
import ec.edu.luisrogerio.dto.CiudadDTO;
import ec.edu.luisrogerio.service.crud.CiudadService;
import ec.edu.luisrogerio.app.utils.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_V1_CIUDAD)
public class CiudadController {

	@Autowired
	private CiudadService ciudadService;

	@GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		List<CiudadDTO> lista = ciudadService.buscarTodo(new CiudadDTO());
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<CiudadDTO>> response = new AppResponseDTO<List<CiudadDTO>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByNombre(@PathVariable("nombre") String nombre) {
		CiudadDTO provinciaDto = new CiudadDTO();
		provinciaDto.setNombre(nombre);
		List<CiudadDTO> lista = ciudadService.buscarTodo(provinciaDto);
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<CiudadDTO>> response = new AppResponseDTO<List<CiudadDTO>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insert(@RequestBody CiudadDTO ciudadDto) {
		ciudadService.guardar(ciudadDto);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody CiudadDTO provinciaDto) {
		ciudadService.actualizar(id, provinciaDto);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}
}
