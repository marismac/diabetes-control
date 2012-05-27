package com.android.diabetescontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.model.NotaRegistroMedico;

public class NotaRegistroMedicoDAO extends BasicoDAO {

	public NotaRegistroMedicoDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_NOTA_REGISTRO_MEDICO = "NOTA_REGISTRO_MEDICO";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_ID_REGISTRO_MEDICO = "REGISTRO_MEDICO_ID";
	public static final String COLUNA_TIPO = "TIPO";
	public static final String COLUNA_DESCRICAO = "DESCRICAO";

	public static final String REGISTROS_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_NOTA_REGISTRO_MEDICO + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUNA_ID_REGISTRO_MEDICO + " INTEGER NOT NULL REFERENCES "
			+ RegistroDAO.TABELA_REGISTRO + " "
			+ RegistroDAO.COLUNA_ID + "," + COLUNA_TIPO
			+ " TEXT NOT NULL," + COLUNA_DESCRICAO + " TEXT NOT NULL);";

	public void criarNotaRegistroMedico(NotaRegistroMedico notaregMed) {
		ContentValues values = deNotaRegistroMedicoParaContentValues(notaregMed);
		mDb.insert(TABELA_NOTA_REGISTRO_MEDICO, null, values);
	}

	public static ContentValues deNotaRegistroMedicoParaContentValues(
			NotaRegistroMedico notaregMed) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_TIPO, notaregMed.getTipo());
		values.put(COLUNA_ID, notaregMed.getId());
		values.put(COLUNA_ID_REGISTRO_MEDICO, notaregMed.getId_registro());
		values.put(COLUNA_DESCRICAO, notaregMed.getDescricao());

		return values;
	}

	public NotaRegistroMedico deCursorParaNotaRegistroMedico(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		NotaRegistroMedico notaregMed = new NotaRegistroMedico();
		notaregMed.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		notaregMed.setId_registro(c.getInt(c
				.getColumnIndex(COLUNA_ID_REGISTRO_MEDICO)));
		notaregMed
				.setDescricao(c.getString(c.getColumnIndex(COLUNA_DESCRICAO)));
		notaregMed.setTipo(c.getString(c.getColumnIndex(COLUNA_TIPO)));

		return notaregMed;
	}

	public Cursor consultarRegistrosWhereOrderLimit(String where,
			String orderby, String limity) {
		return mDb.query(TABELA_NOTA_REGISTRO_MEDICO, null, where, null, null,
				null, orderby, limity);
	}

}
