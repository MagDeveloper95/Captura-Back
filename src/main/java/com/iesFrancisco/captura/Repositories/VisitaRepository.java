package com.iesFrancisco.captura.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Exception.RecordOK;
import com.iesFrancisco.captura.Exception.ResquestUnauthourized;
import com.iesFrancisco.captura.Model.Visita;

@Repository
public interface VisitaRepository extends JpaRepository<Visita,Long> {
	
	/**
	 * Método que nos devuelve una lista con los las las visitas  
	 * introduciendo una Obra.
	 * @param id por la que queremos las visitas
	 * @return La lista de las fotos filtrando por Obra
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente.
	 */
	@Query(value= "SELECT * FROM visita VIST where VIST.id_obra LIKE %?1", nativeQuery=true)
	List<Visita> getFotosPorObra(Long id) throws IllegalArgumentException;
	/**
	 * Método que nos devuelve una lista con los las las visitas  
	 * introduciendo una fecha.
	 * @param fecha por la que queremos el las fotos
	 * @return La lista de las visitas filtrando por día
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente.
	 */
	@Query(value= "SELECT * FROM visita VIST where fecha LIKE %?1", nativeQuery=true)
	List<Visita> getVisitasPorFecha(LocalDate fecha) throws IllegalArgumentException;
}