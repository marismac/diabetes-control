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
	public static final String COLUNA_CODWS = "WS";
	public static final String COLUNA_EDITAR = "EDITAR";

	public static final String PACIENTES_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_PACIENTES + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NASCIMENTO
			+ " TIMESTAMP NOT NULL," + COLUNA_EMAIL + " TEXT NOT NULL,"
			+ COLUNA_NOME + " TEXT NOT NULL," + COLUNA_SEXO + " TEXT NOT NULL,"
			+ COLUNA_CODWS + " TEXT NOT NULL," + COLUNA_EDITAR
			+ " TEXT);";

	public void criarRegistro(Paciente paciente) {
		ContentValues values = dePacienteParaContentValues(paciente);
		mDb.insert(TABELA_PACIENTES, null, values);
	}

	public static ContentValues dePacienteParaContentValues(Paciente paciente) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_ID, paciente.getId());
		values.put(COLUNA_NOME, paciente.getNome());
		values.put(COLUNA_EMAIL, paciente.getEmail());
		values.put(COLUNA_NASCIMENTO, paciente.getDatanascimento().getTime());
		values.put(COLUNA_SEXO, paciente.getSexo());
		values.put(COLUNA_CODWS, paciente.getCodWS());
		values.put(COLUNA_EDITAR, paciente.getEditar());

		return values;
	}

	public boolean atualizarPaciente(Paciente paciente) {
		ContentValues values = new ContentValues();
		return mDb.update(TABELA_PACIENTES, values, COLUNA_ID + "=?",
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
		paciente.setCodWS(c.getString(c.getColumnIndex(COLUNA_CODWS)));
		paciente.setEditar(c.getString(c.getColumnIndex(COLUNA_EDITAR)));

		return paciente;
	}

	public boolean removerPaciente(long idPaciente) {
		return mDb.delete(TABELA_PACIENTES, COLUNA_ID + "=?",
				new String[] { String.valueOf(idPaciente) }) > 0;
	}
}
