package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectGraficosActivity extends Activity {
	private Button btGraficoCategoria;
	private Button btMaioresTipo;
	private Button btUltimosRegistros;
	private Button btUltimosGeraisMedia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.menu_graficos);
		super.onCreate(savedInstanceState);
		inicializaObjetos();
		carregaListeners();
	}

	private void carregaListeners() {
		btUltimosRegistros.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GraficoUltimosRegistrosGlicose g = new GraficoUltimosRegistrosGlicose();
				Intent liIntent = g.getIntent(SelectGraficosActivity.this);
				startActivity(liIntent);
			}
		});
		btMaioresTipo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GraficoLinha g = new GraficoLinha();
				Intent liIntent = g.getIntent(SelectGraficosActivity.this);
				startActivity(liIntent);
			}
		});
		btGraficoCategoria.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GraficoMediaCategoria g = new GraficoMediaCategoria();
				Intent liIntent = g.getIntent(SelectGraficosActivity.this);
				startActivity(liIntent);
			}
		});
		btUltimosGeraisMedia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GraficoMediaCategoria g = new GraficoMediaCategoria();
				Intent liIntent = g.getIntent(SelectGraficosActivity.this);
				startActivity(liIntent);
			}
		});
		

	}

	private void inicializaObjetos() {
		btGraficoCategoria = (Button) findViewById(R.id.btGraficoMaiores);
		btMaioresTipo = (Button) findViewById(R.id.btGraficoTipo);
		btUltimosRegistros = (Button) findViewById(R.id.btGraficoUltimos);
		btUltimosGeraisMedia = (Button) findViewById(R.id.btUltimasGeralMedias);
	}
}
