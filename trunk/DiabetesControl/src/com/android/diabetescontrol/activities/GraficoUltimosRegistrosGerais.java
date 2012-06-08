package com.android.diabetescontrol.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.util.Constante;

public class GraficoUltimosRegistrosGerais {
	public Intent getIntent(Context context) {
		int[] x = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y = getValores(context, Constante.TIPO_GLICOSE);

		TimeSeries series = new TimeSeries("Glicose");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
		}

		int[] x1 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y1 = getValores(context, Constante.TIPO_GORDURA);

		TimeSeries series1 = new TimeSeries("Gordura");
		for (int i = 0; i < x1.length; i++) {
			series1.add(x1[i], y1[i]);
		}

		int[] x2 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y2 = getValores(context, Constante.TIPO_HBA1C);

		TimeSeries series2 = new TimeSeries("HbA1c");
		for (int i = 0; i < x2.length; i++) {
			series2.add(x2[i], y2[i]);
		}

		int[] x3 = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y3 = getValores(context, Constante.TIPO_PESO);

		TimeSeries series3 = new TimeSeries("Peso");
		for (int i = 0; i < x3.length; i++) {
			series3.add(x3[i], y3[i]);
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float) 3.0);
		renderer.setPointStyle(PointStyle.DIAMOND);
		renderer.setFillPoints(true);
		renderer.setColor(Color.BLUE);

		XYSeriesRenderer renderer1 = new XYSeriesRenderer();
		renderer1.setDisplayChartValues(true);
		renderer1.setChartValuesSpacing((float) 3.0);
		renderer1.setPointStyle(PointStyle.CIRCLE);
		renderer1.setFillPoints(true);
		renderer1.setColor(Color.DKGRAY);

		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setDisplayChartValues(true);
		renderer2.setChartValuesSpacing((float) 3.0);
		renderer2.setPointStyle(PointStyle.SQUARE);
		renderer2.setFillPoints(true);
		renderer2.setColor(Color.RED);

		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setDisplayChartValues(true);
		renderer3.setChartValuesSpacing((float) 3.0);
		renderer3.setPointStyle(PointStyle.TRIANGLE);
		renderer3.setFillPoints(true);
		renderer3.setColor(Color.GREEN);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer1);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.addSeriesRenderer(renderer3);

		mRenderer.setChartTitle("Média por Categoria Ultimos 7 dias");
		mRenderer.setXTitle("Dias");
		mRenderer.setYTitle("Valor");
		mRenderer.setBarSpacing((float) 0.5);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.parseColor("#F5F5F5"));
		mRenderer.setMarginsColor(Color.parseColor("#F5F5F5"));
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setXLabelsColor(Color.BLACK);

		Intent intent = ChartFactory.getLineChartIntent(context, dataset,
				mRenderer);
		return intent;
	}

	public int[] getValores(Context ctx, String tipo) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		String sql = getSql(regDao, tipo);
		regDao.open();
		Cursor c = regDao.consultar(sql);
		c.moveToFirst();
		Float valor;
		Date dataHojeDate = new Date();
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_1 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_2 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_3 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_4 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_5 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		dataHojeDate.setDate(dataHojeDate.getDate() - 1);
		String dataHoje_6 = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		int contHoje = 0;
		Float valorHoje = 0f;
		int contHoje1 = 0;
		Float valorHoje1 = 0f;
		int contHoje2 = 0;
		Float valorHoje2 = 0f;
		int contHoje3 = 0;
		Float valorHoje3 = 0f;
		int contHoje4 = 0;
		Float valorHoje4 = 0f;
		int contHoje5 = 0;
		Float valorHoje5 = 0f;
		int contHoje6 = 0;
		Float valorHoje6 = 0f;

		int valores[] = { 0, 0, 0, 0, 0, 0, 0 };
		while (!c.isAfterLast()) {
			valor = c.getFloat(c.getColumnIndex(c.getColumnName(0)));
			Date data = new Date(
					c.getLong(c.getColumnIndex(c.getColumnName(1))));
			String dataRegistro = new SimpleDateFormat("dd/MM/yyyy")
					.format(data);

			if (dataHoje.equals(dataRegistro)) {
				++contHoje;
				valorHoje += valor;
				valores[0] = (int) (valorHoje / contHoje);
			} else if (dataHoje_1.equals(dataRegistro)) {
				++contHoje1;
				valorHoje1 += valor;
				valores[1] = (int) (valorHoje1 / contHoje1);
			} else if (dataHoje_2.equals(dataRegistro)) {
				++contHoje2;
				valorHoje2 += valor;
				valores[2] = (int) (valorHoje2 / contHoje2);
			} else if (dataHoje_3.equals(dataRegistro)) {
				++contHoje3;
				valorHoje3 += valor;
				valores[3] = (int) (valorHoje3 / contHoje3);
			} else if (dataHoje_4.equals(dataRegistro)) {
				++contHoje4;
				valorHoje4 += valor;
				valores[4] = (int) (valorHoje4 / contHoje4);
			} else if (dataHoje_5.equals(dataRegistro)) {
				++contHoje5;
				valorHoje5 += valor;
				valores[5] = (int) (valorHoje5 / contHoje5);
			} else if (dataHoje_6.equals(dataRegistro)) {
				++contHoje6;
				valorHoje6 += valor;
				valores[6] = (int) (valorHoje6 / contHoje6);
			}

			c.moveToNext();

		}
		return valores;
	}

	private String getSql(RegistroDAO regDao, String tipo) {
		@SuppressWarnings("static-access")
		String sql = "SELECT " + regDao.COLUNA_VALOR + ", "
				+ regDao.COLUNA_DATAHORA + " FROM " + regDao.TABELA_REGISTRO
				+ " WHERE " + regDao.COLUNA_TIPO + " = '" + tipo
				+ "' ORDER BY " + regDao.COLUNA_DATAHORA + " ASC LIMIT 0, 100";
		return sql;
	}
}