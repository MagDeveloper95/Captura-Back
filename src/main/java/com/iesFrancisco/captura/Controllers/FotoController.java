package com.iesFrancisco.captura.Controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Model.FotoWrapper;
import com.iesFrancisco.captura.Services.FotoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "FotoController", description = "Operaciones sobre fotos")
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
	@ApiOperation(value = "Muestra todas las fotos dela base de datos", response = Iterable.class, tags = "allFotos")
	@CrossOrigin(origins = "http://localhost:8100")
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
	@ApiOperation(value = "Muestra una foto por su id", response = Foto.class, tags = "getFotoById")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/{id}")
	public ResponseEntity<Foto> getFotosById(@PathVariable(value = "id") Long id) throws ResponseStatusException {
		if(id!=null) {
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
	@ApiOperation(value = "Muestra las fotos por fecha", response = Iterable.class, tags = "getFotoByFecha")
	@CrossOrigin(origins = "http://localhost:8100")
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
	@ApiOperation(value = "Muestra las fotos por visita", response = Iterable.class, tags = "getFotoByVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/visita/{idVisita}")
	public ResponseEntity<List<Foto>> getFotosByVisita(@PathVariable("idVisita") Long idVisita) throws ResponseStatusException {
		if(idVisita!=null && idVisita>-1) {
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
	@ApiOperation(value = "Borra una foto por su id", response = Foto.class, tags = "deleteFoto")
	@CrossOrigin(origins = "http://localhost:8100")
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
	@ApiOperation(value = "Actualiza una foto por su id", response = Foto.class, tags = "updateFoto")
	@CrossOrigin(origins = "http://localhost:8100")
	@PutMapping("/{id}")
	public ResponseEntity<Foto> updateFoto(@RequestBody Foto updateFoto, @PathVariable(value = "id") Long id) throws ResponseStatusException {
		if(updateFoto!=null) {
			try {
				Optional<Foto> foto = Optional.of(updateFoto);
				if (!foto.isPresent()) {
					return ResponseEntity.notFound().build();
				}
				return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarFoto(foto.get()));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la foto no se ha podido actualizar", e);
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}


	}
	@ApiOperation(value = "Muestra las fotos por visita", response = Iterable.class, tags = "getFotoByVisita")
	@CrossOrigin(origins = "http://localhost:8100")
	@PostMapping("/add") 
	public ResponseEntity<Foto> create(@ModelAttribute FotoWrapper foto) throws ResponseStatusException, NullPointerException, IllegalArgumentException, IOException{
		if(foto!=null) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.creaFoto(foto));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la foto no se ha podido guardar", e);
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
