package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Model.Usuario;
import com.iesFrancisco.captura.Repositories.UsuarioRepository;


@Service
public class UsuarioService {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

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
			logger.info("Consulta exitosa en getAllUsuarios");
			//RegistroService.creaRegistro(new Registro("GET ALL OBRAS", LocalDate.now()));
			return getAllUsuariosDummy;
		} else {
			logger.error("No hay usuarios en la base de datos en getAllUsuarios");
			throw new RecordNotFoundException("No hay usuarios en la base de datos");
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
					logger.info("Consulta exitosa en getUsuarioByID");
					//RegistroService.creaRegistro(new Registro("GET ALL OBRAS", LocalDate.now(), getUsuarioDummy.get()));
					return getUsuarioDummy.get();
				} else {
					logger.error("Error ---> El usuario con id: " + id + " no existe en getUsuarioByID");
					throw new RecordNotFoundException("Error ---> El usuario con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getUsuarioByID :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El id introducido tiene un valor nulo en getUsuarioByID");
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	
	/**
	 * Metodo que devuelve un usuario a partir de su nombre
	 * @param nombre
	 * @return
	 * @throws RecordNotFoundException en caso de que no encuentre al usuario
	 * @throws NullPointerException en el caso de algun objeto a NULL
	 * @throws IllegalArgumentException 
	 */
	public Usuario getUsarioByNombre(String nombre)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (nombre != null) {
			try {
				Optional<Usuario> getUsuarioDummy = Optional.of(repository.findByNombre(nombre));
				if(getUsuarioDummy != null) {					
					if (getUsuarioDummy.isPresent()) {
						logger.info("Consulta exitosa en getUsuarioByNombre");
						return getUsuarioDummy.get();
					} else {
						logger.error("Error ---> El usuario con id: " + nombre + " no existe en getUsuarioByID");
						throw new RecordNotFoundException("Error ---> El usuario con nombre: " + nombre + " no existe");
						
					}
				}else {
					throw new NullPointerException("Error ---> El nombre introducido tiene un valor nulo");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getUsuarioByNombre" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El nombre introducido tiene un valor nulo en getUsuarioByNombre");
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
			if (usuario.getId() == -1) {
				try {
					logger.info("Consulta exitosa en creaUsuario");
					return usuario = repository.save(usuario);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en creaUsuario: "+ e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return actualizarUsuario(usuario);
			}
		} else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo en creaUsuario");
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
				if (getUsuarioDummy.isPresent()) {
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
						logger.info("Consulta exitosa en actualizarUsuario");
						return repository.save(actualizaUsuarioDummy);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en actualizarUsuario :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> El usuario no existe", usuario.getId() + " en ActualizarUsuario");
					throw new RecordNotFoundException("Error ---> El usuario no existe: ", usuario.getId() + " en ActualizarUsuario");
				}
		} else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo en ActualizarUsuario");
			throw new NullPointerException("Error ---> El usuario introducido tiene un valor nulo en ActualizarUsuario");
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
				if (borrarUsuarioDummy.isPresent()) {
					try {
						logger.info("Consulta exitosa en borrarUsuario");
						repository.deleteById(id);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> El usuario no existe", id + " en borrarUsuario");
					throw new RecordNotFoundException("Error ---> El usuario no existe", id + " en borrarUsuario");
				}
			} else {
				logger.error("Error ---> El usuario introducido tiene un valor nuloen borrarUsuario");
				throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
			}
		} else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo en borrarUsuario");
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * Método del servicio que crea un usuario o lo actualiza
	 * @param usuario que queremos crear o actualizar
	 * @return el usuario creado o actualizado
	 * @throws RecordNotFoundException en caso de que no encuentre el usuario 
	 * @throws NullPointerException en caso de que algún objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Usuario createOrUpdateUsuario (Usuario usuario) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
	if(usuario!=null) {
		if(usuario.getId()>0) {
			Optional<Usuario> user = repository.findById(usuario.getId());
			if(user.isPresent()) {//UPDATE
				Usuario newUser = user.get();
				newUser.setId(usuario.getId());
				newUser.setDatos(usuario.getDatos());
				newUser.setEmail(usuario.getEmail());
				newUser.setNombre(usuario.getNombre());
				newUser.setFoto(usuario.getFoto());
				newUser.setKey(usuario.getKey());
				newUser = repository.save(newUser);
				return newUser;
			}else {//INSERT
				usuario.setId(null);
				usuario=repository.save(usuario);
				return usuario;
			}
		}else {
				usuario=repository.save(usuario);
				return usuario;
		}
	}else {
		throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
	}
	}
	
	/**
	 * Metodo que devuelve las obras que tiene un usuario
	 * @param usuario
	 * @return una lista de obras
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Usuario> getUserByObra(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(id!=null) {
			try {
				Optional<List<Usuario>> lista = Optional.of(repository.findUsersByObra(id));
					if(lista.isPresent()) {
						logger.info("Consulta exitosa en getUserByObra");
						return lista.get();
					}else {
						logger.error("Error ---> la obra con id: " + id + " no tiene lista de usuarios en getUserByObra");
						throw new RecordNotFoundException("Los datos son erroneos", id);
					}			
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getUserByObra" + e);
				throw new IllegalArgumentException(e);
			}						
		}else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo getUserByObra");
			throw new NullPointerException ("Error: El usuario introducido tiene un valor nulo");
		}
			
	}
}
