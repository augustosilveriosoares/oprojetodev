package com.opd.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opd.core.model.Lancamento;

public interface LancamentoRepository extends CrudRepository<Lancamento, String> {

	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = :id")
	public List<Lancamento> findByUsuarioId(@Param("id") String id);

}
