package com.diabetescontrol.model;

public class Medico {
	private Integer Id;
	private String nome;
	private String email;
	private String registro;
	private String codWS;

	public Medico(Integer id, String nome, String email, String registro,
			String codWS) {
		super();
		Id = id;
		this.nome = nome;
		this.email = email;
		this.registro = registro;
		this.codWS = codWS;
	}

	public Medico() {

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

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getCodWS() {
		return codWS;
	}

	public void setCodWS(String codWS) {
		this.codWS = codWS;
	}

}
