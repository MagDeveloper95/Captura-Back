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
import com.iesFrancisco.captura.Repositories.RegistroRepository;

@Service
public class RegistroService {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistroService.class);
	
	@Autowired // instanciar el repositorio
	static
	RegistroRepository repository;
	/**
	 * M�todo del servicio que devuelve una lista con
	 * @return la lista de los registros 	
	 * @throws RecordNotFoundException en caso de que sea nulo
	 */
	public List<Registro> getAllRegistros() throws RecordNotFoundException {
		List<Registro> result = repository.findAll();
		if (result != null) {
			logger.info("Consulta exitosa en getAllRegistros");
			return result;
		} else {
			logger.error("No hay visitas en la base de datos, en getAllVisitas");
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
					logger.info("Consulta exitosa en getRegistroByID");
					return getRegistroDummy.get();
				} else {
					logger.error("Error ---> El registro con id: " + id + " no existe en getRegistroByID");
					throw new RecordNotFoundException("Error ---> El registro con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getRegistroByID");
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El id introducido tiene un valor nulo en getRegistroByID");
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
					if (getRegistroDummy.isPresent()) {
						logger.info("Consulta exitosa en getRegistroPorUsuario");
						return getRegistroDummy.get();
					} else {
						logger.error("Error ---> El Usuario con id: " + id + " no existe en getRegistroPorUsuario");
						throw new RecordNotFoundException("Error ---> El registro del usuario con id: " + id + " no existe");
					}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getRegistroPorUsuario" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo getRegistroPorUsuario");
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
					if (getRegistroFechaDummy.isPresent()) {
						logger.info("Consulta exitosa en getRegistroPorFecha");
						return getRegistroFechaDummy.get();
					} else {
						logger.error("Error ---> El registro con fecha: " + fecha + " no existe en getRegistroPorFecha");
						throw new RecordNotFoundException("Error ---> El registro con fecha: " + fecha + " no existe");
					}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getRegistroPorFecha :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El registro introducido tiene un valor nulo en getRegistroPorFecha");
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
	 public static Registro creaRegistro(Registro registro) throws NullPointerException, IllegalArgumentException {
		if (registro != null&&registro.getId() < 0) {
				try {
					logger.info("Consulta exitosa en creaRegistro");
					return registro = repository.save(registro);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en creaRegistro: "+ e);
					throw new IllegalArgumentException(e);
				}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo o ya esta creado en creaRegistro: " + registro.getId()+" :" + registro);
			throw new NullPointerException("Error ---> El registro introducido tiene un valor nulo o ya esta creado");
		}
	}
}