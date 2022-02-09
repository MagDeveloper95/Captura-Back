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
import org.springframework.web.server.ResponseStatusException;

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
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) throws ResponseStatusException{
		if(usuario!=null){
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(service.creaUsuario(usuario));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no ha sido guardado", e);
			} 
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}


	}
	
/**
 * Metodo que trae un usuario a través de un ID
 * @param id del usuario
 * @return Usuario que contiene la ID
 * @throws ResponseStatusException 
 */
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable(value ="id") Long id) throws ResponseStatusException{
		if(id!=null) {
			try {
				return ResponseEntity.status(HttpStatus.OK).body(service.getUsuarioById(id));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no ha sido encontrado", e);
			}
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * Metodo para actualizar un usuario
	 * @param Usuario
	 * @param id del usuario
	 * @return Usuario actualizado
	 * @throws ResponseStatusException
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> update(@RequestBody Usuario userDetails, @PathVariable(value="id")Long id) throws ResponseStatusException{
		if(id!=null) {
			try {
				Optional<Usuario> user = Optional.of(service.getUsuarioById(id));
				if(!user.isPresent()) {
					return ResponseEntity.notFound().build();
				}			
				//BeanUtils.copyProperties(userDetails, user.get());		
				return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizarUsuario(user.get()));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no ha podido ser actualizado", e);
			}
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}					
	}
	
	/**
	 * Metodo para borrar un usuario a traves de su ID
	 * @param id de usuario
	 * @return borra el usuario
	 * @throws ResponseStatusException
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> delete(@PathVariable(value="id")Long id) throws ResponseStatusException{
		if(id!=null) {
			try {
				System.out.println(id);
				Optional<Usuario> user = Optional.of(service.getUsuarioById(id));
				if(!user.isPresent()) {
					return ResponseEntity.notFound().build();
				}else {
					service.borrarUsuario(id);
					return new ResponseEntity<Usuario>(HttpStatus.OK);
				}
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no ha podido ser borrado", e);	
			}
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * Metodo que trae una lista de usuarios
	 * @return Lista de usuarios
	 * @throws ResponseStatusException
	 */
	@GetMapping
	public ResponseEntity<List<Usuario>> readAll() throws ResponseStatusException{
		try {
			List<Usuario> usuarios = service.getAllUsuarios();
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); 
		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuarios no encontradas", e);
		}
	}
	/**
	 * Metodo para buscar un usuario a partir de su nombre
	 * @param nombre de usuario
	 * @return usuario
	 * @throws ResponseStatusException
	 */
	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Usuario> listarPorNombre(@PathVariable(value ="nombre") String nombre) throws ResponseStatusException  {
		if(nombre!=null&&nombre.length()>0) {
			try {
				return ResponseEntity.status(HttpStatus.OK).body(service.getUsarioByNombre(nombre));
			} catch (ResponseStatusException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no ha sido encontrado", e);
			}
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El nombre de usuario no es correcto");
		}
	}
	
}
