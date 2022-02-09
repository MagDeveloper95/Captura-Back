package com.iesFrancisco.captura.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.server.ResponseStatusException;

import com.iesFrancisco.captura.Model.Foto;
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
	public ResponseEntity<List<Foto>> allFotos() throws ResponseStatusException {
		try {
			List<Foto> allFoto = service.getAllFotos();
		return new ResponseEntity<List<Foto>>(allFoto, new HttpHeaders(), HttpStatus.OK);
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fotos no encontradas", e);	
	}
	}

	/**
	 * Nos traemos las Fotos, esta vez por la id seleccionada
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Foto> getFotosById(@PathVariable(value = "id") Long id) throws ResponseStatusException {
		if(id!=null && id>-1) {
			try {
				Foto fotoById = service.getFotoPorId(id);
				return new ResponseEntity<Foto>(fotoById, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto no encontrada", e);	
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}


	}

	/**
	 * Nos devuelve las Fotos por la fecha
	 * 
	 * @param fecha
	 * @return ResponseEntity
	 */
	
	@GetMapping("/fecha/{fecha}")
	public ResponseEntity<List<Foto>> getFotosByDate(@RequestParam("fecha") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha) throws ResponseStatusException {
		if(fecha!=null) {
			try {
				List<Foto> fotoByDate = service.getFotosPorFecha(fecha);
				return new ResponseEntity<List<Foto>>(fotoByDate, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto no encontrada", e);
			}
		}else {
			return new ResponseEntity<List<Foto>>(new ArrayList<Foto>(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Nos devuelve las Fotos por las Visitas
	 * 
	 * @param visita
	 * @return ResponseEntity
	 */
	
	@GetMapping("/{idVisita}")
	public ResponseEntity<List<Foto>> getFotosByVisita(@PathVariable("idVisita") Long idVisita) throws ResponseStatusException {
		if(idVisita!=null&&idVisita>-1) {
			try {
				List<Foto> fotoByVisita = service.getFotosPorVisita(idVisita);
				return new ResponseEntity<List<Foto>>(fotoByVisita, new HttpHeaders(), HttpStatus.OK);
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Las fotos de la visita no se han podido encontrar", e);
			}
		}else {
			return new ResponseEntity<List<Foto>>(new ArrayList<Foto>(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Metodo que borra una foto mediante su id
	 * @param id
	 * @return foto eliminada
	 * @throws ResponseStatusException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Foto> deleteFoto(@PathVariable("id") Long id) throws ResponseStatusException {
		if(id != null && id > -1) {
			try {
				service.borrarFoto(id);
				return ResponseEntity.ok().build();
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto no encontrada", e);
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Foto no encontrada por ID");
		}
	}
	
	/**
	 * Actualiza la foto
	 * @param updateFoto
	 * @param id
	 * @return ResponseEntity
	 */
	@PostUpdate
	public ResponseEntity<Foto> updateFoto(@RequestBody Foto updateFoto, @PathVariable(value = "id") Long id) throws ResponseStatusException {
		if(updateFoto!=null) {
			try {
				Optional<Foto> foto = Optional.of(service.getFotoPorId(id));
				if (!foto.isPresent()) {
					return ResponseEntity.notFound().build();
				}
				/**oto.get().setUrl(updateFoto.getUrl());
				foto.get().setComentario(updateFoto.getComentario());
				foto.get().setVisita(updateFoto.getVisita());*/
				return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarFoto(foto.get()));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la foto no se ha podido actualizar", e);
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}


	}
	
	@PostMapping("/add") 
	public ResponseEntity<Foto> create(@RequestBody  Foto foto) throws ResponseStatusException{
		if(foto!=null) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.creaUsuario(foto));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la foto no se ha podido guardar", e);
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
}
