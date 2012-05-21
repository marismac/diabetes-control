package com.android.diabetescontrol.model;

import java.sql.Timestamp;
import java.util.Date;

public class Paciente {
	private Integer Id;
	private String nome;
	private String email;
	private Date datanascimento;
	private String sexo;
	private String codPaciente;
	private String senhaPaciente;

	public Paciente(Integer id, String nome, String email,
			Date datanascimento, String sexo, String codPaciente,
			String senhaPaciente) {
		this.Id = id;
		this.nome = nome;
		this.email = email;
		this.datanascimento = datanascimento;
		this.sexo = sexo;
		this.codPaciente = codPaciente;
		this.senhaPaciente = senhaPaciente;
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

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	public String getSenhaPaciente() {
		return senhaPaciente;
	}

	public void setSenhaPaciente(String senhaPaciente) {
		this.senhaPaciente = senhaPaciente;
	}

}
