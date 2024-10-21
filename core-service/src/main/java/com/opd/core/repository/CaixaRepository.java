package com.opd.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opd.core.model.Caixa;

public interface CaixaRepository extends CrudRepository<Caixa, String> {

	@Query("SELECT c FROM Caixa c WHERE c.usuario.id = :id")
	public List<Caixa> findByUsuarioId(@Param("id") String id);
	
}