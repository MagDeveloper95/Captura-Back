package com.iesFrancisco.captura.Services;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Model.Usuario;
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
	public List<Obra> getAllObras(){
		List<Obra> result = obrasRepository.findAll();
		return result;
	}
	
	/**
	 * Método que devuelve una obra por su id
	 * @param id de la obra
	 * @return result, una obra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
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
	 * Método para obtener una obra por su nombre
	 * @param nombre
	 * @return result, una obra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Obra getObraByName(String nombre) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(nombre != null) {
			try {
				Optional<Obra> result = Optional.of(obrasRepository.findByName(nombre));
				if(result.isPresent()) {
					return result.get();
				}else {
					throw new RecordNotFoundException("La obra no existe", nombre);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
			
		}else {
			throw new NullPointerException("Error: El nombre introducido tiene un valor nulo");
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
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Obra actualizaObra (Obra obra) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		if(obra!=null) {
			Optional<Obra> result = Optional.of(getObraById(obra.getId()));
				if(result!=null) {
					if(!result.isPresent()) {
						Obra newObra = result.get();
						newObra.setId(obra.getId());
						newObra.setNombre(obra.getNombre());
						newObra.setLatLong(obra.getLatLong());
						newObra.setDatos(obra.getDatos());
						newObra.setUsuario(obra.getUsuario());
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
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deleteObraById(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
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
	
	public List<Obra> getObraByUser(Usuario usuario) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		return null;
		
	}
	
	public Obra getObraByLoc(Point2D coordenadas) throws RecordNotFoundException, NullPointerException, IllegalArgumentException{
		return null;
		
	}
	
	public Obra createOrUpdateObra (Obra obra) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(obra.getId()!=null&&obra.getId()>0) {
			Optional<Obra> n = obrasRepository.findById(obra.getId());
			if(n.isPresent()) {//UPDATE
				Obra newObra = n.get();
				newObra.setId(obra.getId());
				newObra.setNombre(obra.getNombre());
				newObra.setLatLong(obra.getLatLong());
				newObra.setDatos(obra.getDatos());
				newObra.setUsuario(obra.getUsuario());
				newObra.setVisita(obra.getVisita());
				newObra = obrasRepository.save(newObra);
				return newObra;
			}else {//INSERT
				obra.setId(null);
				obra=obrasRepository.save(obra);
				return obra;
			}
		}else {
				obra=obrasRepository.save(obra);
				return obra;
		}
	}
	

}
