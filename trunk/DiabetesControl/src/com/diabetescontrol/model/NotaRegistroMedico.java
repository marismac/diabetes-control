package com.diabetescontrol.model;

public class NotaRegistroMedico {
	private Integer Id;
	private Integer idRegistro;
	private String descricao;
	private String sincronizado;
	private String tipoUser;
	private String infoRegistro;
	private String codPaciente;

	public String getInfoRegistro() {
		return infoRegistro;
	}

	public void setInfoRegistro(String infoRegistro) {
		this.infoRegistro = infoRegistro;
	}

	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String nomePaciente) {
		this.codPaciente = nomePaciente;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}

	public String getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(String tipoUser) {
		this.tipoUser = tipoUser;
	}
}
