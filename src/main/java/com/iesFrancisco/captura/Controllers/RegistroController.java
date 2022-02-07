package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Services.RegistroService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	RegistroService service;
	
	/**
	 * Metodo que devuelve todos los registros de la base de datos
	 * @return Todos los registros
	 * @throws ResponseStatusException para informar al usuario
	 */
	@GetMapping
	public ResponseEntity<List<Registro>> getAllNotes() throws ResponseStatusException {
		try {
			List<Registro> all = service.getAllRegistros();
			// Devolvemos la lista y respuestas de http
			return new ResponseEntity<List<Registro>>(all, new HttpHeaders(), HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los registros no se han podido encontrar", e);
		}
	}
	/**
	 * Metodo que devuelve nota por id
	 * @param id lo vinculamos con el getmapping ("/{id}") delante de la variable Long id
	 * @return json con la respuesta
	 */
	@GetMapping("/{id}") //Para meterle el id 
	public ResponseEntity<Registro> getNotesById(@PathVariable("id")Long id) throws ResponseStatusException{
		try {
			Registro noteById = service.getRegistroById(id);	
			return new ResponseEntity<Registro>(noteById,new HttpHeaders(),HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los registros no se han podido encontrar", e);
		}
	}
	/**
	 * Metodo para crear un nuevo registro
	 * @param note
	 * @return un nuevo registro
	 * @throws ResponseStatusException para informar al usuario
	 */
	@PostMapping
	public ResponseEntity<Registro> create(@Valid @RequestBody Registro note) throws ResponseStatusException {
		try {
			Registro creatUp = service.creaRegistro(note);
			return new ResponseEntity<Registro>(creatUp,new HttpHeaders(),HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no se han podido crear", e);
		}

	}
	
	/**
	 * Metodo que devuelve una lista de registros por fecha
	 * @param fecha
	 * @return
	 * @throws ResponseStatusException
	 */
	@GetMapping("/{fecha}")
	public ResponseEntity<List<Registro>> getNotesByDate(@PathVariable(value="fecha")LocalDate fecha) throws ResponseStatusException{
		try {
			List<Registro> noteByDate =  service.getRegistroPorFecha(fecha);
			return new ResponseEntity<List<Registro>>(noteByDate, new HttpHeaders(),HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no se ha podido obtener", e);
		}
	}
	
}
