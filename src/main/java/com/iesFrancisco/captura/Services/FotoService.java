package com.iesFrancisco.captura.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Model.Foto;
import com.iesFrancisco.captura.Model.FotoWrapper;
import com.iesFrancisco.captura.Repositories.FotoRepository;
import com.microsoft.graph.core.Multipart;

@Service
public class FotoService {
	
	private static final Logger logger = LoggerFactory.getLogger(FotoService.class);

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
			logger.info("Consulta exitosa en getAllFotos");
			return result;
		} else {
			logger.error("No hay visitas en la base de datos, en getAllVisitas");
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
					logger.info("Consulta exitosa en getFotoByID");
					return getFotoDummy.get();
				} else {
					logger.error("Error ---> La foto con id: " + id + " no existe en getFotoByID");
					throw new RecordNotFoundException("Error ---> La foto con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getFotoByID" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> El id introducido tiene un valor nulo en getFotoByID");
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
					logger.info("Consulta exitosa en getFotosByVisita");
					return getFotosDummy.get();
				} else {
					logger.error("Error ---> La visita con id: " + id + " no tiene lista de fotos en getFotoPorVisita");
					throw new RecordNotFoundException("Error ---> La foto con id: " + id + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getFotoPorVisita" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo getFotoPorVisita");
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
					logger.info("Consulta exitosa en getFotosPorFecha");
					return getFotosDummy.get();
				} else {
					logger.error("Error ---> La fecha: " + fecha + " no tiene lista de fotos en getFotosPorFecha");
					throw new RecordNotFoundException("Error ---> La foto con fecha: " + fecha + " no existe");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Error ---> IllegarArgumentException en getFotosPorFecha" + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("Error ---> La fecha introducida tiene un valor nulo getVisitaPorObra");
			throw new NullPointerException("Error ---> La fecha introducida tiene un valor nulo");
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
	 * @throws IOException 
	 */

	public Foto creaFoto(FotoWrapper foto,MultipartFile file) throws NullPointerException, IllegalArgumentException, IOException {
		if (foto != null) {
		if (foto.getId() < 0) {
				try {
					System.out.println(foto.toString());
					System.out.println(file);
					OneDriveService.uploadFile(
							file.getOriginalFilename(),
							foto.getVisita().getObra().getNombre(),
							foto.getVisita().getHeader(),
						 	file.getInputStream());
					Foto fotoCreada = new Foto(
							foto.getId(),
							OneDriveService.getUrl(
									foto.getFile().getOriginalFilename(),
									foto.getVisita().getObra().getNombre(),
									foto.getVisita().getHeader()),
							foto.getComentario(),
							foto.getVisita());
					logger.info("Consulta exitosa en creaFoto");
					return repository.save(fotoCreada);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				Foto fotoCreada = new Foto(
						foto.getId(),
						OneDriveService.getUrl(foto.getFile().getName(),
								foto.getVisita().getObra().getNombre(),
								foto.getVisita().getHeader()),
						foto.getComentario(),
						foto.getVisita());
				return actualizarFoto(fotoCreada);
			}
		} else {
			logger.error("Error ---> La foto introducida tiene un valor nulo en creaFoto");
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
				if (getFotoDummy.isPresent()) {
					Foto actualizarFotoDummy = getFotoDummy.get();
					actualizarFotoDummy.setId(foto.getId());
					actualizarFotoDummy.setUrl(foto.getUrl());
					actualizarFotoDummy.setComentario(foto.getComentario());
					actualizarFotoDummy.setVisita(foto.getVisita());
					try {
						logger.info("Consulta exitosa en actualizarFoto");
						return repository.save(actualizarFotoDummy);
					} catch (IllegalArgumentException e) {
						logger.error("Error ---> IllegarArgumentException en actualizarFoto :" + e);
						throw new IllegalArgumentException(e);
					}
				} else {
					logger.error("Error ---> La foto no existe", foto.getId() + " en ActualizarVisita");
					throw new RecordNotFoundException("Error ---> La foto no existe", foto.getId());
				}
		} else {
			logger.error("Error ---> La visita introducida tiene un valor nulo en ActualizarFoto");
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
	public void borrarFoto(Long id) throws RecordNotFoundException {
		if(id!=null) {
			Optional<Foto> foto = repository.findById(id);
			try {
				if (foto.isPresent()) {
					//OneDriveService.deleteFile(foto.get().getId());
					logger.info("Consulta exitosa en borrarFoto");
					repository.deleteById(id);
				} else {
					logger.error("Error ---> La visita no existe", id + " en borrarFoto");
					throw new RecordNotFoundException("La foto no existe", id);
				}
			} catch (IllegalArgumentException e ) {
				throw new IllegalArgumentException(e);
			}
		}else {
			logger.error("Error ---> La foto introducida tiene un valor nulo en borrarFoto");
			throw new NullPointerException("Error ---> El id introducido tiene un valor nulo");			
		}
	}
	
	public byte[] getPhoto(String url) {
		return OneDriveService.getPhoto(url);
	}
	
}
