package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.AppMain;
import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Registro;
import com.iesFrancisco.captura.Repositories.RegistroRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class RegistroService {
	
	private static final Logger logger = LogManager.getLogger(RegistroService.class);
	
	@Autowired // instanciar el repositorio
	RegistroRepository repository;
	/**
	 * M�todo del servicio que devuelve una lista con
	 * @return la lista de los registros 	
	 * @throws RecordNotFoundException en caso de que sea nulo
	 */
	public List<Registro> getAllRegistros() throws RecordNotFoundException {
		List<Registro> result = repository.findAll();
		if (result != null) {
			return result;
		} else {
			throw new RecordNotFoundException("No hay registros en la base de datos");
		}
	}
	/**
	 * M�todo del servicio que devuelve un registro introduciendo un id
	 * @param id del registro
	 * @return el registro
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Registro getRegistroById(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Registro> getRegistroDummy = repository.findById(id);
				if (getRegistroDummy.isPresent()) {
					return getRegistroDummy.get();
				} else {
					throw new RecordNotFoundException("Error ---> El registro con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * M�todo del servicio que devuelve una lista de registros dependiendo del usuario introducido
	 * @param id del usuario
	 * @return la lista con los registros del usuario
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
		
	public List<Registro> getRegistroPorUsuario(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<List<Registro>> getRegistroDummy = Optional.of(repository.getRegistroPorUsuario(id));
				if(getRegistroDummy != null) {					
					if (getRegistroDummy.isPresent()) {
						return getRegistroDummy.get();
					} else {
						throw new RecordNotFoundException("Error ---> El registro del usuario con id: " + id + " no existe");
					}
				}else {
					throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * Metodo del servicio que devuelve un registro introduciendo una fecha
	 * @param fecha por la que queremos filtrar
	 * @return una listra con los registro con esa fecha
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Registro> getRegistroPorFecha(LocalDate fecha)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (fecha != null) {
			try {
				Optional<List<Registro>> getRegistroFechaDummy = Optional.of(repository.getRegistroPorFecha(fecha));
				if(getRegistroFechaDummy != null) {					
					if (getRegistroFechaDummy.isPresent()) {
						return getRegistroFechaDummy.get();
					} else {
						throw new RecordNotFoundException("Error ---> El registro con fecha: " + fecha + " no existe");
					}
				}else {
					throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}
	/**
	 * M�todo del servicio que crea un nuevo Registro
	 * @param registro que queremos crear
	 * @return el registro
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Registro creaRegistro(Registro registro) throws NullPointerException, IllegalArgumentException {
		if (registro != null) {
			if (registro.getId() < 0 & registro != null) {
				try {
					return registro = repository.save(registro);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return registro;
			}
		} else {
			throw new NullPointerException("Error ---> El registro introducido tiene un valor nulo");
		}
	}
}