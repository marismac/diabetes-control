package com.android.diabetescontrol.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroMedicoDAO;
import com.android.diabetescontrol.model.RegistroMedico;

public class RegistrosMedicosBusiness {
	@SuppressWarnings("static-access")
	public List<RegistroMedico> getRegistrosMedicosPaciente(Context context, String codPaciente) {
		List<RegistroMedico> medicoPaciente = new ArrayList<RegistroMedico>();
		RegistroMedicoDAO regMedDAO = new RegistroMedicoDAO(context);
		regMedDAO.open();
		Cursor c = regMedDAO.consultarRegistrosWhereOrderLimit(regMedDAO.COLUNA_CODPACIENTE + " = '" + codPaciente + "'", regMedDAO.COLUNA_DATAHORA + " ASC", "100");				
		c.moveToFirst();
		while (!c.isAfterLast()) {
			medicoPaciente.add(regMedDAO.deCursorParaRegistroMedico(c));
			c.moveToNext();
		}
		regMedDAO.close();
		return medicoPaciente;
	}
}
