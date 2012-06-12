package com.diabetescontrol.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.diabetescontrol.database.MedicamentoDAO;

public class Medicamento {
	private Integer Id;
	private String descricao;
	private String tipo;
	public static List<Medicamento> LIST_MEDICAMENTOS = new ArrayList<Medicamento>();

	public Medicamento(Integer id, String descricao, String tipo) {
		this.Id = id;
		this.descricao = descricao;
		this.tipo = tipo;
	}

	public Medicamento() {

	}

	public static void preencheListaMedicamento(Context ctx) {
		MedicamentoDAO medDao = new MedicamentoDAO(ctx);
		medDao.open();
		Cursor c = medDao.consultarMedicamentos();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			LIST_MEDICAMENTOS.add(medDao.deCursorParaMedicamento(c));
			c.moveToNext();
		}
		medDao.close();
	}

	@SuppressWarnings("rawtypes")
	public List getMedicamentos(Context ctx) {
		List<String> list = new ArrayList<String>();
		MedicamentoDAO medDao = new MedicamentoDAO(ctx);
		medDao.open();
		Cursor c = medDao.consultarMedicamentos();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Medicamento med = medDao.deCursorParaMedicamento(c);
			list.add(med.getId() + " - " + med.getDescricao() + " "
					+ med.getTipo());
			c.moveToNext();
		}
		medDao.close();
		return list;

	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

}
