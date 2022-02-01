package com.iesFrancisco.captura.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;


@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {
	
	
	
	Obra findByName(String nombre) throws IllegalArgumentException;
	
	
}

