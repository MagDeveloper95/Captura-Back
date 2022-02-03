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

import com.iesFrancisco.captura.Model.Usuario;
import com.iesFrancisco.captura.Services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioService service;
	
	/**
	 * RELLENAR A PARTIR DE AQUI
	 */
	
	/**
	 * Método del controlador Crea un usuario
	 * @param usuario
	 * @return ResponseEntity
	 */ 
	@PostMapping("/guardar")
	public ResponseEntity<?> create(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdateUsuario(usuario));

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable(value ="id") Long id){
		Optional<Usuario> user = Optional.of(service.getUsuarioById(id));
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Usuario userDetails, @PathVariable(value="id")Long id){
		Optional<Usuario> user = Optional.of(service.getUsuarioById(id));
			if(!user.isPresent()) {
				return ResponseEntity.notFound().build();
			}			
			BeanUtils.copyProperties(userDetails, user.get());// Copiaria todo el objeto, aqui no interesa por el Id que no lo queremos actualizar

			
			return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdateUsuario(user.get()));						
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value="id")Long id){
		Optional<Usuario> user = Optional.of(service.getUsuarioById(id));
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		service.borrarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<Usuario> readAll(){
		List<Usuario> usuarios = service.getAllUsuarios();
		return usuarios;
	}
	
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<?> listarPorNombre(@PathVariable(value ="nombre") String name){
		Optional<Usuario> user = Optional.of(service.getUsarioByNombre(name));
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
}
