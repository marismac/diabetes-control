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

public class GlicoseUltimosRegistrosBusiness {

	@SuppressWarnings("static-access")
	public List<Map<String, String>> getUltimosRegistrosList(Context context) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		RegistroDAO regDAO = new RegistroDAO(context);
		Map<String, String> m = null;
		regDAO.open();
		Cursor c = regDAO.consultarAlgunsRegistrosGlicoseOrdenados(
				regDAO.COLUNA_DATAHORA + " DESC", 10);
		c.moveToFirst();
		SimpleDateFormat sdhf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		while (!c.isAfterLast()) {
			m = new HashMap<String, String>();
			Registro reg = regDAO.deCursorParaRegistro(c);
			m.put("Master", reg.getValor().toString() + " mmo/l");
			String formattedDateHour = sdhf.format(reg.getDataHora());
			m.put("Detail", formattedDateHour + " - "
					+ reg.getCategoria().toString());
			l.add(m);
			c.moveToNext();
		}
		regDAO.close();
		return l;
	}

}