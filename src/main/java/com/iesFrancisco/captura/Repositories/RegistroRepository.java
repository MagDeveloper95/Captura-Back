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

	
	
	@Query(value= "SELECT daReg FROM registro daReg where daReg.fecha LIKE %?1", nativeQuery=true)
	List<Registro> getRegistroPorFecha(LocalDate fecha) throws IllegalArgumentException;

	@Query(value= "SELECT daReg FROM registro daReg where daReg.idUser = ?1",nativeQuery=true)
	List<Registro> getRegistroPorUsuario(Long usuario) throws IllegalArgumentException;
	
	
}
