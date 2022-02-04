package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.PostUpdate;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Services.VisitaService;

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

	@GetMapping()
	public ResponseEntity<List<Visita>> getAllVisitas() {
		List<Visita> allVisita = service.getAllVisitas();
		return new ResponseEntity<List<Visita>>(allVisita, new HttpHeaders(), HttpStatus.OK);
	}
	/**
	 * Nos traemos la visica con la id que nosotros queramos
	 * @param id
	 * @return ResponseEntity
	 */

	@GetMapping("/{id}")
	public ResponseEntity<Visita> getVisitaById(@PathVariable("id") Long id) {
		Visita visitaById = service.getVisitaPorId(id);
		return new ResponseEntity<Visita>(visitaById, new HttpHeaders(), HttpStatus.OK);
	}
	/**
	 * Creamos una visita
	 * @param visita
	 * @return ResponseEntity
	 */

	@PostMapping
	public ResponseEntity<Visita> createVisita(@Valid @RequestBody Visita visita) {
		Visita addVisita = service.creaVisita(visita);
		return new ResponseEntity<Visita>(addVisita, new HttpHeaders(), HttpStatus.OK);
	}
	/**
	 * Obtenemos una visita por la Obra
	 * @param obra
	 * @return ResponseEntity
	 */

	@GetMapping("/obra/{obra}")
	public ResponseEntity<List<Visita>> getVisitaByObra(@PathVariable("obra") Obra obra) {
		List<Visita> visitabyObra = service.getVisitaPorObra(obra.getId());
		return new ResponseEntity<List<Visita>>(visitabyObra, new HttpHeaders(), HttpStatus.OK);

	}
	/**
	 * Obtenemos una visita segun la Fecha
	 * @param fecha
	 * @return ResponseEntity
	 */

	@GetMapping("/{fecha}")
	public ResponseEntity<List<Visita>> getVisitaByDate(@PathVariable("fecha")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fecha) {
		List<Visita> visitabyDate = service.getVisitaPorFecha(fecha);
		return new ResponseEntity<List<Visita>>(visitabyDate, new HttpHeaders(), HttpStatus.OK);
	}
	/**
	 * Borramos la Visita
	 * @param id
	 * @return ResponseEntity
	 * @throws RecordNotFoundException
	 */

	@DeleteMapping("/{id}")
	public HttpStatus deleteVisita(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.borrarVisita(id);
		return HttpStatus.OK;
	}
	/**
	 * Actualizamos la Visita
	 * @param updateVisita
	 * @param id
	 * @return ResponseEntity
	 */

	@PostUpdate
	public ResponseEntity<Visita> updateVisita(@RequestBody Visita updateVisita, @PathVariable(value = "id") Long id) {
		Optional<Visita> visita = Optional.of(service.getVisitaPorId(id));
		if (!visita.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		visita.get().setHeader(updateVisita.getHeader());
		visita.get().setFecha(updateVisita.getFecha());
		visita.get().setNota(updateVisita.getNota());
		visita.get().setFotos(updateVisita.getFotos());
		visita.get().setObra(updateVisita.getObra());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizaVisita(visita.get()));

	}
}
