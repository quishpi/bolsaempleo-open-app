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
import ec.edu.luisrogerio.dto.ProvinciaDTO;
import ec.edu.luisrogerio.service.crud.ProvinciaService;
import ec.edu.luisrogerio.app.utils.Constants;

@RestController
@RequestMapping(value = Constants.URI_API_V1_PROVINCIA)
public class ProvinciaController {

	@Autowired
	private ProvinciaService provinciaService;

	@GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		List<ProvinciaDTO> lista = provinciaService.buscarTodo(new ProvinciaDTO());
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<ProvinciaDTO>> response = new AppResponseDTO<List<ProvinciaDTO>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByNombre(@PathVariable("nombre") String nombre) {
		ProvinciaDTO provinciaDto = new ProvinciaDTO();
		provinciaDto.setNombre(nombre);
		List<ProvinciaDTO> lista = provinciaService.buscarTodo(provinciaDto);
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<ProvinciaDTO>> response = new AppResponseDTO<List<ProvinciaDTO>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insert(@RequestBody ProvinciaDTO provinciaDto) {
		provinciaService.guardar(provinciaDto);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@RequestBody ProvinciaDTO provinciaDto) {
		provinciaService.actualizar(provinciaDto);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}
}
