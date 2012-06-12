package com.diabetescontrol.model;

import java.util.Date;

public class Paciente {
	private static String CODIGOPACIENTE;
	private String codPaciente;
	private Date datanascimento;
	private String email;
	private Integer Id;
	private String nome;
	private String senhaPaciente;
	private String sexo;

	public Paciente() {

	}

	public Paciente(Integer id, String nome, String email, Date datanascimento,
			String sexo, String codPaciente, String senhaPaciente) {
		this.Id = id;
		this.nome = nome;
		this.email = email;
		this.datanascimento = datanascimento;
		this.sexo = sexo;
		this.codPaciente = codPaciente;
		this.senhaPaciente = senhaPaciente;
	}

	public String getCodPaciente() {
		return codPaciente;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return Id;
	}

	public String getNome() {
		return nome;
	}

	public String getSenhaPaciente() {
		return senhaPaciente;
	}

	public String getSexo() {
		return sexo;
	}

	public static String getCODIGOPACIENTE() {
		return CODIGOPACIENTE;
	}

	public static void setCODIGOPACIENTE(String cODIGOPACIENTE) {
		CODIGOPACIENTE = cODIGOPACIENTE;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSenhaPaciente(String senhaPaciente) {
		this.senhaPaciente = senhaPaciente;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
