package com.android.diabetescontrol.database;

import java.sql.Date;
import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;

import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Constante;

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
	public static final String COLUNA_VALOR_PRESSAO = "VALOR_PRESSAO";
	public static final String COLUNA_UNIDADE = "UNIDADE";
	public static final String COLUNA_SINCRONIZADO = "SINCRONIZADO";
	public static final String COLUNA_CODPAC = "CODIGO_PACIENTE";
	public static final String COLUNA_ID_REGISTRO_PAC = "ID_REGISTRO_PAC";
	public static final String COLUNA_TIPO_USER = "TIPO_USER";
	public static final String COLUNA_ID_MEDICAMENTO = "ID_MEDICAMENTO";

	public static final String REGISTROS_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_REGISTRO + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_DATAHORA
			+ " TIMESTAMP, " + COLUNA_TIPO + " TEXT NOT NULL, "
			+ COLUNA_CATEGORIA + " TEXT NOT NULL, " + COLUNA_VALOR
			+ " DECIMAL(6, 2), " + COLUNA_UNIDADE + " TEXT NOT NULL, "
			+ COLUNA_SINCRONIZADO + " TEXT NOT NULL, " + COLUNA_CODPAC
			+ " TEXT, " + COLUNA_ID_REGISTRO_PAC + " INTEGER, "
			+ COLUNA_TIPO_USER + " TEXT NOT NULL," + COLUNA_ID_MEDICAMENTO
			+ " INTEGER REFERENCES " + MedicamentoDAO.TABELA_MEDICAMENTOS
			+ " ( " + MedicamentoDAO.COLUNA_ID + ") ," + COLUNA_VALOR_PRESSAO
			+ " TEXT );";

	public void criarRegistro(Registro registro) {
		ContentValues values = deRegistroParaContentValues(registro);
		mDb.insert(TABELA_REGISTRO, null, values);
	}

	public void atualizaRegistro(Registro registro) {
		ContentValues values = deRegistroParaContentValues(registro);
		mDb.update(TABELA_REGISTRO, values,
				COLUNA_ID + " = " + registro.getId(), null);
	}

	public static ContentValues deRegistroParaContentValues(Registro registro) {
		ContentValues values = new ContentValues();
		if (registro.getId() != null) {
			values.put(COLUNA_ID, registro.getId());
		}

		values.put(COLUNA_TIPO, registro.getTipo());
		values.put(COLUNA_CATEGORIA, registro.getCategoria());
		if (registro.getDataHora() != null) {
			values.put(COLUNA_DATAHORA, registro.getDataHora().getTime());
		}
		values.put(COLUNA_VALOR, registro.getValor());
		values.put(COLUNA_UNIDADE, registro.getUnidade());
		values.put(COLUNA_SINCRONIZADO, registro.getSincronizado());
		values.put(COLUNA_CODPAC, registro.getCodPaciente());
		values.put(COLUNA_ID_REGISTRO_PAC, registro.getCodCelPac());
		values.put(COLUNA_TIPO_USER, registro.getModoUser());
		values.put(COLUNA_ID_MEDICAMENTO, registro.getMedicamento());
		values.put(COLUNA_VALOR_PRESSAO, registro.getValorPressao());

		return values;
	}

	// public boolean atualizarRegistro(Registro regis) {
	// ContentValues values = new ContentValues();
	// return mDb.update(TABELA_REGISTRO, values, COLUNA_ID + "=?",
	// new String[] { String.valueOf(regis.getId()) }) > 0;
	// }

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
		reg.setDataHora(new Timestamp(c.getLong(c
				.getColumnIndex(COLUNA_DATAHORA))));
		reg.setCategoria(c.getString(c.getColumnIndex(COLUNA_CATEGORIA)));
		reg.setTipo(c.getString(c.getColumnIndex(COLUNA_TIPO)));
		reg.setValor(c.getFloat(c.getColumnIndex(COLUNA_VALOR)));
		reg.setUnidade(c.getString(c.getColumnIndex(COLUNA_UNIDADE)));
		reg.setSincronizado(c.getString(c.getColumnIndex(COLUNA_SINCRONIZADO)));
		reg.setCodPaciente(c.getString(c.getColumnIndex(COLUNA_CODPAC)));
		reg.setCodCelPac(c.getInt(c.getColumnIndex(COLUNA_ID_REGISTRO_PAC)));
		reg.setModoUser(c.getString(c.getColumnIndex(COLUNA_TIPO_USER)));
		reg.setMedicamento(c.getInt(c.getColumnIndex(COLUNA_ID_MEDICAMENTO)));
		reg.setValorPressao(c.getString(c.getColumnIndex(COLUNA_VALOR_PRESSAO)));
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

	public Cursor consultarTodosRegistrosV1() {

		return mDb.query(TABELA_REGISTRO, new String[] { COLUNA_ID,
				COLUNA_DATAHORA, COLUNA_VALOR, COLUNA_TIPO, COLUNA_CATEGORIA },
				null, null, null, null, null);
	}
	
	public Cursor consultar(String sql) {
		return mDb.rawQuery(sql, null);
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

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros selecionando uma
	 * quantidade máxima e informando um tipo de ordenação.
	 * 
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * @param qtde
	 *            Coluna para informar a quantidade de registros a serem
	 *            trazidos na consulta
	 * 
	 * @return Cursos com os Registros ordenados
	 */
	public Cursor consultarAlgunsRegistrosGlicoseOrdenados(String orderby,
			Integer qtde) {
		return mDb.query(TABELA_REGISTRO, null, COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "'", null, null, null, orderby,
				qtde.toString());
	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros selecionando
	 * determinados registros e ordenando os mesmos.
	 * 
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * @param where
	 *            Coluna para informar uma clausola where a ser utilizada na
	 *            consulta. NÃO DEVE CONTER O WHERE
	 * 
	 * @return Cursor com os Registros selecionados e ordenados
	 */
	public Cursor consultarRegistrosWhereOrder(String where, String orderby) {
		return mDb.query(TABELA_REGISTRO, null, where, null, null, null,
				orderby);
	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros selecionando
	 * determinados registros e ordenando os mesmos.
	 * 
	 * @param where
	 *            Coluna para informar uma clausola where a ser utilizada na
	 *            consulta. NÃO DEVE CONTER O WHERE
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * @param limit
	 *            Coluna para informar o número limite de dados apresentados
	 * 
	 * @return Cursor com os Registros selecionados e ordenados
	 */
	public Cursor consultarRegistrosWhereOrderLimit(String where,
			String orderby, String limit) {
		return mDb.query(TABELA_REGISTRO, null, where, null, null, null,
				orderby, limit);
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