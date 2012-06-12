package com.android.diabetescontrol.activities;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Constante;

public class GraficoUltimosRegistrosGlicose {
	public Intent getIntent(Context context) {
		int[] y = getValores(context);
		int[] x = new int[y.length];
		for (int i = 0; i < y.length; i++) {
			x[i] = i;
		}
		XYSeries series = new XYSeries("Registros");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
		}
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setPointStyle(PointStyle.TRIANGLE);
		renderer.setLineWidth(6);
		renderer.setColor(Color.BLACK);
		renderer.setDisplayChartValues(true);
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("Registros de Glicose");
		mRenderer.setXTitle("Registros");
		mRenderer.setYTitle("Valor");
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.parseColor("#F5F5F5"));
		mRenderer.setMarginsColor(Color.parseColor("#F5F5F5"));
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);
		Intent intent = ChartFactory.getScatterChartIntent(context, dataset,
				mRenderer);
		return intent;
	}

	public int[] getValores(Context ctx) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		String sql = getSql(regDao);
		regDao.open();
		Cursor c = regDao.consultar(sql);
		c.moveToFirst();
		Float valor;
		int valores[] = new int[c.getCount()];
		int i = 0;
		while (!c.isAfterLast()) {
			valor = c.getFloat(c.getColumnIndex(c.getColumnName(0)));
			c.moveToNext();
			valores[i] = valor.intValue();
			i++;
		}
		return valores;
	}

	private String getSql(RegistroDAO regDao) {
		@SuppressWarnings("static-access")
		String sql = "SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_TIPO + ", " + regDao.COLUNA_UNIDADE + " FROM "
				+ regDao.TABELA_REGISTRO + " WHERE " + regDao.COLUNA_TIPO
				+ " = '" + Constante.TIPO_GLICOSE + "' " + getAddSQLPermissao()
				+ " ORDER BY " + regDao.COLUNA_DATAHORA + " ASC";
		return sql;
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
}