package com.iesFrancisco.captura.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iesFrancisco.captura.Model.Obra;
import com.iesFrancisco.captura.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
	
	@Query(value="SELECT * FROM usuario WHERE usuario.nombre LIKE %?1", nativeQuery = true)
	Usuario findByNombre(String nombre)throws IllegalArgumentException;
	
	/**
	 * Método que devuelve los usuarios que tiene una obra
	 * @param id de la obra para devolver sus usuarios
	 * @return lista de usuarios que tiene esa obra
	 * @throws IllegalArgumentException
	 */
	@Query(value="SELECT * FROM usuario JOIN usuario_obra ON usuario_obra.id_usuario = usuario.id "
			+ "JOIN obra ON obra.id = usuario_obra.id_obra WHERE obra.id= ?1", nativeQuery = true)
	List<Usuario> findUsersByObra(Long id) throws IllegalArgumentException;
}