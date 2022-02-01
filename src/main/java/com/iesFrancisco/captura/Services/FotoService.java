package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Repositories.FotoRepository;

@Service
public class FotoService {

    @Autowired // instanciar el repositorio
    FotoRepository repository;
    /**
	 * M�todo del servicio que devuelve una lista con las fotos
	 * @return la lista de las fotos     	
	 * @throws RecordNotFoundException en caso de que sea nulo
	 */
	public List<Foto> getAllFotos() throws RecordNotFoundException {
		List<Foto> fotosDummy = repository.findAll();
		if (fotosDummy != null) {
			return fotosDummy;
		} else {
			throw new RecordNotFoundException("No hay fotos en la base de datos");
		}
	}
    /**
     * M�todo del servicio que devuelve una foto por su id 
     * @param id de la foto 
     * @return la foto
     * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
     */
    public Foto getFotoById(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
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
     * M�todo del servicio que devuelve las fotos de una visita 
     * @param id de la foto 
     * @return la foto
     * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
     */
    public List<Foto> getFotosPorVisita(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
        if (id != null) {
            try {
                Optional<List<Foto>> getFotoDummy = Optional.of(repository.getFotosPorVisita(id));
                if (getFotoDummy.isPresent()) {
                    return getFotoDummy.get();
                } else {
                    throw new RecordNotFoundException("Error ---> La fotos visita con id: " + id + " no existe");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
        }
    }
    /**
     * M�todo del servicio que devuelve una foto por su id 
     * @param id de la foto 
     * @return la foto
     * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws NullPointerException     en caso de que alg�n objeto sea null
	 * @throws IllegalArgumentException en caso de que sea nulo
     */
    public List<Foto> getFotosPorFecha(LocalDate fecha) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
        if (fecha != null) {
            try {
                Optional<List<Foto>> getFotoDummy = Optional.of(repository.getFotosPorFecha(fecha));
                if (getFotoDummy.isPresent()) {
                    return getFotoDummy.get();
                } else {
                    throw new RecordNotFoundException("Error ---> La foto con fecha: " + fecha + " no existe");
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");
        }
    }
    /**
     * M�todo del serviciio que crea una foto o la actualiza en caso de que exista
     * @param foto que queremos crear 
     * @return foto 
     * @throws NullPointerException     en caso de que algun objeto sea null
     * @throws IllegalArgumentException en caso de que sea nulo
     */
    public Foto creaFoto(Foto foto) throws NullPointerException, IllegalArgumentException {
        if (foto != null) {
			if (foto.getId() < 0) {
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
	 * Método del servicio que usaremos para actualizar una foto que exista.
	 * 
	 * @param foto que queremos actualizar
	 * @return la foto actualizada
	 * @throws NullPointerException     en caso de que algún objeto sea null
	 * @throws RecordNotFoundException  en caso de que no encuentre el usuario
	 * @throws IllegalArgumentException en caso de que sea nulo
	 */
	public Foto actualizarFoto(Foto foto)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (foto != null) {
			Optional<Foto> getFotoDummy = Optional.of(getFotoById(foto.getId()));
			if (getFotoDummy != null) {
				if (!getFotoDummy.isPresent()) {
					Foto actualizaFotoDummy = getFotoDummy.get();
					actualizaFotoDummy.setId(foto.getId());
					actualizaFotoDummy.setUrl(foto.getUrl());
					actualizaFotoDummy.setComentario(foto.getComentario());
					actualizaFotoDummy.setVisita(foto.getVisita());
					try {
						return repository.save(actualizaFotoDummy);
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
	public void borrarFoto(Long id) throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Foto> borrarFotoDummy = Optional.of(getFotoById(id));
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
	}
}
