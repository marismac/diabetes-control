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
	public static final String COLUNA_DESCRICAO = "DESCRICAO";
	public static final String COLUNA_SINCRONIZADO = "SINCRONIZADO";
	public static final String COLUNA_TIPO_USER = "TIPO_USER";

	public static final String NOTA_REGISTRO_MEDICO_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_NOTA_REGISTRO_MEDICO
			+ "  ("
			+ COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUNA_ID_REGISTRO_MEDICO
			+ " INTEGER NOT NULL REFERENCES "
			+ RegistroDAO.TABELA_REGISTRO
			+ " ("
			+ RegistroDAO.COLUNA_ID
			+ ") ,"
			+ COLUNA_SINCRONIZADO
			+ " TEXT NOT NULL,"
			+ COLUNA_DESCRICAO
			+ " TEXT NOT NULL, "
			+ COLUNA_TIPO_USER
			+ " TEXT NOT NULL);";

	public void criarNotaRegistroMedico(NotaRegistroMedico notaregMed) {
		ContentValues values = deNotaRegistroMedicoParaContentValues(notaregMed);
		mDb.insert(TABELA_NOTA_REGISTRO_MEDICO, null, values);
	}

	public void atualizaNota(NotaRegistroMedico notaregMed) {
		ContentValues values = deNotaRegistroMedicoParaContentValues(notaregMed);
		mDb.update(TABELA_NOTA_REGISTRO_MEDICO, values, COLUNA_ID + " = "
				+ notaregMed.getId(), null);
	}

	public static ContentValues deNotaRegistroMedicoParaContentValues(
			NotaRegistroMedico notaregMed) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_SINCRONIZADO, notaregMed.getSincronizado());
		values.put(COLUNA_ID, notaregMed.getId());
		values.put(COLUNA_ID_REGISTRO_MEDICO, notaregMed.getIdRegistro());
		values.put(COLUNA_TIPO_USER, notaregMed.getTipoUser());
		values.put(COLUNA_DESCRICAO, notaregMed.getDescricao());

		return values;
	}

	public NotaRegistroMedico deCursorParaNotaRegistroMedico(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		NotaRegistroMedico notaregMed = new NotaRegistroMedico();
		notaregMed.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		notaregMed.setIdRegistro(c.getInt(c
				.getColumnIndex(COLUNA_ID_REGISTRO_MEDICO)));
		notaregMed
				.setDescricao(c.getString(c.getColumnIndex(COLUNA_DESCRICAO)));
		notaregMed.setSincronizado(c.getString(c
				.getColumnIndex(COLUNA_SINCRONIZADO)));
		notaregMed.setTipoUser(c.getString(c.getColumnIndex(COLUNA_TIPO_USER)));
		notaregMed.setInfoRegistro(infoRegistros(notaregMed.getId()));
		notaregMed.setCodPaciente(codPaciente(notaregMed.getId()));

		return notaregMed;
	}

	private String codPaciente(Integer idRegistro) {
		Cursor c = mDb.query("REGISTROS", null, "_id = " + idRegistro, null,
				null, null, null);
		c.moveToFirst();
		return c.getString(c.getColumnIndex("CODIGO_PACIENTE"));
	}

	private String infoRegistros(Integer idRegistro) {
		Cursor c = mDb.query("REGISTROS", null, "_id = " + idRegistro, null,
				null, null, null);
		if (c == null || c.getCount() < 1) {
			return null;
		}
		c.moveToFirst();
		String valor = String.valueOf(c.getFloat(c.getColumnIndex("VALOR")));
		return valor + " " + c.getString(c.getColumnIndex("UNIDADE")) + " de "
				+ c.getString(c.getColumnIndex("TIPO"));
	}

	public Cursor consultarNotaMedicoWhereOrderLimit(String where,
			String orderby, String limity) {
		return mDb.query(TABELA_NOTA_REGISTRO_MEDICO, null, where, null, null,
				null, orderby, limity);
	}

}
