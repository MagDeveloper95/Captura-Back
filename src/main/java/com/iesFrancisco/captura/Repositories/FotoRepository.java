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
	/**
	 * Metodo que nos devuelve una lista con los las fotos  
	 * introduciendo una Visita.
	 * @param vista por la que queremos las fotos
	 * @return La lista de las fotos filtrando por visitas
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente.
	 */
	@Query(value= "SELECT * FROM foto where foto.id_visita = ?1", nativeQuery=true)
	List<Foto> getFotosPorVisita(Long id) throws IllegalArgumentException;
	/**
	 * Metodo que nos devuelve una lista con los las fotos  
	 * introduciendo una fecha.
	 * @param fecha por la que queremos el las fotos
	 * @return La lista de las fotos filtrando por día
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente.
	 */
	@Query(value= "SELECT FOT FROM foto FOT where FOT.fecha LIKE %?1", nativeQuery=true)
	List<Foto> getFotosPorFecha(LocalDate fecha) throws IllegalArgumentException;
	
}
