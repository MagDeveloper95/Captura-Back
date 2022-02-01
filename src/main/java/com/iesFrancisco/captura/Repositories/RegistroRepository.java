package com.iesFrancisco.captura.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Exception.RecordNotFoundException;
import com.iesFrancisco.captura.Exception.RecordOK;
import com.iesFrancisco.captura.Exception.ResquestUnauthourized;
import com.iesFrancisco.captura.Model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

	/**
	 * Método que nos devuelve una lista con los registros 
	 * introduciendo una fecha.
	 * @param fecha por la que queremos el registro
	 * @return La lista de registros filtrando por día
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente.
	 */
	
	@Query(value= "SELECT daReg FROM registro daReg where daReg.fecha LIKE %?1", nativeQuery=true)
	List<Registro> getRegistroPorFecha(LocalDate fecha) throws IllegalArgumentException;
	/**
	 * Métodoo que nos devuelve una lista con los registros
	 * introduciendo un usuario
	 * @param usuario por el que queremos el registro
	 * @return La lista de registros filtrando por Usuario
	 * @throws RecordNotFoundException en caso de no encontrar registros
	 * @throws ResquestUnauthourized en caso de que no este autorizado
	 * @throws RecordOK en caso de que todo vaya correctamente
	 */
	
	@Query(value= "SELECT daReg FROM registro daReg where daReg.idUser = ?1",nativeQuery=true)
	List<Registro> getRegistroPorUsuario(Long usuario) throws IllegalArgumentException;
	
	
}
