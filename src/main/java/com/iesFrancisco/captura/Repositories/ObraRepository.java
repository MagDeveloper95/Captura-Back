package com.iesFrancisco.captura.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;


@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {
	
	//Aqui van m�todos que no est�n en el CRUD b�sico
	/**
	 * M�todo que devuelve una obra por un nombre
	 * @param nombre por la que queremos las obras
	 * @return lista de obras con ese nombre
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM obra WHERE obra.nombre LIKE %?1", nativeQuery = true)
	Obra findByName(String nombre) throws IllegalArgumentException;
	
	/**
	 * M�todo que devuelve las obras que tiene un usuario
	 * @param id del usuario para devolver sus obras
	 * @return lista de obras que tiene ese usuario
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM obra JOIN usuario_obra ON usuario_obra.id_obra = obra.id "
			+ "JOIN usuario ON usuario.id = usuario_obra.id_usuario WHERE usuario.id= ?1", nativeQuery = true)
	List<Obra> findObrasByUser(Long id) throws IllegalArgumentException;
	
	/**
	 * M�todo que nos devuelve una obra tras introducir las coordendas
	 * @param coordenadas de la obra
	 * @return obra donde est�n las coordenadas
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * From obra WHERE obra.latitud = ?1 AND obra.longitud =?2 ", nativeQuery = true)
	Obra findObraByLatLong(float latitud, float longitud) throws IllegalArgumentException;

	//crea una obra con un usuario asociado atacando a la tabla usuario_obra
	@Query(value="INSERT INTO usuario_obra (id_usuario, id_obra) VALUES (?1, ?2)", nativeQuery = true)
	Obra insertarUsuarioObra(Long idUsuario, Long idObra);
}

