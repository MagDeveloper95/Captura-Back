package com.iesFrancisco.captura.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Visita;

@Repository
public interface VisitaRepository extends JpaRepository<Visita,Long> {
	
	
	@Query(value= "SELECT * FROM visita where id_obra = ?1", nativeQuery=true)
	List<Visita> getFotosPorObra(Long id) throws IllegalArgumentException;
	
	@Query(value= "SELECT * FROM visita WHERE fecha = ?1", nativeQuery=true)
	List<Visita> getVisitasPorFecha(LocalDate fecha) throws IllegalArgumentException;
}