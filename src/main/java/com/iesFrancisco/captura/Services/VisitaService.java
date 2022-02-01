package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Usuario;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Repositories.VisitaRepository;

@Service
public class VisitaService {

    @Autowired 
    VisitaRepository repository;
    
    /**
	 * Método del servicio que nos devolverá todas las visitas que tenemos
	 * guardados
	 * 
	 * @return la lista de vistas
	 */
	public List<Visita> getAllVisitas() throws RecordNotFoundException {
		List<Visita> getAllVisitasDummy = repository.findAll();
		if (getAllVisitasDummy != null) {
			return getAllVisitasDummy;
		} else {
			throw new RecordNotFoundException("No hay visitas en la base de datos");
		}
	}
	/**
	 * M�todo del servicio que devuelve una Visita introduciendo un id
	 * @param id de la visita
	 * @return la visita
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Visita getVisitaPorId(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Visita> getVisitaDummy = repository.findById(id);
				if (getVisitaDummy.isPresent()) {
					return getVisitaDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La visita con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * M�todo del servicio que devuelve una Visita introduciendo una obra
	 * @param id de la obra
	 * @return la visita
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Visita> getVisitaPorObra(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<List<Visita>> getVisitasDummy = Optional.of(repository.getFotosPorObra(id));
				if (getVisitasDummy.isPresent()) {
					return getVisitasDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La visita con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * M�todo del servicio que devuelve una Visita introduciendo una obra
	 * @param id de la obra
	 * @return la visita
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Visita> getVisitaPorFecha(LocalDate fecha)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (fecha != null) {
			try {
				Optional<List<Visita>> getVisitasDummy = Optional.of(repository.getVisitasPorFecha(fecha));
				if (getVisitasDummy.isPresent()) {
					return getVisitasDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> La visita con fecha: " + fecha + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> La fecha introducido tiene un valor nulo");
		}
	}
	/**
	 * Método del servicio que nos creará una visita y en caso de que exista nos lo
	 * actualiza
	 * 
	 * @param visita que queremos crear
	 * @return el visita creado/actualizado
	 * @throws RecordNotFoundException en caso de que no encuentre el usuario
	 * @throws NullPointerException    en caso de que algún objeto sea null
	 */
	public Visita creaVisita(Visita visita) throws NullPointerException, IllegalArgumentException {
		if (visita != null) {
			if (visita.getId() < 0) {
				try {
					return visita = repository.save(visita);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return actualizaVisita(visita);
			}
		} else {
			throw new NullPointerException("Error ---> La visita introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que usaremos para actualizar una visita que exista.
	 * 
	 * @param visita que queremos actualizar
	 * @return visita actualizada
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Visita actualizaVisita(Visita visita)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (visita != null) {
			Optional<Visita> getVisitaDummy = Optional.of(getVisitaPorId(visita.getId()));
			if (getVisitaDummy != null) {
				if (!getVisitaDummy.isPresent()) {
					Visita actualizaVisitaDummy = getVisitaDummy.get();
					actualizaVisitaDummy.setId(visita.getId());
					actualizaVisitaDummy.setHeader(visita.getHeader());
					actualizaVisitaDummy.setFecha(visita.getFecha());
					actualizaVisitaDummy.setFecha(visita.getFecha());
					actualizaVisitaDummy.setNota(visita.getNota());
					actualizaVisitaDummy.setFotos(visita.getFotos());
					actualizaVisitaDummy.setObra(visita.getObra());
					try {
						return repository.save(actualizaVisitaDummy);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> La visita no existe", visita.getId());
				}
			} else {
				throw new NullPointerException("Error ---> La visita optional tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> La visita introducido tiene un valor nulo");
		}
	}

	/**
	 * Método del servicio que borra una visita introducido por id
	 * 
	 * @param id de la visita que queremos borrar
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public void borrarVisita(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Visita> borrarVisitaDummy = Optional.of(getVisitaPorId(id));
			if (borrarVisitaDummy != null) {
				if (!borrarVisitaDummy.isPresent()) {
					try {
						repository.deleteById(id);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					throw new RecordNotFoundException("Error ---> La visita no existe", id);
				}
			} else {
				throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
}
