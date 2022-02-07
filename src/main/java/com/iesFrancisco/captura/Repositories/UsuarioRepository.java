package com.iesFrancisco.captura.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
	
	@Query(value="SELECT * FROM usuario WHERE usuario.nombre LIKE %?1", nativeQuery = true)
	Usuario findByNombre(String nombre)throws IllegalArgumentException;
}