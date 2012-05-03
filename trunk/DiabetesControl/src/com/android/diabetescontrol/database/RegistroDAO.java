package com.android.diabetescontrol.database;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.android.diabetescontrol.model.Registro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;

public class RegistroDAO extends BasicoDAO {

	public RegistroDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_REGISTRO = "REGISTROS";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_TIPO = "TIPO";
	public static final String COLUNA_DATAHORA = "DATAHORA";
	public static final String COLUNA_CATEGORIA = "CATEGORIA";
	public static final String COLUNA_VALOR = "VALOR";

	public static final String REGISTROS_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_REGISTRO + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_DATAHORA
			+ " TIMESTAMP," + COLUNA_TIPO + " TEXT NOT NULL,"
			+ COLUNA_CATEGORIA + " INTEGER NOT NULL," + COLUNA_VALOR + ");";

	public void criarRegistro(Registro registro) {
		ContentValues values = deRegistroParaContentValues(registro);
		mDb.insert(TABELA_REGISTRO, null, values);
	}

	public static ContentValues deRegistroParaContentValues(Registro registro) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_TIPO, registro.getTipo());
		values.put(COLUNA_CATEGORIA, registro.getCategoria());
		values.put(COLUNA_DATAHORA, registro.getDatahora().getTime());
		values.put(COLUNA_VALOR, registro.getValor());

		return values;
	}

	public boolean atualizarRegistro(Registro regis) {
		ContentValues values = new ContentValues();
		return mDb.update(TABELA_REGISTRO, values, COLUNA_ID + "=?",
				new String[] { String.valueOf(regis.getId()) }) > 0;
	}

	public Registro getRegistro(long idRegistro) {

		Cursor c = consultarRegistros(idRegistro);
		Registro reg = deCursorParaRegistro(c);
		c.close();

		return reg;
	}

	public Registro deCursorParaRegistro(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		Registro reg = new Registro();
		reg.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		reg.setDatahora(new Timestamp(c.getLong(c
				.getColumnIndex(COLUNA_DATAHORA))));
		reg.setCategoria(c.getString(c.getColumnIndex(COLUNA_CATEGORIA)));
		reg.setTipo(c.getString(c.getColumnIndex(COLUNA_TIPO)));
		reg.setValor(c.getInt(c.getColumnIndex(COLUNA_VALOR)));

		return reg;
	}

	public boolean removerRegistro(long idRegistro) {
		return mDb.delete(TABELA_REGISTRO, COLUNA_ID + "=?",
				new String[] { String.valueOf(idRegistro) }) > 0;
	}

	public Date getDataRegistro(long idRegistro) {

		Cursor c = consultarRegistros(idRegistro);

		Long mili = c.getLong(c.getColumnIndex(COLUNA_DATAHORA));
		c.close();

		return new Date(mili);
	}

	/**
	 * 
	 * Permite realizar uma consulta de registros, selecionados apeneas
	 * registros de uma determinada categoria.
	 * 
	 * @param tipoCategoria
	 *            Coluna para informar a descrição do tipo de Cateforia para
	 *            realizar os filtros. % busca todos
	 * 
	 * @return Cursor com os registros filtrados
	 */
	public Cursor consultarRegistrosPorTipo(String tipoCategoria) {
		String selection = COLUNA_TIPO + " LIKE '" + tipoCategoria + "'";
		Cursor mCursor = mDb.query(TABELA_REGISTRO, null, selection, null,
				null, null, null);
		return mCursor;

	}

	// public List<Date> consultarQuantosRegistroBetween(Timestamp inicio,
	// Timestamp fim) {
	// List<Date> datas = new ArrayList<Date>();
	//
	// Cursor mCursor = consultarRegistrosBetween(inicio, fim);
	// while (!mCursor.isAfterLast()) {
	// Long mili = mCursor
	// .getLong(mCursor.getColumnIndex(COLUNA_DATAHORA));
	// datas.add(new Date(mili));
	// mCursor.moveToNext();
	// }
	// mCursor.close();
	//
	// return datas;
	//
	// }

	/**
	 * 
	 * Busca a quantia total de registros salvos
	 * 
	 * @return Integer com a quantidade total de registros no banco
	 */
	public Integer consultarQuantosRegistro() {
		Cursor mCursor = mDb.query(TABELA_REGISTRO, null, null, null, null,
				null, null);
		mCursor.getCount();
		return mCursor.getCount();

	}

	// public List<Registro> consultarQuantosRegistrosBetween(Registro inicio,
	// Registro fim) {
	// List<Registro> datas = new ArrayList<Registro>();
	//
	// Cursor mCursor = consultarRegistrosBetween(inicio.getDatahora(),
	// fim.getDatahora());
	// while (!mCursor.isAfterLast()) {
	// Registro reg = deCursorParaRegistro(mCursor);
	// datas.add(reg);
	// mCursor.moveToNext();
	// }
	// mCursor.close();
	//
	// return datas;
	//
	// }

	public Cursor consultarTodosRegistrosV1() {

		return mDb.query(TABELA_REGISTRO, new String[] { COLUNA_ID,
				COLUNA_DATAHORA, COLUNA_VALOR, COLUNA_TIPO, COLUNA_CATEGORIA },
				null, null, null, null, null);
	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros, informando um tipo
	 * de ordenação.
	 * 
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * 
	 * @return Cursos com os Registros ordenados
	 */
	public Cursor consultarTodosRegistrosOrdenados(String orderby) {
		return mDb.query(TABELA_REGISTRO, new String[] { COLUNA_ID,
				COLUNA_DATAHORA, COLUNA_VALOR, COLUNA_TIPO, COLUNA_CATEGORIA },
				null, null, null, null, orderby);
	}

	// Retorna TUDO que estiver na tabela Emprestimos, assim como o método V1,
	// mas é bem mais simples.
	public Cursor consultarTodosRegistrosV2() {

		return mDb.query(TABELA_REGISTRO, null, null, null, null, null, null);
	}

	public Cursor consultarRegistros(long idRegistro) throws SQLException {

		Cursor mCursor = mDb.query(true, TABELA_REGISTRO, null, COLUNA_ID
				+ "=?", new String[] { String.valueOf(idRegistro) }, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor consultarRegistrosPorCategoria(long idCategoria)
			throws SQLException {

		Cursor mCursor =

		mDb.query(true, TABELA_REGISTRO, null, COLUNA_ID + "=?",
				new String[] { String.valueOf(idCategoria) }, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public long getNumeroDeRegistros() {
		return DatabaseUtils.queryNumEntries(mDb, TABELA_REGISTRO);
	}

}
