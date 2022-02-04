package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PostUpdate;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Services.FotoService;

@RestController
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	FotoService service;

	/**
	 * Nos traemos todas las Fotos
	 * 
	 * @param Foto
	 * @return ResponseEntity
	 */

	@GetMapping()
	public ResponseEntity<List<Foto>> allFotos() {
		List<Foto> allFoto = service.getAllFotos();
		return new ResponseEntity<List<Foto>>(allFoto, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Nos traemos las Fotos, esta vez por la id seleccionada
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/foto/{id}")
	public ResponseEntity<Foto> getFotosById(@PathVariable("id") Long id) {
		Foto fotoById = service.getFotoPorId(id);
		return new ResponseEntity<Foto>(fotoById, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Nos devuelve las Fotos por la fecha
	 * 
	 * @param fecha
	 * @return ResponseEntity
	 */
	
	@GetMapping("/fecha/{fecha}")
	public ResponseEntity<List<Foto>> getFotosByDate(@RequestParam("fecha")@DateTimeFormat(pattern = "yyy-MM-dd") LocalDate fecha) {
		List<Foto> fotoByDate = service.getFotosPorFecha(fecha);
		return new ResponseEntity<List<Foto>>(fotoByDate, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Nos devuelve las Fotos por las Visitas
	 * 
	 * @param visita
	 * @return ResponseEntity
	 */
	
	@GetMapping("/{visita}")
	public ResponseEntity<List<Foto>> getFotosByVisita(@PathVariable("visita") Visita visita) {
		List<Foto> fotoByVisita = service.getFotosPorVisita(visita.getId());
		return new ResponseEntity<List<Foto>>(fotoByVisita, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public HttpStatus deleteFoto(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.borrarFoto(id);
		return HttpStatus.OK;
	}
	
	
	/**
	 * Actualiza la foto
	 * @param updateFoto
	 * @param id
	 * @return ResponseEntity
	 */

	@PostUpdate
	public ResponseEntity<Foto> updateFoto(@RequestBody Foto updateFoto, @PathVariable(value = "id") Long id) {
		Optional<Foto> foto = Optional.of(service.getFotoPorId(id));
		if (!foto.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		foto.get().setUrl(updateFoto.getUrl());
		foto.get().setComentario(updateFoto.getComentario());
		foto.get().setVisita(updateFoto.getVisita());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarFoto(foto.get()));
	}
	
	@PostMapping("/add") 
	public ResponseEntity<Foto> create(@RequestBody  Foto foto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.creaUsuario(foto));

	}
}
