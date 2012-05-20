package com.android.diabetescontrol.database;

import java.sql.Timestamp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.android.diabetescontrol.model.RegistroMedico;
import com.android.diabetescontrol.util.Constante;

public class RegistroMedicoDAO extends BasicoDAO {

	public RegistroMedicoDAO(Context ctx) {
		super(ctx);
	}

	public static final String TABELA_REGISTRO_MEDICO = "REGISTRO_MEDICO";

	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_TIPO = "TIPO";
	public static final String COLUNA_DATAHORA = "DATAHORA";
	public static final String COLUNA_CATEGORIA = "CATEGORIA";
	public static final String COLUNA_VALOR = "VALOR";
	public static final String COLUNA_CODPACIENTE = "CODPACIENTE";

	public static final String REGISTROS_CREATE_TABLE = "CREATE TABLE "
			+ TABELA_REGISTRO_MEDICO + "  (" + COLUNA_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_DATAHORA
			+ " TIMESTAMP," + COLUNA_TIPO + " TEXT NOT NULL,"
			+ COLUNA_CATEGORIA + " INTEGER NOT NULL," + COLUNA_CODPACIENTE
			+ " TEXT NOT NULL, " + COLUNA_VALOR + ");";

	public void criarRegistro(RegistroMedico regMed) {
		ContentValues values = deRegistroMedicoParaContentValues(regMed);
		mDb.insert(TABELA_REGISTRO_MEDICO, null, values);
	}

	public static ContentValues deRegistroMedicoParaContentValues(
			RegistroMedico regMed) {
		ContentValues values = new ContentValues();

		values.put(COLUNA_TIPO, regMed.getTipo());
		values.put(COLUNA_CATEGORIA, regMed.getCategoria());
		values.put(COLUNA_DATAHORA, regMed.getDatahora().getTime());
		values.put(COLUNA_VALOR, regMed.getValor());
		values.put(COLUNA_CODPACIENTE, regMed.getCodPaciente());

		return values;
	}

	public RegistroMedico deCursorParaRegistroMedico(Cursor c) {
		if (c == null || c.getCount() < 1) {
			return null;
		}
		RegistroMedico regMed = new RegistroMedico();
		regMed.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
		regMed.setDatahora(new Timestamp(c.getLong(c
				.getColumnIndex(COLUNA_DATAHORA))));
		regMed.setCategoria(c.getString(c.getColumnIndex(COLUNA_CATEGORIA)));
		regMed.setTipo(c.getString(c.getColumnIndex(COLUNA_TIPO)));
		regMed.setValor(c.getInt(c.getColumnIndex(COLUNA_VALOR)));
		regMed.setCodPaciente(c.getString(c.getColumnIndex(COLUNA_CODPACIENTE)));

		return regMed;
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
	public Cursor consultarRegistrosMedicosPorTipo(String tipoCategoria) {
		String selection = COLUNA_TIPO + " LIKE '" + tipoCategoria + "'";
		Cursor mCursor = mDb.query(TABELA_REGISTRO_MEDICO, null, selection,
				null, null, null, null);
		return mCursor;

	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros dos Medicos,
	 * informando um tipo de ordenação.
	 * 
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * 
	 * @return Cursos com os Registros ordenados
	 */
	public Cursor consultarTodosRegistrosMedicosOrdenados(String orderby) {
		return mDb.query(TABELA_REGISTRO_MEDICO, new String[] { COLUNA_ID,
				COLUNA_DATAHORA, COLUNA_VALOR, COLUNA_TIPO, COLUNA_CATEGORIA },
				null, null, null, null, orderby);
	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros dos Medicos
	 * selecionando uma quantidade máxima e informando um tipo de ordenação.
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
	public Cursor consultarAlgunsRegistrosMedicosGlicoseOrdenados(
			String orderby, Integer qtde) {
		return mDb.query(TABELA_REGISTRO_MEDICO, null, COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "'", null, null, null, orderby,
				qtde.toString());
	}

	/**
	 * 
	 * Permite realizar uma consulta na tabela de Registros dos Medicos
	 * selecionando determinados registros e ordenando os mesmos.
	 * 
	 * @param orderby
	 *            Coluna para informar a ordenação, que deve ser excluir o ORDER
	 *            BY. Exemplos: DATAHORA ASC ou VALOR DESC
	 * @param where
	 *            Coluna para informar uma clausola where a ser utilizada na
	 *            consulta. NÃO DEVE CONTER O WHERE
	 * @param limity
	 *            Coluna para informar um número limíte de valores a serem apresentados
	 * 
	 * @return Cursor com os Registros selecionados e ordenados
	 */
	public Cursor consultarRegistrosWhereOrderLimit(String where, String orderby, String limity) {
		return mDb.query(TABELA_REGISTRO_MEDICO, null, where, null, null, null,
				orderby, limity);
	}

}
