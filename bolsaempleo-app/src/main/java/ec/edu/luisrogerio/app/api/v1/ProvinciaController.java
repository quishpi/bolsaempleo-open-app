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
import ec.edu.luisrogerio.service.crud.ProvinciaService;
import ec.edu.luisrogerio.app.utils.Constants;
import ec.edu.luisrogerio.domain.Provincia;

@RestController
@RequestMapping(value = Constants.URI_API_V1_PROVINCIA)
public class ProvinciaController {

	@Autowired
	private ProvinciaService provinciaService;

	@GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		List<Provincia> lista = provinciaService.buscarTodo(new Provincia());
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<Provincia>> response = new AppResponseDTO<List<Provincia>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByNombre(@PathVariable("nombre") String nombre) {
		Provincia provinciaDto = new Provincia();
		provinciaDto.setNombre(nombre);
		List<Provincia> lista = provinciaService.buscarTodo(provinciaDto);
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<Provincia>> response = new AppResponseDTO<List<Provincia>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insert(@RequestBody Provincia entity) {
		provinciaService.guardar(entity);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@RequestBody Provincia entity) {
		provinciaService.actualizar(entity);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}
}
