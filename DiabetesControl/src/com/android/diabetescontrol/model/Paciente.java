package com.android.diabetescontrol.model;

import java.sql.Timestamp;

public class Paciente {
	private Integer Id;
	private String nome;
	private String email;
	private Timestamp datanascimento;
	private String sexo;
	private String codWS;
	private String editar;

	public Paciente(Integer id, String nome, String email,
			Timestamp datanascimento, String sexo, String codWS, String editar) {
		this.Id = id;
		this.nome = nome;
		this.email = email;
		this.datanascimento = datanascimento;
		this.sexo = sexo;
		this.codWS = codWS;
		this.editar = editar;
	}

	public Paciente() {

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

	public Timestamp getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Timestamp datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCodWS() {
		return codWS;
	}

	public void setCodWS(String codWS) {
		this.codWS = codWS;
	}

	public String getEditar() {
		return editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

}
