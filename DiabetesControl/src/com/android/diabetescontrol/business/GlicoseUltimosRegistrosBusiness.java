package com.android.diabetescontrol.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Constante;
import com.android.diabetescontrol.util.Utils;

public class GlicoseUltimosRegistrosBusiness {

	@SuppressWarnings("static-access")
	public List<Map<String, String>> getUltimosRegistrosList(Context context) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		RegistroDAO regDAO = new RegistroDAO(context);
		Map<String, String> m = null;
		regDAO.open();
		Cursor c = null;
		if (Utils.isPaciente(context)) {
			c = regDAO.consultarRegistrosWhereOrderLimit(
					regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
							+ "' AND " + regDAO.COLUNA_TIPO_USER + " = '"
							+ Utils.tipo_modo(context) + "'",
					regDAO.COLUNA_DATAHORA + " DESC", "20");
		} else {
			c = regDAO.consultarRegistrosWhereOrderLimit(
					regDAO.COLUNA_TIPO + " = '" + Constante.TIPO_GLICOSE
							+ "' AND " + regDAO.COLUNA_TIPO_USER + " = '"
							+ Utils.tipo_modo(context) + "' AND "
							+ regDAO.COLUNA_CODPAC + " = '"
							+ Paciente.getCODIGOPACIENTE() + "' ",
					regDAO.COLUNA_DATAHORA + " DESC", "20");
		}
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

}