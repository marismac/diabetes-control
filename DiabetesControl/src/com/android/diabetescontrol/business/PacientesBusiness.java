package com.android.diabetescontrol.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;

public class PacientesBusiness {
	@SuppressWarnings("static-access")
	public List<Paciente> getPacientesDoMedico(Context context) {
		List<Paciente> lista = new ArrayList<Paciente>();
		PacienteDAO pacDAO = new PacienteDAO(context);
		pacDAO.open();
		Cursor c = pacDAO.consultarPacientesWhereOrder(pacDAO.COLUNA_SENHAPAC
				+ " IS NULL", pacDAO.COLUNA_NOME);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			lista.add(pacDAO.deCursorParaPaciente(c));
			c.moveToNext();
		}
		pacDAO.close();
		return lista;
	}
}
