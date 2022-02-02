package com.iesFrancisco.captura.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
	 * Crea un usuario
	 * @param obra
	 * @return ResponseEntity
	 */ 
	@PostMapping("/guardar")
	public ResponseEntity<?> create(@RequestBody Obra obra){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdateObra(obra));
		//Obra newObra = service.createOrUpdateObra(obra);
		//return ResponseEntity.ok(newObra);
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
	 * 
	 * @param obraDetails
	 * @param idO
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Obra obraDetails, @PathVariable(value="id")Long id){
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value="id")Long id){
		Optional<Obra> obra = Optional.of(service.getObraById(id));
		if(!obra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		service.deleteObraById(id);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping
	public List<Obra> readAll(){
		List<Obra> obras = service.getAllObras();
		return obras;
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<?> listarPorNombre(@PathVariable(value ="name") String name){
		Optional<Obra> oObra = Optional.of(service.getObraByName(name));
		if(!oObra.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oObra);
	}
	
	
	
}
