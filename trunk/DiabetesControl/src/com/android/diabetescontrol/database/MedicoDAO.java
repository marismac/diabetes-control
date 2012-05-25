package com.android.diabetescontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.model.Medico;

public class MedicoDAO extends BasicoDAO {

	public MedicoDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_MEDICO = "MEDICO";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_NOME = "NOME";
	public static final String COLUNA_EMAIL = "EMAIL";
	public static final String COLUNA_REGISTRO = "REGISTRO";
	public static final String COLUNA_CODWS = "WS";

	public static final String MEDICO_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_MEDICO + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_EMAIL
			+ " TEXT NOT NULL," + COLUNA_NOME + " TEXT NOT NULL,"
			+ COLUNA_REGISTRO + " TEXT," + COLUNA_CODWS + " TEXT NOT NULL);";

	public void criarMedico(Medico medico) {
		ContentValues values = deMedicoParaContentValues(medico);
		mDb.insert(TABELA_MEDICO, null, values);
	}

	public static ContentValues deMedicoParaContentValues(Medico medico) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_ID, medico.getId());
		values.put(COLUNA_NOME, medico.getNome());
		values.put(COLUNA_EMAIL, medico.getEmail());
		values.put(COLUNA_CODWS, medico.getCodWS());
		values.put(COLUNA_REGISTRO, medico.getRegistro());

		return values;
	}

	public boolean atualizarMedico(Medico medico) {
		ContentValues values = new ContentValues();
		return mDb.update(TABELA_MEDICO, values, COLUNA_ID + "=?",
				new String[] { String.valueOf(medico.getId()) }) > 0;
	}

	public Medico deCursorParaMedico(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		Medico medico = new Medico();
		medico.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		medico.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));
		medico.setEmail(c.getString(c.getColumnIndex(COLUNA_EMAIL)));
		medico.setCodWS(c.getString(c.getColumnIndex(COLUNA_CODWS)));
		medico.setRegistro(c.getString(c.getColumnIndex(COLUNA_REGISTRO)));

		return medico;
	}

	public boolean removerMedico(long idMedico) {
		return mDb.delete(TABELA_MEDICO, COLUNA_ID + "=?",
				new String[] { String.valueOf(idMedico) }) > 0;
	}
	
	public Cursor consultarMedicosWhereOrder(String where,
			String orderby) {
		return mDb.query(TABELA_MEDICO, null, where, null, null, null,
				orderby);
	}
}