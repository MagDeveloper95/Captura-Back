package com.iesFrancisco.captura.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;


@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {
	
	/**
	//Aqui van m�todos que no est�n en el CRUD b�sico
	@Query("SELECT OB FROM obra OB WHERE OB.nombre = ?1")
	Optional<Obra> findByName(String nombre) throws IllegalArgumentException;
	*/
	
}
