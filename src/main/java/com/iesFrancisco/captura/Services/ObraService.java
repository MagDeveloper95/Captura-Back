package com.iesFrancisco.captura.Services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Repositories.ObraRepository;


@Service
public class ObraService {
	
	private static final Logger logger = LoggerFactory.getLogger(ObraService.class);
	
	@Autowired
	ObraRepository obrasRepository;

	/**
	 * Metodo que devuelve todas las obras de la base de datos 
	 * @return List<Obra> lista de obras
	 * @throws RecordNotFoundException en caso de que no haya obras en la base de datos
	 */
	public List<Obra> getAllObras() throws RecordNotFoundException{
		List<Obra> result = obrasRepository.findAll();
		if (result!=null) {
			logger.info("Consulta exitosa en getAllObras");
			//RegistroService.creaRegistro(new Registro("GET ALL OBRAS"));
			return result;
		} else {
			logger.error("No hay Obras en la base de datos, en getAllObras");
			throw new RecordNotFoundException("No hay Obras en la base de datos");
		}

	}
	
	/**
	 * M�todo que devuelve una obra por su id
	 * @param id de la obra
	 * @return result, una obra
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra getObraById(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(id!=null) {
			try {
				Optional<Obra> result = obrasRepository.findById(id);
				/**ObraResponse obraResponse = new ObraResponse();
				obraResponse.setid(result.get().getId());*/
				if(result.isPresent()) {
					logger.info("Consulta exitosa en getObraByID");
					return result.get();
				}else {
					logger.error("Error ---> La Obra con id: " + id + " no existe en getObraByID");
					throw new RecordNotFoundException("La obra no existe", id);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getObraByID" + e);
				throw new IllegalArgumentException(e);
			}

		}else {
			logger.error("Error ---> El id introducido tiene un valor nulo en getObraByID");
			throw new NullPointerException("Error: El id introducido tiene un valor nulo");
		}

	}
	
	/**
	 * M�todo del servicio que crea un usuario y si existe nos lo actualiza
	 * @param obra
	 * @return obra creada/actualizada
	 * @throws NullPointerException sino encuentra el usuario
	 * @throws IllegalArgumentException si posee algun objeto nulo.
	 */
	public Obra creaObra(Obra obra) throws NullPointerException, IllegalArgumentException{
		if(obra!=null) {
			if(obra.getId() == -1) {
				try {
					OneDriveService.createObra(obra.getNombre());
					logger.info("Consulta exitosa en creaObra");
					obrasRepository.save(obra);
					return obra;
				} catch (IllegalArgumentException e) {
					logger.error("Error ---> IllegarArgumentException en creaObra: "+ e);
					throw new IllegalArgumentException(e);
				}
			}else {
				return actualizaObra(obra);
			}
		}else {
			logger.error("Error ---> La obra introducida tiene un valor nulo en creaObra");
			throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
		}
	}
	
	/**
	 * M�todo del servicio que actualizara la obra si existe en la base de datos
	 * @param obra que queremos actualizar
	 * @return obra actualizada
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra actualizaObra (Obra obra) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(obra!=null) {
			Optional<Obra> result = Optional.of(getObraById(obra.getId()));
					if(result.isPresent()) {
						OneDriveService.updateFolderName(obra.getNombre(),result.get().getNombre());
						Obra newObra = result.get();
						newObra.setId(obra.getId());
						newObra.setNombre(obra.getNombre());
						newObra.setLatitud(obra.getLatitud());
						newObra.setLongitud(obra.getLongitud());
						newObra.setDatos(obra.getDatos());
						newObra.setUsuario(obra.getUsuario());
						newObra.setVisita(obra.getVisita());
						try {
							logger.info("Consulta exitosa en actualizarObra");
							return obrasRepository.save(newObra);
						} catch (IllegalArgumentException e) {
							logger.error("Error ---> IllegarArgumentException en actualizarObra :" + e);
							throw new IllegalArgumentException(e);
						}
					}else {
						logger.error("Error ---> La obra no existe", obra.getId() + " en ActualizarObra");
						throw new RecordNotFoundException("La obra no existe", obra);
					}
			}else {
				logger.error("Error ---> La visita introducida tiene un valor nulo en ActualizarObra");	
				throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
			}

	}
	
	/**
	 * M�todo del servicio que borra una obra introducida por id
	 * @param id de la obra que queremos borrar
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public void deleteObraById(Long id) throws RecordNotFoundException, NullPointerException{
		if(id!=null) {
			Optional<Obra> obra = obrasRepository.findById(id);
				if(obra.isPresent()) {
					OneDriveService.borraObra(obra.get().getNombre());
					logger.info("Consulta exitosa en borrarObra");
					obrasRepository.deleteById(id);
				} else {
					logger.error("Error ---> La obra no existe", id + " en borrarObra");
					throw new RecordNotFoundException("La Obra no existe, por lo que no se puede borrar", id);
				}
		}else {
			logger.error("Error ---> La obra introducida tiene un valor nulo en deleteObraById");
			throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
		}
	}

	/**
	 * M�todo que devuelve las obras que tiene un usuario
	 * @param usuario
	 * @return una lista de obras
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Obra> getObraByUser(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(id!=null) {
			try {
				Optional<List<Obra>> lista = Optional.of(obrasRepository.findObrasByUser(id));
					if(lista.isPresent()) {
						logger.info("Consulta exitosa en getObraByUser");
						return lista.get();
					}else {
						logger.error("Error ---> el usuario con id: " + id + " no tiene lista de obras en getVisitaPorObra");
						throw new RecordNotFoundException("Los datos son erroneos", id);
					}			
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getObraByUser" + e);
				throw new IllegalArgumentException(e);
			}						
		}else {
			logger.error("Error ---> El usuario introducido tiene un valor nulo getVisitaPorObra");
			throw new NullPointerException ("Error: El usuario introducido tiene un valor nulo");
		}
			
	}
	
	/**
	 * M�todo que devuelve una obra por las coordenadas
	 * @param coordenadas
	 * @return obra en caso de que no encuentre la obra
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra getObraByLoc(float latitud,float longitud) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
			try {
				Optional<Obra> obra = Optional.of(obrasRepository.findObraByLatLong(latitud, longitud));
					if(obra.isPresent()) {
						logger.info("Consulta exitosa en getObraByLoc");
						return obra.get();
					}else {
						logger.error("Error ---> La obra con las coordenadas: " + latitud +" y "+ longitud+ " ,no tiene obra en getObraByLoc");
						throw new RecordNotFoundException("La obra no existe", obra);
					}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getObraByLoc " + e);
				throw new IllegalArgumentException(e);
		}	
	}
	
	/**
	 * M�todo para obtener una obra por su nombre
	 * @param nombre
	 * @return result, una obra
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra getObraByName(String nombre) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(nombre != null) {
			try {
				Optional<Obra> result = Optional.of(obrasRepository.findByName(nombre));
					if(result.isPresent()) {
						logger.info("Consulta exitosa en getObraByName");
						return result.get();
					}else {
						logger.error("Error ---> La obra con nombre: " + nombre + " , no tiene lista de visitas en getObraByName");
						throw new RecordNotFoundException("La obra no existe", nombre);
					}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getObraByName" + e);
				throw new IllegalArgumentException(e);
			}			
		}else {
			logger.error("Error ---> La obra introducido tiene un valor nulo getObraByName");
			throw new NullPointerException("Error: El nombre introducido tiene un valor nulo");
		}
	}
	

}
