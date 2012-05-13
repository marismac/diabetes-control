package com.android.diabetescontrol.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;

public class PacientesBusiness {
	@SuppressWarnings("static-access")
	public List<Map<String, String>> getPacientesDoMedico(Context context) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		PacienteDAO pacDAO = new PacienteDAO(context);
		Map<String, String> m = null;
		pacDAO.open();
		Cursor c = pacDAO.consultarPacientesWhereOrder(pacDAO.COLUNA_EDITAR
				+ " == 'N'", pacDAO.COLUNA_NOME);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			m = new HashMap<String, String>();
			Paciente paciente = pacDAO.deCursorParaPaciente(c);
			m.put("Master", paciente.getNome() + " (" + paciente.getId() + ")");
			m.put("Detail", paciente.getEmail());
			l.add(m);
			c.moveToNext();
		}
		pacDAO.close();
		return l;
	}
}
