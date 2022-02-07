package com.iesFrancisco.captura.Repositories;

import java.awt.geom.Point2D;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;


@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {
	
	//Aqui van métodos que no estén en el CRUD básico
	/**
	 * Método que devuelve una obra por un nombre
	 * @param nombre por la que queremos las obras
	 * @return lista de obras con ese nombre
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM obra WHERE obra.nombre LIKE %?1", nativeQuery = true)
	Obra findByName(String nombre) throws IllegalArgumentException;
	
	/**
	 * Método que devuelve las obras que tiene un usuario
	 * @param id del usuario para devolver sus obras
	 * @return lista de obras que tiene ese usuario
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM obra JOIN usuario_obra ON usuario_obra.id_obra = obra.id "
			+ "JOIN usuario ON usuario.id = usuario_obra.id_usuario WHERE usuario.id= ?1", nativeQuery = true)
	List<Obra> findUsersByObra(Long id) throws IllegalArgumentException;
	
	/**
	 * Método que nos devuelve una obra tras introducir las coordendas
	 * @param coordenadas de la obra
	 * @return obra donde están las coordenadas
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * From obra WHERE obra.lat_long LIKE %?1", nativeQuery = true)
	Obra findObraByLatLong(Point2D coordenadas) throws IllegalArgumentException;
	
	
}

