package com.diabetescontrol.activities;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class GraficoLinha {
	public Intent getIntent(Context context) {
		int[] x = { 1, 2, 3, 4, 5, 6, 7 };
		int[] y = { 30, 34, 45, 57, 77, 100, 20 };
		
		
		TimeSeries series = new TimeSeries("Line1");
		for (int i = 0; i < x.length; i++) {
			series.add(x[i], y[i]);
			
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("TESTES");

		Intent intent = ChartFactory.getLineChartIntent(context, dataset,
				mRenderer, "Line");
		return intent;
	}
}
