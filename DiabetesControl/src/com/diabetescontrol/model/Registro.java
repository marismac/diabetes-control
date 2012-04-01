package com.diabetescontrol.model;


import java.sql.Timestamp;


public class Registro {
	private String tipo;
	private String categoria;
	private Integer valor;
	private Timestamp datahora;
	private Integer Id;

	public Registro(Integer Id, String tipo, String categoria, Integer valor,
			Timestamp datahora) {
		this.Id = Id;
		this.tipo = tipo;
		this.categoria = categoria;
		this.valor = valor;
		this.datahora = datahora;
	}

	public Registro() {
		// TODO Auto-generated constructor stub
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Timestamp getDatahora() {
		return datahora;
	}

	public void setDatahora(Timestamp datahora) {
		this.datahora = datahora;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
}
