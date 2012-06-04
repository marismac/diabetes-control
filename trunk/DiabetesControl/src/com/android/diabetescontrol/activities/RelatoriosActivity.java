package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RelatoriosActivity extends Activity {
	private Button btRegistroDia;
	private Button btGlicoseUltimosRegs;
	private Button btGlicoseMedias;
	private Button btGlicoseValor;
	private Button btPesoMedio;
	private Button btInsulinaAplica;
	private Button btGeralTipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultas);
		inicializaObjetos();
		carregaListeners();
	}

	private void carregaListeners() {
		btRegistroDia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(RelatoriosActivity.this,
						consultaRegistroDiaActivity.class);
				startActivity(i);
			}
		});
		btGlicoseUltimosRegs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(RelatoriosActivity.this,
						ListaGlicoseUltimosRegistrosActivity.class);
				startActivity(i);
			}
		});
		btGlicoseMedias.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(RelatoriosActivity.this,
						consultaGlicoseMediasActivity.class);
				startActivity(i);
			}
		});
		btGlicoseValor.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(RelatoriosActivity.this,
						ConsultaGlicoseValoresActivity.class);
				startActivity(i);
			}
		});
		btPesoMedio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		btInsulinaAplica.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		btGeralTipo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});

	}

	private void inicializaObjetos() {
		btRegistroDia = (Button) findViewById(R.id.btRegdia);
		btGlicoseUltimosRegs = (Button) findViewById(R.id.btGlicoultregs);
		btGlicoseMedias = (Button) findViewById(R.id.btGlicomedias);
		btGlicoseValor = (Button) findViewById(R.id.btGlicovalor);
		btPesoMedio = (Button) findViewById(R.id.btPesomed);
		btInsulinaAplica = (Button) findViewById(R.id.btInsulaplic);
		btGeralTipo = (Button) findViewById(R.id.btGeraltipo);
	}
}