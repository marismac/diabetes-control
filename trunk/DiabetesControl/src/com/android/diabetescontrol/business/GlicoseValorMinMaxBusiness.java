package com.android.diabetescontrol.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Constante;

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
			m.put("Master", reg.getValor().toString() + " mmo/l");
			String formattedDateHour = sdhf.format(reg.getDatahora());
			m.put("Detail", formattedDateHour + " - "
					+ reg.getCategoria().toString());
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
		where.append("WHERE ");
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
		return where.toString();
	}
}