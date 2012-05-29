package com.android.diabetescontrol.database;

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

		DatabaseHelper(Context context) {
			super(context, NOME_BD, null, VERSAO_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(RegistroDAO.REGISTROS_CREATE_TABLE);
			db.execSQL(PacienteDAO.PACIENTES_CREATE_TABLE);
			db.execSQL(MedicoDAO.MEDICO_CREATE_TABLE);
			db.execSQL(NotaRegistroMedicoDAO.NOTA_REGISTRO_MEDICO_CREATE_TABLE);

			Log.w("DbAdapter", "DB criado com sucesso!");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(LOG_TAG, "Atualizando o banco de dados da vers�o "
					+ oldVersion + " para " + newVersion
					+ ", todos os dados ser�o perdidos!");
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