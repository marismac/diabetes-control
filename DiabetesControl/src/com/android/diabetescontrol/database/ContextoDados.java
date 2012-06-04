package com.android.diabetescontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContextoDados {

	protected static final String NOME_BD = "DiabetesControl";
	protected static final int VERSAO_BD = 1;
	protected static final String LOG_TAG = "ContextoDados";
	protected final Context contexto;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
			if (!db.isReadOnly()) {
				db.execSQL("PRAGMA foreign_keys=ON;");
			}
		}

		public void insereMedicamentos(SQLiteDatabase db) {
			ContentValues values = new ContentValues();
			values.put(MedicamentoDAO.COLUNA_TIPO, "Humalog (Insulina Lispro)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Novomix (Insulina Asparte)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Lantus (Insulina Glargina)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Novolin (Insulina Humana)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Novorapid (Insulina Asparte)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Humulin (Insulina Humana)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Levemir (Insulina Detemir)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Apidra (Insulina Glulisina)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Insunorm (Insulina Humana para uso subcutâneo)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Outro (Insulina lispro)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Outro (Insulina asparte)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Outro (Insulina solúvel ou regular)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Outro (Insulina isófano ou insulina protamina-zinco)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Outro (Insulina com protamina)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO, "Outro (Insulina zinco)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
			values.put(MedicamentoDAO.COLUNA_TIPO,
					"Outro (Insulina solúvel + Insulina isofano)");
			db.insert(MedicamentoDAO.TABELA_MEDICAMENTOS, null, values);
		}

		DatabaseHelper(Context context) {
			super(context, NOME_BD, null, VERSAO_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(RegistroDAO.REGISTROS_CREATE_TABLE);
			db.execSQL(PacienteDAO.PACIENTES_CREATE_TABLE);
			db.execSQL(MedicoDAO.MEDICO_CREATE_TABLE);
			db.execSQL(MedicamentoDAO.MEDICAMENTOS_CREATE_TABLE);
			db.execSQL(NotaRegistroMedicoDAO.NOTA_REGISTRO_MEDICO_CREATE_TABLE);

			insereMedicamentos(db);

			Log.w("DbAdapter", "DB criado com sucesso!");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(LOG_TAG, "Atualizando o banco de dados da versï¿½o "
					+ oldVersion + " para " + newVersion
					+ ", todos os dados serï¿½o perdidos!");
			// db.execSQL("DROP TABLE IF EXISTS " +
			// CategoriaDAO.TABELA_CATEGORIA);
			// db.execSQL("DROP TABLE IF EXISTS " +
			// EmprestimoDAO.TABELA_EMPRESTIMOS);
			onCreate(db);
		}
	}

	public ContextoDados(Context ctx) {
		this.contexto = ctx;
	}

	public SQLiteDatabase open() throws SQLException {
		mDbHelper = new DatabaseHelper(contexto);
		mDb = mDbHelper.getWritableDatabase();
		return mDb;
	}

	public void close() {
		mDbHelper.close();
		mDb.close();
	}
}