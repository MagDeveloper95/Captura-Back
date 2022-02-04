package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Repositories.FotoRepository;

@Service
public class FotoService {

	@Autowired // instanciar el repositorio
	FotoRepository repository;

	/**
	 * Método del servicio que nos devolverá todas las fotos que tenemos guardados
	 * 
	 * @return la lista de fotos
	 */
	public List<Foto> getAllFotos() throws RecordNotFoundException {
		List<Foto> result = repository.findAll();
		if (result != null) {
			return result;
		} else {
			throw new RecordNotFoundException("No hay registros en la base de datos");
		}
	}

	/**
	 * M�todo del servicio que devuelve una Foto introduciendo un id
	 * 
	 * @param id de la foto
	 * @return la foto
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Foto getFotoPorId(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Foto> getFotoDummy = repository.findById(id);
				if (getFotoDummy.isPresent()) {
					return getFotoDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La foto con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}

	/**
	 * M�todo del servicio que devuelve una Foto introduciendo una Visita
	 * 
	 * @param id de la visita
	 * @return la foto
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Foto> getFotosPorVisita(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<List<Foto>> getFotosDummy = Optional.of(repository.getFotosPorVisita(id));
				if (getFotosDummy.isPresent()) {
					return getFotosDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La foto con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}

	/**
	 * M�todo del servicio que devuelve una Foto introduciendo una fecha
	 * 
	 * @param fecha
	 * @return La foto
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Foto> getFotosPorFecha(LocalDate fecha)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (fecha != null) {
			try {
				Optional<List<Foto>> getFotosDummy = Optional.of(repository.getFotosPorFecha(fecha));
				if (getFotosDummy.isPresent()) {
					return getFotosDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La foto con fecha: " + fecha + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> La foto introducida tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que nos creará una foto y en caso de que exista nos lo
	 * actualiza
	 * 
	 * @param foto que queremos crear
	 * @return foto creada/actualizada
	 * @throws RecordNotFoundException en caso de que no encuentre el usuario
	 * @throws NullPointerException    en caso de que algún objeto sea null
	 */
	public Foto creaUsuario(Foto foto) throws NullPointerException, IllegalArgumentException {
		if (foto != null) {
			if (foto.getId() < 0 & foto != null) {
				try {
					return foto = repository.save(foto);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return actualizarFoto(foto);
			}
		} else {
			throw new NullPointerException("Error ---> La foto introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que usaremos para actualizar un foto que exista.
	 * 
	 * @param foto que queremos actualizar
	 * @return el foto actualizado
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Foto actualizarFoto(Foto foto)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (foto != null) {
			Optional<Foto> getFotoDummy = Optional.of(getFotoPorId(foto.getId()));
			if (getFotoDummy != null) {
				if (!getFotoDummy.isPresent()) {
					Foto actualizarFotoDummy = getFotoDummy.get();
					actualizarFotoDummy.setId(foto.getId());
					actualizarFotoDummy.setUrl(foto.getUrl());
					actualizarFotoDummy.setComentario(foto.getComentario());
					actualizarFotoDummy.setVisita(foto.getVisita());
					try {
						return repository.save(actualizarFotoDummy);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> La foto no existe", foto.getId());
				}
			} else {
				throw new NullPointerException("Error ---> La foto optional tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> La foto introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que borra una foto introducido por id
	 * 
	 * @param id de la foto que queremos borrar
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	/*public void borrarFotoPorId(Long id)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Foto> borrarFotoDummy = Optional.of(getFotoPorId(id));
			if (borrarFotoDummy != null) {
				if (!borrarFotoDummy.isPresent()) {
					try {
						repository.deleteById(id);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> La foto no existe", id);
				}
			} else {
				throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}*/

	public void borrarFoto(Long id) throws RecordNotFoundException {
		Optional<Foto> foto = repository.findById(id);
		try {
			if (foto.isPresent()) {
				repository.deleteById(id);

			} else {
				throw new RecordNotFoundException("La foto no existe", id);
			}
		} catch (IllegalArgumentException e ) {
			throw new IllegalArgumentException(e);
		}

	}
}
