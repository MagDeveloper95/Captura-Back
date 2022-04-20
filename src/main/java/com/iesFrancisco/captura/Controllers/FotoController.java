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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.azure.core.annotation.Headers;
import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Model.FotoWrapper;
import com.iesFrancisco.captura.Services.FotoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiOperation(value = "Metodo que devuelve todas las fotos",
		 notes = "Metodo que devuelve todas las fotos de la base de datos", tags = "getAllFotos") 
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error obtener las fotos", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
	@CrossOrigin(
		methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
		@Headers({"Access-Control-Allow-Origin: *"})
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
	@ApiOperation(value = "Metodo que devuelve una foto", 
		notes = "Metodo que devuelve una foto de la base de datos", tags = "getFotoById")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = Foto.class),
			@ApiResponse(code = 404, message = "Error obtener la foto", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(
				origins = "*",methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
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
	@ApiOperation(value = "Muestra las fotos por fecha", 
		notes = "Muestra las fotos por fecha", tags = "getFotosByFecha")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error obtener las fotos", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(
				methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
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
	@ApiOperation(value = "Muestra las fotos asociadas a una visita",
	 	notes = "Muestra las fotos asociadas a una visita de la base de datos", tags = "getFotosByVisita")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error obtener las fotos", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(
				methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
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
	@ApiOperation(value = "Borrado de una foto", 
		notes = "Borra una foto de la base de datos", tags = "deleteFotoById")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = Foto.class),
			@ApiResponse(code = 404, message = "Error borrar la foto", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(
				methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
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
	@ApiOperation(value = "Actualiza una foto", 
		notes = "Actualiza una foto de la base de datos", tags = "updateFotoById")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = Foto.class),
			@ApiResponse(code = 404, message = "Error actualizar la foto", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(
				methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
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
	@ApiOperation(value = "Crea una foto", 
		notes = "Crea una foto en la base de datos y en OneDrive", tags = "createFoto")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operacion exitosa", response = Foto.class),
			@ApiResponse(code = 404, message = "Error crear la foto", response = ResponseStatusException.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ResponseStatusException.class)})
			@CrossOrigin(origins = "*",
				methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
  
	@PostMapping("/add") 
	public ResponseEntity<Foto> create(@ModelAttribute FotoWrapper foto,@RequestParam("file") MultipartFile file) throws ResponseStatusException, NullPointerException, IllegalArgumentException, IOException{
		if(foto!=null) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.creaFoto(foto,file));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la foto no se ha podido guardar", e);
			}
		}else {
			return new ResponseEntity<Foto>(new Foto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
