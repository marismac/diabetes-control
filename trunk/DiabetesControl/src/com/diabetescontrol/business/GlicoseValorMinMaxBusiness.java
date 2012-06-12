package com.diabetescontrol.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Constante;

public class GlicoseValorMinMaxBusiness {

	@SuppressWarnings("static-access")
	public List<Map<String, String>> getUltimosRegistrosList(Context context,
			Double valorMax, Double valorMin) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		RegistroDAO regDAO = new RegistroDAO(context);
		Map<String, String> m = null;
		regDAO.open();
		Cursor c = regDAO.consultarRegistrosWhereOrder(
				getWhereValores(valorMin, valorMax, regDAO),
				regDAO.COLUNA_DATAHORA + " DESC");
		c.moveToFirst();
		SimpleDateFormat sdhf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		while (!c.isAfterLast()) {
			m = new HashMap<String, String>();
			Registro reg = regDAO.deCursorParaRegistro(c);
			if (Constante.TIPO_PRESSAO.equals(reg.getTipo())) {
				m.put("Master", reg.getValorPressao() + " " + reg.getUnidade()
						+ " de " + reg.getTipo());
			} else {
				m.put("Master",
						reg.getValor().toString() + " " + reg.getUnidade()
								+ " de " + reg.getTipo());
			}
			String formattedDateHour = sdhf.format(reg.getDataHora());
			if (Constante.TIPO_MEDICAMENTO.equals(reg.getTipo())) {
				m.put("Detail", reg.getMedicamento() + " - "
						+ formattedDateHour + " - "
						+ reg.getCategoria().toString());
			} else {
				m.put("Detail", formattedDateHour + " - "
						+ reg.getCategoria().toString());
			}
			l.add(m);
			c.moveToNext();
		}
		regDAO.close();
		return l;
	}

	@SuppressWarnings({ "null", "unused", "static-access" })
	private String getWhereValores(Double valorMin, Double valorMax,
			RegistroDAO regDAO) {
		StringBuilder where = new StringBuilder();

		if ((valorMax != null || !valorMax.isNaN())
				&& (valorMin != null || !valorMin.isNaN())) {
			where.append(regDAO.COLUNA_VALOR + " <= " + valorMax.intValue());
			where.append(" AND ");
			where.append(regDAO.COLUNA_VALOR + " >= " + valorMin.intValue());
			where.append(" AND ");
			where.append(regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
					+ "'");
		} else if (valorMax != null || !valorMax.isNaN()) {
			where.append(regDAO.COLUNA_VALOR + " <= " + valorMax.intValue());
			where.append(" AND ");
			where.append(regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
					+ "'");
		} else if (valorMin != null || !valorMin.isNaN()) {
			where.append(regDAO.COLUNA_VALOR + " >= " + valorMin.intValue());
			where.append(" AND ");
			where.append(regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
					+ "'");
		} else {
			where.append(regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
					+ "'");
		}
		if (Constante.TIPO_MODO_MEDICO.equals(Constante.TIPO_MODO)) {
			where.append(" AND " + regDAO.COLUNA_CODPAC + " = '"
					+ Paciente.getCODIGOPACIENTE() + "' AND "
					+ regDAO.COLUNA_TIPO_USER + " = '" + Constante.TIPO_MODO
					+ "' ");
		} else {
			where.append(" AND " + regDAO.COLUNA_TIPO_USER + " = '"
					+ Constante.TIPO_MODO + "' ");
		}
		return where.toString();
	}
}