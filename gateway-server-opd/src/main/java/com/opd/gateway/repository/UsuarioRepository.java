package com.opd.gateway.repository;

import org.springframework.data.repository.CrudRepository;

import com.opd.gateway.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	public Usuario findByEmail(String email);
	
	// JPQL
	// @Query("SELECT u FROM Usuario u WHERE u.email = :email")
	// public Usuario findByEmail(@Param("email") String email);
	
	// SQL
	// @Query(value = "SELECT u FROM usuario u WHERE u.email = ?1", nativeQuery = true)
	// public Usuario findByEmail(@Param("email") String email);
	
}