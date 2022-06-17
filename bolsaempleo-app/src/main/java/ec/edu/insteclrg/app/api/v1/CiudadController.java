package ec.edu.insteclrg.app.api.v1;

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

import ec.edu.insteclrg.app.utils.Constants;
import ec.edu.insteclrg.domain.Ciudad;
import ec.edu.insteclrg.domain.Provincia;
import ec.edu.insteclrg.dto.AppResponseDTO;
import ec.edu.insteclrg.service.crud.CiudadService;
import ec.edu.insteclrg.service.crud.ProvinciaService;

@RestController
@RequestMapping(value = Constants.URI_API_V1_CIUDAD)
public class CiudadController {

	@Autowired
	private CiudadService ciudadService;
	
	@Autowired
	private ProvinciaService provinciaService;

	@GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		List<Ciudad> lista = ciudadService.buscarTodo(new Ciudad());
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<Ciudad>> response = new AppResponseDTO<List<Ciudad>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByNombre(@PathVariable("nombre") String nombre) {
		Ciudad provinciaDto = new Ciudad();
		provinciaDto.setNombre(nombre);
		List<Ciudad> lista = ciudadService.buscarTodo(provinciaDto);
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<Ciudad>> response = new AppResponseDTO<List<Ciudad>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/guardar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insert(@RequestBody Ciudad entity) {
		Provincia provincia =new Provincia();
		provincia.setId(entity.getProvincia().getId());
		provinciaService.buscar(provincia);
		ciudadService.guardar(entity);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}

	@PutMapping(value = "/actualizar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Ciudad entity) {
		ciudadService.actualizar(entity);
		return new ResponseEntity<>(new AppResponseDTO<>(true, null), HttpStatus.CREATED);
	}
}
