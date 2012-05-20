package com.android.diabetescontrol.model;

public class NotaRegistroMedico {
	private Integer Id;
	private Integer Id_registro;
	private String descricao;
	private String tipo;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getId_registro() {
		return Id_registro;
	}

	public void setId_registro(Integer id_registro) {
		Id_registro = id_registro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public NotaRegistroMedico(Integer id, Integer id_registro,
			String descricao, String tipo) {
		this.Id = id;
		this.Id_registro = id_registro;
		this.descricao = descricao;
		this.tipo = tipo;
	}

	public NotaRegistroMedico() {
	}
}
