package com.opd.gateway.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opd.gateway.model.Categoria;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class CategoriaForm {

	private String id;
	@NotNull
	@NotEmpty
	private String nome;

	public CategoriaForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoriaForm(Categoria categoria) {
		super();
		this.parse(categoria);
	}

	public void parse(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}