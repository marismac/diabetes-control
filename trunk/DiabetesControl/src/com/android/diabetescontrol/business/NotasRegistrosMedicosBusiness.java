package com.android.diabetescontrol.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.database.NotaRegistroMedicoDAO;
import com.android.diabetescontrol.model.NotaRegistroMedico;
import com.android.diabetescontrol.util.Utils;

public class NotasRegistrosMedicosBusiness {
	@SuppressWarnings("static-access")
	public List<NotaRegistroMedico> getNotasRegistrosPaciente(Context context) {
		List<NotaRegistroMedico> notasRegistroMedico = new ArrayList<NotaRegistroMedico>();
		NotaRegistroMedicoDAO nrmDAO = new NotaRegistroMedicoDAO(context);
		nrmDAO.open();
		Cursor c = nrmDAO.consultarNotaMedicoWhereOrderLimit(
				nrmDAO.COLUNA_TIPO_USER + " = '" + Utils.tipo_modo(context)
						+ "'", nrmDAO.COLUNA_ID + " ASC", "100");
		c.moveToFirst();
		while (!c.isAfterLast()) {
			notasRegistroMedico.add(nrmDAO.deCursorParaNotaRegistroMedico(c));
			c.moveToNext();
		}
		nrmDAO.close();
		return notasRegistroMedico;
	}
}
