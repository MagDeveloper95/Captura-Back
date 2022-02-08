package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
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
		if(id != null && id > -1) {
			try {
				Registro noteById = service.getRegistroById(id);	
				return new ResponseEntity<Registro>(noteById,new HttpHeaders(),HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Los registros no se han podido encontrar", e);
			}
		}else {
			return new ResponseEntity<Registro>(new Registro(), new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}

	}
	/**
	 * Metodo para crear un nuevo registro
	 * @param note
	 * @return un nuevo registro
	 * @throws ResponseStatusException para informar al usuario
	 */
	@PostMapping("/guardar")
	public ResponseEntity<Registro> create(@Valid @RequestBody Registro registro) throws ResponseStatusException {
		if(registro != null) {
			try {
				Registro creatUp = service.creaRegistro(registro);
				return new ResponseEntity<Registro>(creatUp,new HttpHeaders(),HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no se han podido crear", e);
			}
		}else {
			return new ResponseEntity<Registro>(new Registro(), new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}


	}
	
	/**
	 * Metodo que devuelve una lista de registros por fecha
	 * @param fecha
	 * @return
	 * @throws ResponseStatusException
	 */
	@GetMapping("/fecha/{fecha}")
	public ResponseEntity<List<Registro>> getRegistroByDate(@PathVariable(value="fecha")LocalDate fecha) throws ResponseStatusException{
		if(fecha!=null) {
			try {
				List<Registro> noteByDate =  service.getRegistroPorFecha(fecha);
				return new ResponseEntity<List<Registro>>(noteByDate, new HttpHeaders(),HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no se ha podido obtener", e);
			}
		}else {
			return new ResponseEntity<List<Registro>>(new ArrayList<Registro>(), new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}

	}
	/**
	 * Metodo que devuelve los registros de un usuario
	 * @param usuario
	 * @return Lista de Registros
	 * @throws ResponseStatusException
	 */
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<List<Registro>> getRegistroPorUsuario(@PathVariable(value="idUsuario")Long idUsuario) throws ResponseStatusException{
		if(idUsuario!=null&&idUsuario!=-1) {
			try {
				List<Registro> registroByUser = service.getRegistroPorUsuario(idUsuario);
				return new ResponseEntity<List<Registro>>(registroByUser, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El registro no se ha podido obtener", e);
			}
		}else {
			return new ResponseEntity<List<Registro>>(new ArrayList<Registro>(), new HttpHeaders(),HttpStatus.BAD_REQUEST);
		}

	}
	
}
