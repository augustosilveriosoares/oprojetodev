package com.opd.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.opd.core.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

	public Usuario findByEmail(String email);

}
