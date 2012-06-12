package com.diabetescontrol.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.DataUtil;

public class GraficoMediaCategoria {
	public Intent getIntent(Context context) {

		int[] x1 = getMediasCategoriasSemana(context);
		CategorySeries series = new CategorySeries("Semana");
		for (int i = 0; i < x1.length; i++) {
			series.add(x1[i]);
		}

		int[] x2 = getMediasCategorias(context);
		CategorySeries series2 = new CategorySeries("Geral");
		for (int i = 0; i < x2.length; i++) {
			series2.add(x2[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		dataset.addSeries(series2.toXYSeries());

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float) 3.0);
		renderer.setColor(Color.BLUE);

		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setDisplayChartValues(true);
		renderer2.setChartValuesSpacing((float) 3.0);
		renderer2.setColor(Color.DKGRAY);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);

		mRenderer.setChartTitle("Média por Categoria");
		mRenderer.setXTitle("Categorias");
		mRenderer.setYTitle("Valor");
		mRenderer.setBarSpacing((float) 0.5);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.parseColor("#F5F5F5"));
		mRenderer.setMarginsColor(Color.parseColor("#F5F5F5"));
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setXLabels(1);
		mRenderer.addXTextLabel(1, "Café Antes");
		mRenderer.addXTextLabel(2, "Café Depois");
		mRenderer.addXTextLabel(3, "Lanche Antes");
		mRenderer.addXTextLabel(4, "Lanche Depois");
		mRenderer.addXTextLabel(5, "Almoço Antes");
		mRenderer.addXTextLabel(6, "Almoço Depois");
		mRenderer.addXTextLabel(7, "Janta Antes");
		mRenderer.addXTextLabel(8, "Janta Depois");

		Intent intent = ChartFactory.getBarChartIntent(context, dataset,
				mRenderer, Type.DEFAULT);
		return intent;
	}

	public int[] getMediasCategorias(Context ctx) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		List<String> sqlList = getSqlList(regDao);
		regDao.open();
		int valores[] = new int[sqlList.size()];
		// Integer[] valores = new Integer[sqlList.size()];
		for (int i = 0; i < sqlList.size(); i++) {
			Cursor c = regDao.consultar(sqlList.get(i));
			c.moveToFirst();
			Float valor;
			if (c.getCount() > 0) {
				valor = c.getFloat(c.getColumnIndex(c.getColumnName(0)));
			} else {
				valor = 0f;
			}
			valores[i] = valor.intValue();
		}
		return valores;
	}

	private String getAddSQLPermissao() {
		String sqlAddPaciente = "";
		if (Constante.TIPO_MODO_MEDICO.equals(Constante.TIPO_MODO)) {
			sqlAddPaciente = " AND " + RegistroDAO.COLUNA_TIPO_USER + " = '"
					+ Constante.TIPO_MODO + "' " + " AND "
					+ RegistroDAO.COLUNA_CODPAC + " = '"
					+ Paciente.getCODIGOPACIENTE() + "' ";
		} else {
			sqlAddPaciente = " AND " + RegistroDAO.COLUNA_TIPO_USER + " = '"
					+ Constante.TIPO_MODO + "' ";
		}
		return sqlAddPaciente;
	}

	@SuppressWarnings("static-access")
	private List<String> getSqlList(RegistroDAO regDao) {
		List<String> sqlList = new ArrayList<String>();
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_A_CAFE
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_D_CAFE
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_A_LANCHE
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_D_LANCHE
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_A_ALMOCO
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_D_ALMOCO
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_A_JANTA
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT AVG(" + regDao.COLUNA_VALOR + ") FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' AND "
				+ regDao.COLUNA_CATEGORIA + " = '" + Constante.CAT_D_JANTA
				+ "' " + getAddSQLPermissao() + " GROUP BY "
				+ regDao.COLUNA_CATEGORIA);
		return sqlList;
	}

	@SuppressWarnings("static-access")
	private List<String> getSqlListSemana(RegistroDAO regDao) {
		List<String> sqlList = new ArrayList<String>();
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_A_CAFE + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_D_CAFE + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_A_LANCHE + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_D_LANCHE + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_A_ALMOCO + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_D_ALMOCO + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_A_JANTA + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		sqlList.add("SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '"
				+ Constante.TIPO_GLICOSE + "' AND " + regDao.COLUNA_CATEGORIA
				+ " = '" + Constante.CAT_D_JANTA + "' " + getAddSQLPermissao()
				+ " GROUP BY " + regDao.COLUNA_CATEGORIA);
		return sqlList;
	}

	private int[] getMediasCategoriasSemana(Context ctx) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		List<String> sqlList = getSqlListSemana(regDao);
		Date data = new DataUtil().getPrimeiroDiaSemana();
		regDao.open();
		int valores[] = new int[sqlList.size()];
		// Integer[] valores = new Integer[sqlList.size()];
		for (int i = 0; i < sqlList.size(); i++) {
			Float valor = null;
			Date dt;
			Cursor c = regDao.consultar(sqlList.get(i));
			c.moveToFirst();
			if (c.getCount() > 0) {
				int contMedioHoje = 0;
				Float valorMedioHoje = 0f;
				while (!c.isAfterLast()) {
					valor = c.getFloat(c.getColumnIndex(c.getColumnName(0)));
					dt = new Date(
							c.getLong(c.getColumnIndex(c.getColumnName(1))));
					if (!dt.before(data)) {
						++contMedioHoje;
						valorMedioHoje += valor;
					}
					c.moveToNext();
				}
				valor = valorMedioHoje / contMedioHoje;
			} else {
				valor = 0f;
			}
			valores[i] = valor.intValue();
		}
		return valores;
	}
}