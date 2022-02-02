package com.iesFrancisco.captura.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;


@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {
	
	//Aqui van métodos que no estén en el CRUD básico
	@Query(value="SELECT * FROM obra WHERE obra.nombre LIKE %?1", nativeQuery = true)
	Optional<Obra> findByName(String nombre) throws IllegalArgumentException;
	
	
}

