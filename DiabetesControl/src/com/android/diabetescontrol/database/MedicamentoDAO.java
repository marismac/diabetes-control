package com.android.diabetescontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.model.Medicamento;

public class MedicamentoDAO extends BasicoDAO {

	public MedicamentoDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_MEDICAMENTOS = "MEDICAMENTOS";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_TIPO = "TIPO";
	public static final String COLUNA_CODPAC = "COPPAC";

	public static final String MEDICAMENTOS_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_MEDICAMENTOS + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MedicamentoDAO.COLUNA_TIPO + " TEXT NOT NULL, "
			+ MedicamentoDAO.COLUNA_CODPAC + " TEXT );";

	public void criarRegistro(Medicamento medicamento) {
		ContentValues values = deMedicamentoParaContentValues(medicamento);
		mDb.insert(TABELA_MEDICAMENTOS, null, values);
	}

	public static ContentValues deMedicamentoParaContentValues(
			Medicamento medicamento) {
		ContentValues values = new ContentValues();
		values.put(COLUNA_TIPO, medicamento.getTipo());
		values.put(COLUNA_CODPAC, medicamento.getDescricao());
		return values;
	}

	public Medicamento deCursorParaMedicamento(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		Medicamento med = new Medicamento();
		med.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		med.setTipo(c.getString(c.getColumnIndex(COLUNA_TIPO)));
		med.setDescricao(c.getString(c.getColumnIndex(COLUNA_CODPAC)));

		return med;
	}

	public Cursor consultarMedicamentos() {
		return mDb.query(TABELA_MEDICAMENTOS, null, null, null, null, null,
				null);
	}
}