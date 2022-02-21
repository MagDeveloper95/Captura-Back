package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Visita;
import com.iesFrancisco.captura.Repositories.VisitaRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Service
public class VisitaService {

	private static final Logger logger = LoggerFactory.getLogger(VisitaService.class);


    @Autowired 
    VisitaRepository repository;
    
    /**
	 * Método del servicio que nos devolverá todas las visitas que tenemos
	 * guardados
	 * 
	 * @return la lista de vistas
	 */
	@ApiOperation(value = "Muestra todas las visitas",notes = "Devuelve todas las visitas")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public List<Visita> getAllVisitas() throws RecordNotFoundException {
		List<Visita> getAllVisitasDummy = repository.findAll();
		if (getAllVisitasDummy != null) {
			logger.info("Consulta exitosa en getAllVisitas");
			return getAllVisitasDummy;
		} else {
			logger.error("No hay fotos en la base de datos, en getAllFotos");
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
	@ApiOperation(value = "Muestra una visita por su id",notes = "Devuelve una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public Visita getVisitaPorId(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Visita> getVisitaDummy = repository.findById(id);
				System.out.println(getVisitaDummy);
				if (getVisitaDummy.isPresent()) {
					logger.info("Consulta exitosa en getVisitaByID");
					
					return getVisitaDummy.get();
				} else {
					logger.error("Error ---> La visita con id: " + id + " no existe en getVisitaByID");
					throw new RecordNotFoundException("Error ---> La visita con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getVisitaByID" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El id introducido tiene un valor nulo en getVisitaByID");
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
	@ApiOperation(value = "Muestra una visita por su obra",notes = "Devuelve una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public List<Visita> getVisitaPorObra(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<List<Visita>> getVisitasDummy = Optional.of(repository.getFotosPorObra(id));
				if (getVisitasDummy.isPresent()) {
					logger.info("Consulta exitosa en getVisitaPorObra");
					return getVisitasDummy.get();
				} else {
					logger.error("Error ---> La obra con id: " + id + " no tiene lista de visitas en getVisitaPorObra");
					throw new RecordNotFoundException("Error ---> La visita con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getVisitaPorObra" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> La obra introducido tiene un valor nulo getVisitaPorObra");
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo ");
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
	@ApiOperation(value = "Muestra una visita por su obra",notes = "Devuelve una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public List<Visita> getVisitaPorFecha(LocalDate fecha)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (fecha != null) {
			try {
				Optional<List<Visita>> getVisitasDummy = Optional.of(repository.getVisitasPorFecha(fecha));
				if (getVisitasDummy.isPresent()) {
					logger.info("Consulta exitosa en getVisitaPorFecha");
					return getVisitasDummy.get();
				} else {
					logger.error("Error ---> La visita con fecha: " + fecha + " no existe en getVisitaPorFecha");
					throw new RecordNotFoundException("Error ---> La visita con fecha: " + fecha + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getVisitaPorFecha :" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo en getVisitaPorFecha");
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
	@ApiOperation(value = "Crea o actualiza una visita",notes = "Crea o actualiza una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public Visita creaVisita(Visita visita) throws NullPointerException, IllegalArgumentException {
		if (visita != null) {	
			if (visita.getId() < 0 && visita!=null) {
				try {
					logger.info("Consulta exitosa en creaVisita");
					
					OneDriveService.createVisita(visita.getHeader(),visita.getObra().getNombre());
					visita.setHeader(LocalDate.now()+visita.getHeader());
					
					visita.setFecha(LocalDate.now());
					return visita = repository.save(visita);
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en creaVisita: "+ e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return actualizaVisita(visita);
			}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo en creaVisita");
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
	@ApiOperation(value = "Actualiza una visita",notes = "Actualiza una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public Visita actualizaVisita(Visita visita)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (visita != null) {
			Optional<Visita> getVisitaDummy = Optional.of(getVisitaPorId(visita.getId()));
				if (getVisitaDummy.isPresent()) {
					Visita actualizaVisitaDummy = getVisitaDummy.get();
					actualizaVisitaDummy.setId(visita.getId());
					actualizaVisitaDummy.setHeader(LocalDate.now()+visita.getHeader());
					actualizaVisitaDummy.setFecha(LocalDate.now());
					actualizaVisitaDummy.setNota(visita.getNota());
					actualizaVisitaDummy.setFotos(visita.getFotos());
					actualizaVisitaDummy.setObra(visita.getObra());
					try {
						logger.info("Consulta exitosa en actualizarVisita");
						//return repository.save(actualizaVisitaDummy);
						actualizaVisitaDummy = repository.save(actualizaVisitaDummy);
						return actualizaVisitaDummy;
						
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en actualizarVisita :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> La visita no existe", visita.getId() + " en ActualizarVisita");
					throw new RecordNotFoundException("Error ---> La visita no existe", visita.getId());
				}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo en ActualizarVisita");
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
	@ApiOperation(value = "Borra una visita",notes = "Borra una visita")
	@ApiResponses(value ={
		@ApiResponse(code = 200, message = "Peticion correcta", response = Visita.class),
		@ApiResponse(code = 404, message = "Error al obtener la obra"),
		@ApiResponse(code = 500, message = "Internal server error") })
	public void borrarVisita(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Visita> borrarVisitaDummy = Optional.of(getVisitaPorId(id));
				if (borrarVisitaDummy.isPresent()) {
					try {
						OneDriveService.borrarVisita(borrarVisitaDummy.get().getHeader(),
								borrarVisitaDummy.get().getObra().getNombre());
						logger.info("Consulta exitosa en borrarVisita");
						repository.deleteById(id);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> La visita no existe", id + " en borrarVisita");
					throw new RecordNotFoundException("Error ---> La visita no existe", id);
				}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo en borrarVisita");
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
		}
	}

}
