package com.opd.core.controller.caixa;

import java.math.BigDecimal;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opd.core.model.Caixa;
import com.opd.core.model.CaixaTipo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class CaixaForm {

	private String id;
	@NotNull
	@NotEmpty
	private String nome;
	@NotNull
	private BigDecimal saldo;
	@NotNull
	private CaixaTipo tipo = CaixaTipo.CONTA_BANCARIA;
	@NotNull
	@NotEmpty
	private String usuarioId;

	public CaixaForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaixaForm(Caixa caixa) {
		super();
		this.parse(caixa);
	}

	public void parse(Caixa caixa) {
		this.id = caixa.getId();
		this.nome = caixa.getNome();
		this.saldo = caixa.getSaldo();
		this.tipo = caixa.getTipo();
		this.usuarioId = caixa.getUsuario().getId();
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public CaixaTipo getTipo() {
		return tipo;
	}

	public void setTipo(CaixaTipo tipo) {
		this.tipo = tipo;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

}