package com.android.diabetescontrol.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;

public class RegistrosMedicosBusiness {
	@SuppressWarnings("static-access")
	public List<Registro> getRegistrosPaciente(Context context, String codPaciente) {
		List<Registro> medicoPaciente = new ArrayList<Registro>();
		RegistroDAO regDAO = new RegistroDAO(context);
		regDAO.open();
		Cursor c = regDAO.consultarRegistrosWhereOrderLimit(regDAO.COLUNA_CODPAC + " = '" + codPaciente + "' AND " + regDAO.COLUNA_TIPO_USER + "= '" + Utils.tipo_modo(context) + "'", regDAO.COLUNA_DATAHORA + " ASC", "100");				
		c.moveToFirst();
		while (!c.isAfterLast()) {
			medicoPaciente.add(regDAO.deCursorParaRegistro(c));
			c.moveToNext();
		}
		regDAO.close();
		return medicoPaciente;
	}
}
