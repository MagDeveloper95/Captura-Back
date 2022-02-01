package com.iesFrancisco.captura.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Usuario;
import com.iesFrancisco.captura.Repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired // instanciar el repositorio
	UsuarioRepository repository;

	/**
	 * Método del servicio que nos devolverá todos los usuarios que tenemos
	 * guardados
	 * 
	 * @return la lista de usuarios
	 */
	public List<Usuario> getAllUsuarios() throws RecordNotFoundException {
		List<Usuario> getAllUsuariosDummy = repository.findAll();
		if (getAllUsuariosDummy != null) {
			return getAllUsuariosDummy;
		} else {
			throw new RecordNotFoundException("No hay registros en la base de datos");
		}
	}

	/**
	 * Método del servicio que nos devolverá un usuario por id
	 * 
	 * @param id del usuario que queremos buscar
	 * @return el usuario
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Usuario getUsuarioById(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Usuario> getUsuarioDummy = repository.findById(id);
				if (getUsuarioDummy.isPresent()) {
					return getUsuarioDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> El usuario con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	
	public Usuario getUsarioByNombre(String nombre)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (nombre != null) {
			try {
				Optional<Usuario> getUsuarioDummy = Optional.of(repository.findByNombre(nombre));
				if(getUsuarioDummy != null) {					
					if (getUsuarioDummy.isPresent()) {
						return getUsuarioDummy.get();
					} else {
						throw new RecordNotFoundException("Error ---> El usuario con nombre: " + nombre + " no existe");
					}
				}else {
					throw new NullPointerException("Error ---> El nombre introducido tiene un valor nulo");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El nombre introducido tiene un valor nulo");
		}
	}
	
	/**
	 * Método del servicio que nos creará un usuario y en caso de que exista nos lo
	 * actualiza
	 * 
	 * @param usuario que queremos crear
	 * @return el usuario creado/actualizado
	 * @throws RecordNotFoundException en caso de que no encuentre el usuario
	 * @throws NullPointerException    en caso de que algún objeto sea null
	 */
	public Usuario creaUsuario(Usuario usuario) throws NullPointerException, IllegalArgumentException {
		if (usuario != null) {
			if (usuario.getId() < 0 & usuario != null) {
				try {
					return usuario = repository.save(usuario);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return actualizarUsuario(usuario);
			}
		} else {
			throw new NullPointerException("Error ---> El usuario introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que usaremos para actualizar un usuario que exista.
	 * 
	 * @param usuario que queremos actualizar
	 * @return el usuario actualizado
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Usuario actualizarUsuario(Usuario usuario)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (usuario != null) {
			Optional<Usuario> getUsuarioDummy = Optional.of(getUsuarioById(usuario.getId()));
			if (getUsuarioDummy != null) {
				if (!getUsuarioDummy.isPresent()) {
					Usuario actualizaUsuarioDummy = getUsuarioDummy.get();
					actualizaUsuarioDummy.setId(usuario.getId());
					actualizaUsuarioDummy.setNombre(usuario.getNombre());
					actualizaUsuarioDummy.setEmail(usuario.getEmail());
					actualizaUsuarioDummy.setKey(usuario.getKey());
					actualizaUsuarioDummy.setFoto(usuario.getFoto());
					actualizaUsuarioDummy.setDatos(usuario.getDatos());
					actualizaUsuarioDummy.setRegistro(usuario.getRegistro());
					actualizaUsuarioDummy.setObra(usuario.getObra());
					try {
						return repository.save(actualizaUsuarioDummy);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> El usuario no existe", usuario.getId());
				}
			} else {
				throw new NullPointerException("Error ---> El usuario optional tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> El usuario introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que borra un usuario introducido por id
	 * 
	 * @param id del usuario que queremos borrar
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public void borrarUsuario(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Usuario> borrarUsuarioDummy = Optional.of(getUsuarioById(id));
			if (borrarUsuarioDummy != null) {
				if (!borrarUsuarioDummy.isPresent()) {
					try {
						repository.deleteById(id);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> El usuario no existe", id);
				}
			} else {
				throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
}
