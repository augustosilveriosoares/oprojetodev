package com.opd.gateway.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RegistroForm {

	@NotNull
	@NotEmpty
	private String email;
	@NotNull
	@NotEmpty
	private String nome;
	@NotNull
	@NotEmpty
	private String senha;

	public RegistroForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
