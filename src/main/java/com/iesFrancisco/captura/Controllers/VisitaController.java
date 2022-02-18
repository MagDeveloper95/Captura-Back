package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Services.VisitaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "VisitaController", description = "Operaciones sobre visitas")
@RestController
@RequestMapping("/visita")
public class VisitaController {

	@Autowired
	VisitaService service;
	/**
	 * Nos traemos todas las visitas
	 * @param visita
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Muestra todas las visitas dela base de datos", response = Iterable.class, tags = "allVisitas")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping()
	public ResponseEntity<List<Visita>> getAllVisitas() throws ResponseStatusException{
		try {
			List<Visita> allVisita = service.getAllVisitas();
			return new ResponseEntity<List<Visita>>(allVisita, new HttpHeaders(), HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Las visitas no se han podido encontrar", e);
		}

	}
	
	/**
	 * Nos traemos la visica con la id que nosotros queramos
	 * @param id
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Muestra una visita de la base de datos", response = Visita.class, tags = "getVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/{id}")
	public ResponseEntity<Visita> getVisitaById(@PathVariable("id") Long id) throws ResponseStatusException {
		if(id!=null&&id>-1) {
			try {
				Visita visitaById = service.getVisitaPorId(id);
				return new ResponseEntity<Visita>(visitaById, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita no se ha podido encontrar", e);
			}
		}else {
			return new ResponseEntity<Visita>(new Visita(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Creamos una visita
	 * @param visita
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Crea una visita en la base de datos", response = Visita.class, tags = "createVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@PostMapping("/guardar")
	public ResponseEntity<Visita> createVisita(@RequestBody Visita visita) throws ResponseStatusException {
		if(visita!=null) {
			try {
				Visita addVisita = service.creaVisita(visita);
				return new ResponseEntity<Visita>(addVisita, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita no se ha podido crear", e);
			}
		}else {
			return new ResponseEntity<Visita>(new Visita(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Obtenemos una visita por la Obra
	 * @param obra
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Muestra una visita de la base de datos", response = Visita.class, tags = "getVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/obra/{obra}")
	public ResponseEntity<List<Visita>> getVisitaByObra(@PathVariable("obra") Long id) throws ResponseStatusException {
		if(id!=null) {
			try {
				List<Visita> visitabyObra = service.getVisitaPorObra(id);
				return new ResponseEntity<List<Visita>>(visitabyObra, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visitas de la obra no se han podido encontrar", e);
			}
		}else {
			return new ResponseEntity<List<Visita>>(new ArrayList<Visita>(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Obtenemos una visita segun la Fecha
	 * @param fecha
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Muestra una visita de la base de datos", response = Visita.class, tags = "getVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/fecha/{fecha}")
	public ResponseEntity<List<Visita>> getVisitaByDate(@PathVariable("fecha")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha)
			throws ResponseStatusException{
		if(fecha!=null) {
			try {
				List<Visita> visitabyDate = service.getVisitaPorFecha(fecha);
				return new ResponseEntity<List<Visita>>(visitabyDate, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita no se ha podido encontrar", e);
			}
		}else {
			return new ResponseEntity<List<Visita>>(new ArrayList<Visita>(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Borramos la Visita
	 * @param id
	 * @return ResponseEntity
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Borra una visita de la base de datos", response = Visita.class, tags = "deleteVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@DeleteMapping("/{id}")
	public ResponseEntity<Visita> deleteVisita(@PathVariable("id") Long id) throws ResponseStatusException {
		try {
			Optional<Visita> visita = Optional.of(service.getVisitaPorId(id));
			if(visita.isPresent()) {
				service.borrarVisita(id);
				return new ResponseEntity<Visita>(HttpStatus.OK);
			}else {
				return new ResponseEntity<Visita>(HttpStatus.NOT_FOUND);
			}
		} catch (ResponseStatusException e) {
			return new ResponseEntity<Visita>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Actualizamos la Visita
	 * @param updateVisita
	 * @param id
	 * @return ResponseEntity
	 */
	@ApiOperation(value = "Actualiza una visita de la base de datos", response = Visita.class, tags = "updateVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@PutMapping("/{id}")
	public ResponseEntity<Visita> updateVisita(@RequestBody Visita updateVisita, @PathVariable(value = "id") Long id) throws ResponseStatusException {
		if(updateVisita!=null&&id>-1) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizaVisita(updateVisita));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La visita no se han podido actualizar", e);
			}
		}else {
			return new ResponseEntity<Visita>(new Visita(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
