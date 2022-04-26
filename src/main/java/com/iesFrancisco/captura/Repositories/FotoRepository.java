package com.iesFrancisco.captura.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Exception.RecordOK;
import com.iesFrancisco.captura.Exception.ResquestUnauthourized;
import com.iesFrancisco.captura.Model.Foto;


@Repository
public interface FotoRepository extends JpaRepository<Foto,Long> {
	
	@Query(value= "SELECT * FROM foto where foto.id_visita = ?1", nativeQuery=true)
	List<Foto> getFotosPorVisita(Long id) throws IllegalArgumentException;

	@Query(value= "SELECT FOT FROM foto FOT where FOT.fecha LIKE %?1", nativeQuery=true)
	List<Foto> getFotosPorFecha(LocalDate fecha) throws IllegalArgumentException;
	
}
