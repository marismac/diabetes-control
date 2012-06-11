package com.android.diabetescontrol.database;

import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.model.Paciente;

public class PacienteDAO extends BasicoDAO {

	public PacienteDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_PACIENTES = "PACIENTE";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_NOME = "NOME";
	public static final String COLUNA_EMAIL = "EMAIL";
	public static final String COLUNA_NASCIMENTO = "NASCIMENTO";
	public static final String COLUNA_SEXO = "SEXO";
	public static final String COLUNA_CODPAC = "CODIGO_PACIENTE";
	public static final String COLUNA_SENHAPAC = "SENHA_PACIENTE";

	public static final String PACIENTES_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_PACIENTES + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NASCIMENTO
			+ " TIMESTAMP," + COLUNA_EMAIL + " TEXT NOT NULL," + COLUNA_NOME
			+ " TEXT NOT NULL," + COLUNA_SEXO + " TEXT," + COLUNA_CODPAC
			+ " TEXT NOT NULL," + COLUNA_SENHAPAC + " TEXT);";

	public void criarPaciente(Paciente paciente) {
		ContentValues values = dePacienteParaContentValues(paciente);
		mDb.insert(TABELA_PACIENTES, null, values);
	}

	public Integer consultarUltimoPaciente() {
		Cursor mCursor = mDb.rawQuery("SELECT MAX(_id) FROM "
				+ PacienteDAO.TABELA_PACIENTES, null);
		mCursor.moveToFirst();
		if (mCursor.getCount() > 0) {
			return null;
		}
		return mCursor.getInt(mCursor.getColumnIndex(mCursor.getColumnName(0)));
	}

	public String getCodPac() {
		Cursor mCursor = mDb.rawQuery("SELECT " + PacienteDAO.COLUNA_CODPAC
				+ " FROM " + PacienteDAO.TABELA_PACIENTES + " WHERE "
				+ PacienteDAO.COLUNA_CODPAC + " IS NOT NULL AND "
				+ PacienteDAO.COLUNA_SENHAPAC + " IS NOT NULL", null);
		mCursor.moveToFirst();
		if (mCursor.getCount() <= 0) {
			return null;
		}
		return mCursor.getString(mCursor.getColumnIndex(mCursor
				.getColumnName(0)));
	}
	
	public String consultarPacienteCodPac(String codPac) {
		Cursor mCursor = mDb.rawQuery("SELECT " + PacienteDAO.COLUNA_CODPAC
				+ " FROM " + PacienteDAO.TABELA_PACIENTES + " WHERE "
				+ PacienteDAO.COLUNA_CODPAC + " = '" + codPac + "' AND "
				+ PacienteDAO.COLUNA_SENHAPAC + " IS NULL", null);
		mCursor.moveToFirst();
		if (mCursor.getCount() <= 0) {
			return null;
		}
		return mCursor.getString(mCursor.getColumnIndex(mCursor
				.getColumnName(0)));
	}

	public static ContentValues dePacienteParaContentValues(Paciente paciente) {
		ContentValues values = new ContentValues();
		values.put(COLUNA_ID, paciente.getId());
		values.put(COLUNA_NOME, paciente.getNome());
		values.put(COLUNA_EMAIL, paciente.getEmail());
		if (paciente.getDatanascimento() != null) {
			values.put(COLUNA_NASCIMENTO, paciente.getDatanascimento()
					.getTime());
		}
		values.put(COLUNA_SEXO, paciente.getSexo());
		values.put(COLUNA_CODPAC, paciente.getCodPaciente());
		values.put(COLUNA_SENHAPAC, paciente.getSenhaPaciente());

		return values;
	}

	public boolean atualizarPaciente(Paciente paciente) {
		ContentValues values = dePacienteParaContentValues(paciente);
		return mDb.update(TABELA_PACIENTES, values, COLUNA_ID + "= ?",
				new String[] { String.valueOf(paciente.getId()) }) > 0;
	}

	public Paciente deCursorParaPaciente(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		Paciente paciente = new Paciente();
		paciente.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		paciente.setDatanascimento(new Timestamp(c.getLong(c
				.getColumnIndex(COLUNA_NASCIMENTO))));
		paciente.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));
		paciente.setEmail(c.getString(c.getColumnIndex(COLUNA_EMAIL)));
		paciente.setSexo(c.getString(c.getColumnIndex(COLUNA_SEXO)));
		paciente.setCodPaciente(c.getString(c.getColumnIndex(COLUNA_CODPAC)));
		paciente.setSenhaPaciente(c.getString(c.getColumnIndex(COLUNA_SENHAPAC)));

		return paciente;
	}

	public boolean removerPaciente(long idPaciente) {
		return mDb.delete(TABELA_PACIENTES, COLUNA_ID + "=?",
				new String[] { String.valueOf(idPaciente) }) > 0;
	}

	public Cursor consultarPacientesWhereOrder(String where, String orderby) {
		return mDb.query(TABELA_PACIENTES, null, where, null, null, null,
				orderby);
	}
}
