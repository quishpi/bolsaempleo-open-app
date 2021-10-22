package ec.edu.luisrogerio.app.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.luisrogerio.app.dto.AppResponseDTO;
import ec.edu.luisrogerio.domain.Provincia;
import ec.edu.luisrogerio.service.crud.ProvinciaService;

@RestController
@RequestMapping(value = "/provincia")
public class ProvinciaController {

	@Autowired
	private ProvinciaService provinciaService;

	@GetMapping(value = "/lista", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll() {
		List<Provincia> lista = provinciaService.buscarTodo();
		for (Provincia prov : lista) {
			System.out.println(prov.getNombre());
		}
		if (!ObjectUtils.isEmpty(lista)) {
			AppResponseDTO<List<Provincia>> response = new AppResponseDTO<List<Provincia>>(true, lista);
			return (new ResponseEntity<Object>(response, HttpStatus.OK));
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Provincia> insertProvincia(@RequestBody Provincia provincia) {
		return new ResponseEntity<Provincia>(provinciaService.guardar(provincia), HttpStatus.CREATED);
	}
}
