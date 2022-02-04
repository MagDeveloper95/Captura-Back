package com.iesFrancisco.captura.Controllers;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Services.ObraService;

@RestController
@RequestMapping("/obra")
public class ObraController {

	@Autowired
	ObraService service;
	
	/**
	 * Método del controlador Crea una obra
	 * @param obra
	 * @return ResponseEntity
	 */ 
	@PostMapping("/guardar")
	public ResponseEntity<?> create(@RequestBody Obra obra){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdateObra(obra));
	}
	
	/**
	 * Trae una obra por su Id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable(value ="id") Long id){
		Optional<Obra> oObra = Optional.of(service.getObraById(id));
		if(!oObra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oObra);
	}
	/**
	 * Controlador que actualiza por ID una obra que ya esta creada
	 * @param obraDetails
	 * @param id
	 * @return obra
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Obra> update(@RequestBody Obra obraDetails, @PathVariable(value="id")Long id){
		Optional<Obra> obra = Optional.of(service.getObraById(id));
			if(!obra.isPresent()) {
				return ResponseEntity.notFound().build();
			}			
			//BeanUtils.copyProperties(obraDetails, obra.get());// Copiaria todo el objeto, aqui no interesa por el Id que no lo queremos actualizar
			obra.get().setDatos(obraDetails.getDatos());
			obra.get().setLatLong(obraDetails.getLatLong());
			obra.get().setNombre(obraDetails.getNombre());
			obra.get().setUsuario(obraDetails.getUsuario());
			obra.get().setVisita(obraDetails.getVisita());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdateObra(obra.get()));						
	}
	/**
	 * Metodo que borra una obra
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Obra> delete(@PathVariable(value="id")Long id){
		Optional<Obra> obra = Optional.of(service.getObraById(id));
		if(!obra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		service.deleteObraById(id);
		return ResponseEntity.ok().build();
	}	
	/**
	 * Metodo que trae todas las obras de la base de datos
	 * @return todas las obras
	 */
	@GetMapping
	public List<Obra> readAll(){
		List<Obra> obras = service.getAllObras();
		return obras;
	}
	/**
	 * Metodo que busca por nombre de la Obra
	 * @param name
	 * @return
	 */
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<?> listarPorNombre(@PathVariable(value ="nombre") String nombre){
		Optional<Obra> oObra = Optional.of(service.getObraByName(nombre));
		if(!oObra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oObra);
	}
	/**
	 * Metodo que busca las obras por usuario
	 * @param id
	 * @return obras del usuario
	 */
	@GetMapping("usuario/{usuarioId}")
	public ResponseEntity<?> listarPorUsuario(@PathVariable(value ="usuarioId") Long id){
		Optional<List<Obra>> oObra = Optional.of(service.getObraByUser(id));
		if(!oObra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oObra);
	}
	/**
	 * Metodo que devuelve una obra por sus coordenadas
	 * @param coordenadas
	 * @return obra
	 */
	@GetMapping("/coordenadas/{coordenadas}")
	public ResponseEntity<?> listarPorCoordenada(@PathVariable(value="coordenadas")Point2D coordenadas){
		Optional<Obra> oObra = Optional.of(service.getObraByLoc(coordenadas));
		if(!oObra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oObra);
	}
	
	
	
	
	
}
