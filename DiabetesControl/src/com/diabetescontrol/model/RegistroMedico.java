package com.diabetescontrol.model;

import java.sql.Timestamp;

public class RegistroMedico {
	private String tipo;
	private String categoria;
	private Integer valor;
	private Timestamp datahora;
	private Integer Id;
	private String codPaciente;

	public RegistroMedico(String tipo, String categoria, Integer valor,
			Timestamp datahora, Integer id, String codPaciente) {
		this.tipo = tipo;
		this.categoria = categoria;
		this.valor = valor;
		this.datahora = datahora;
		this.Id = id;
		this.codPaciente = codPaciente;
	}

	public RegistroMedico() {

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

	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

}
