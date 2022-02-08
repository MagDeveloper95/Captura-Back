package com.iesFrancisco.captura.Services;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Repositories.ObraRepository;


@Service
public class ObraService {
	
	@Autowired
	ObraRepository obrasRepository;
	
	/**
	 * Devuelve todas las obras de la base de datos
	 * 
	 * @return result , lista de usuarios
	 */
	public List<Obra> getAllObras() throws RecordNotFoundException{
		List<Obra> result = obrasRepository.findAll();
		if (result!=null) {
			return result;
		} else {
			throw new RecordNotFoundException("No hay Obras en la base de datos");
		}

	}
	
	/**
	 * Método que devuelve una obra por su id
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
				if(result.isPresent()) {
					return result.get();
				}else {
					throw new RecordNotFoundException("La obra no existe", id);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}

		}else {
			throw new NullPointerException("Error: El id introducido tiene un valor nulo");
		}

	}
	
	/**
	 * Método del servicio que crea un usuario y si existe nos lo actualiza
	 * @param obra
	 * @return obra creada/actualizada
	 * @throws NullPointerException sino encuentra el usuario
	 * @throws IllegalArgumentException si posee algun objeto nulo.
	 */
	public Obra creaObra(Obra obra) throws NullPointerException, IllegalArgumentException{
		if(obra!=null) {
			if(obra.getId()<0) {
				try {
					//String name = obra.getId().toString()+obra.getNombre();
					//System.out.println(name);
					//OneDriveService.createFolder(name);
					return obra = obrasRepository.save(obra);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}else {
				return actualizaObra(obra);
			}
		}else {
			throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
		}
	}
	
	/**
	 * Método del servicio que actualizara la obra si existe en la base de datos
	 * @param obra que queremos actualizar
	 * @return obra actualizada
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra actualizaObra (Obra obra) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(obra!=null) {
			Optional<Obra> result = Optional.of(getObraById(obra.getId()));
				if(result!=null) {
					if(result.isPresent()) {
						Obra newObra = result.get();
						newObra.setId(obra.getId());
						newObra.setNombre(obra.getNombre());
						newObra.setLatitud(obra.getLatitud());
						newObra.setLongitud(obra.getLongitud());
						newObra.setDatos(obra.getDatos());
						newObra.setUsuario(obra.getUsuario());
						System.out.println(obra.getUsuario());
						newObra.setVisita(obra.getVisita());
						try {
							return obrasRepository.save(newObra);
						} catch (IllegalArgumentException e) {
							throw new IllegalArgumentException(e);
						}
					}else {
						throw new RecordNotFoundException("La obra no existe", obra);
					}
				}else {
					throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
				}
		}else {
			throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
		}

	}
	
	/**
	 * Método del servicio que borra una obra introducida por id
	 * @param id de la obra que queremos borrar
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public void deleteObraById(Long id) throws RecordNotFoundException, NullPointerException{
		if(id!=null) {
			Optional<Obra> obra = obrasRepository.findById(id);
			if(obra!=null) {
				if(obra.isPresent()) {
					obrasRepository.deleteById(id);
				} else {
					throw new RecordNotFoundException("La Obra no existe, por lo que no se puede borrar", id);
				}
			}else {
				throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
			}
		}else {
			throw new NullPointerException ("Error: La obra introducida tiene un valor nulo");
		}

	}

		
	/**
	 * Método que devuelve las obras que tiene un usuario
	 * @param usuario
	 * @return una lista de obras
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public List<Obra> getObraByUser(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(id!=null) {
			try {
				Optional<List<Obra>> lista = Optional.of(obrasRepository.findUsersByObra(id));
				if(lista!=null) {
					if(lista.isPresent()) {
						return lista.get();
					}else {
						throw new RecordNotFoundException("Los datos son erroneos", id);
					}
				}else {
					throw new NullPointerException("Error ---> El usuario introducido tiene un valor erroneo");
				}				
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}						
		}else {
			throw new NullPointerException ("Error: El usuario introducido tiene un valor nulo");
		}
			
	}
	
	/**
	 * Método que devuelve una obra por las coordenadas
	 * @param coordenadas
	 * @return obra en caso de que no encuentre la obra
	 * @throws RecordNotFoundException en caso de que no encuentre la obra
	 * @throws NullPointerException en caso de que algun objeto sea nulo
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Obra getObraByLoc(float latitud,float longitud) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(latitud!=0) {
			try {
				Optional<Obra> obra = Optional.of(obrasRepository.findObraByLatLong(latitud, longitud));
				if(obra!=null) {
					if(obra.isPresent()) {
						return obra.get();
					}else {
						throw new RecordNotFoundException("La obra no existe", obra);
					}
				}else {
					throw new NullPointerException("Error ---> Las coordenadas introducidas tiene un valor erroneo");
				}

			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		}else {
			throw new NullPointerException ("Error: Las coordenadas introducidas tienen un valor nulo");
		}	
	}
	
	/**
	 * Método para obtener una obra por su nombre
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
				if(result!=null) {
					if(result.isPresent()) {
						return result.get();
					}else {
						throw new RecordNotFoundException("La obra no existe", nombre);
					}
				}else {
					throw new NullPointerException("Error ---> El nombre introducido tiene un valor nulo");
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
			
		}else {
			throw new NullPointerException("Error: El nombre introducido tiene un valor nulo");
		}
	}
	

}
