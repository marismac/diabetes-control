package com.android.diabetescontrol.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroDAO;

public class Registro {
	private String tipo;
	private String categoria;
	private Float valor;
	private Timestamp dataHora;
	private Integer Id;
	private String sincronizado;
	private String codPaciente;
	private String unidade;
	private Integer codCelPac;
	private Integer medicamento;
	private String valorPressao;
	private String modoUser;

	public Registro(Integer Id, String tipo, String categoria, Float valor,
			Timestamp dataHora, String sincronizado, String codPaciente,
			String unidade, Integer codCelPac, String modoUser,
			Integer medicamento, String valorPressao) {
		this.Id = Id;
		this.tipo = tipo;
		this.categoria = categoria;
		this.valor = valor;
		this.dataHora = dataHora;
		this.codPaciente = codPaciente;
		this.unidade = unidade;
		this.sincronizado = sincronizado;
		this.codCelPac = codCelPac;
		this.modoUser = modoUser;
		this.medicamento = medicamento;
		this.valorPressao = valorPressao;
	}

	public Registro() {
		//
	}

	public List<Registro> lista(Context ctx, String where, String limit) {
		List<Registro> list = new ArrayList<Registro>();
		RegistroDAO regDao = new RegistroDAO(ctx);
		regDao.open();
		Cursor c = regDao.consultarRegistrosWhereOrderLimit(where, "_id desc",
				limit);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			list.add(regDao.deCursorParaRegistro(c));
			c.moveToNext();
		}
		regDao.close();
		return list;
	}

	public Float getValor() {
		return valor;
	}

	public Integer getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Integer medicamento) {
		this.medicamento = medicamento;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}

	public String getCodPaciente() {
		return codPaciente;
	}

	public void setCodPaciente(String codPaciente) {
		this.codPaciente = codPaciente;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getCodCelPac() {
		return codCelPac;
	}

	public void setCodCelPac(Integer codCelPac) {
		this.codCelPac = codCelPac;
	}

	public String getModoUser() {
		return modoUser;
	}

	public void setModoUser(String modoUser) {
		this.modoUser = modoUser;
	}

	public String getValorPressao() {
		return valorPressao;
	}

	public void setValorPressao(String valorPressao) {
		this.valorPressao = valorPressao;
	}

}
