package com.opd.core.controller.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opd.core.model.Usuario;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class UsuarioForm {

	private String id;
	@NotNull
	@NotEmpty
	private String nome;
	@NotNull
	@NotEmpty
	private String email;
	private String senha;

	public UsuarioForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioForm(Usuario usuario) {
		super();
		this.parse(usuario);
	}

	public void parse(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.senha = usuario.getSenha();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}