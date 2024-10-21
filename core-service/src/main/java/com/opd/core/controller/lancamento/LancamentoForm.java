package com.opd.core.controller.lancamento;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opd.core.model.Lancamento;
import com.opd.core.model.LancamentoTipo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
public class LancamentoForm {

	private String id;
	@NotNull
	private Date data;
	@NotNull
	private BigDecimal valor;
	@NotNull
	@NotEmpty
	private String descricao;
	@NotNull
	private LancamentoTipo tipo;
	@NotNull
	@NotEmpty
	private String caixaId;
	@NotNull
	@NotEmpty
	private String categoriaId;

	public LancamentoForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LancamentoForm(Lancamento lancamento) {
		super();
		this.parse(lancamento);
	}

	public void parse(Lancamento lancamento) {
		this.id = lancamento.getId();
		this.data = lancamento.getData();
		this.valor = lancamento.getValor();
		this.descricao = lancamento.getDescricao();
		this.tipo = lancamento.getTipo();
		this.caixaId = lancamento.getCaixa().getId();
		this.categoriaId = lancamento.getCategoria().getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LancamentoTipo getTipo() {
		return tipo;
	}

	public void setTipo(LancamentoTipo tipo) {
		this.tipo = tipo;
	}

	public String getCaixaId() {
		return caixaId;
	}

	public void setCaixaId(String caixaId) {
		this.caixaId = caixaId;
	}

	public String getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}

}