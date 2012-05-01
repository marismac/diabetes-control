package com.android.diabetescontrol.activities;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class graficosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graficos);
		addGraficoGeralUltimos();
		// init example series data
//		GraphViewSeries exampleSeries = new GraphViewSeries(
//				new GraphViewData[] {
//						new GraphViewData(1, 2.0d),
//						new GraphViewData(2, 1.5d),
//						new GraphViewData(2.5, 3.0d),
//						new GraphViewData(3, 2.5d),
//						new GraphViewData(4, 1.0d), 
//						new GraphViewData(5, 3.0d) });
//		GraphView graphView;
//		
//		graphView = new LineGraphView(this, "Gráficos dos Registros");
//		((LineGraphView) graphView).setDrawBackground(true);
//		graphView.setHorizontalLabels(new String[] { "2 days ago", "yesterday",
//				"today", "tomorrow" });
//		graphView.setVerticalLabels(new String[] { "high", "middle", "low" });
//		graphView.addSeries(exampleSeries);
//		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
//		layout.addView(graphView);
	}

	private void addGraficoGeralUltimos() {
		RegistroDAO regDAO = new RegistroDAO(this);
		regDAO.open();
		Cursor c = regDAO.consultarTodosRegistrosOrdenados("DATAHORA ASC");
		Integer qtdRegistros = regDAO.consultarQuantosRegistro();
		--qtdRegistros;
		c.moveToFirst();
		GraphViewData[] data = new GraphViewData[qtdRegistros];
		String[] horizontalLabels = new String [qtdRegistros];
		for (int i=0; i < qtdRegistros; i++) {
			Registro reg = regDAO.deCursorParaRegistro(c);
			data[i] = new GraphViewData(i, reg.getValor());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String formattedDate = sdf.format(reg.getDatahora());
			horizontalLabels[i] = new String (formattedDate);
			System.out.println(i + " - " + reg.getValor());
			System.out.println(reg.getDatahora());
			c.moveToNext();
		}
		regDAO.close();
		GraphView graphView;
		
		GraphViewSeries exampleSeries = new GraphViewSeries("Gráficos Registros", Color.rgb(200, 50, 00), data);
		
		graphView = new BarGraphView(this, "Gráficos dos Registros");
		//((BarGraphView) graphView).setDrawBackground(true);
		graphView.setHorizontalLabels(horizontalLabels);
		graphView.setVerticalLabels(new String[] { "high", "middle", "low" });
		graphView.addSeries(exampleSeries);
		graphView.setScrollable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
		layout.addView(graphView);
	}

}
