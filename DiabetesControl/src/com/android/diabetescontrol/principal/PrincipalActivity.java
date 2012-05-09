package com.android.diabetescontrol.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.diabetescontrol.activities.R;
import com.android.diabetescontrol.activities.cadastroRegistroActivity;
import com.android.diabetescontrol.activities.consultaActivity;
import com.android.diabetescontrol.activities.graficosActivity;
import com.android.diabetescontrol.activities.testeWSActivity;
import com.android.diabetescontrol.business.GlicoseMediaBusiness;
import com.android.diabetescontrol.database.ContextoDados;
import com.android.diabetescontrol.database.RegistroDAO;

public class PrincipalActivity extends Activity {
	private Button btAdicionar;
	private Button btConsultar;
	private Button btGraficos;
	private Button btTesteWS;
	private TextView tvValorHoje;
	private TextView tvValorOntem;
	private TextView tvValorSemana;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new ContextoDados(this);
		setContentView(R.layout.main);
		inicializaObjetos();
		carregaListeners();
	}

	private void inicializaObjetos() {
		btAdicionar = (Button) findViewById(R.id.btAdicionar);
		btConsultar = (Button) findViewById(R.id.btConsultar);
		btTesteWS = (Button) findViewById(R.id.btTesteWS);
		btGraficos = (Button) findViewById(R.id.btGraficos);
		tvValorHoje = (TextView) findViewById(R.id.etvUltimoReg);
		tvValorOntem = (TextView) findViewById(R.id.etvOntem);
		tvValorSemana = (TextView) findViewById(R.id.etvSemana);
	}

	private void carregaListeners() {
		btAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						cadastroRegistroActivity.class);
				startActivity(i);
			}
		});
		btConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						consultaActivity.class);
				startActivity(i);
			}
		});
		btGraficos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						graficosActivity.class);
				startActivity(i);
			}
		});
		btTesteWS.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(PrincipalActivity.this,
						testeWSActivity.class);
				startActivity(i);
			}
		});
	}

	public void carregar() {

	}

	public void ImprimirLinha(String nome, String telefone) {
		TextView tv = (TextView) findViewById(R.id.listaRegistros);

		if (tv.getText().toString()
				.equalsIgnoreCase("Nenhum contato cadastrado."))
			tv.setText("");

		tv.setText(tv.getText() + "\r\n" + nome + " - " + telefone);
	}

	
	@Override
	protected void onStart() {
		super.onStart();
		carregaResumo();
	}

	private void carregaResumo() {
		GlicoseMediaBusiness gmb = new GlicoseMediaBusiness();
		gmb.getValorMedio(new RegistroDAO(this));
		tvValorHoje.setText(gmb.getValorMedioHoje());
		tvValorOntem.setText(gmb.getValorMedioOntem());
		tvValorSemana.setText(gmb.getValorMedioSemana());		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// A activity tornou-se visível.
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Outra activity tem o foco na aplicação.
	}

	@Override
	protected void onStop() {
		super.onStop();
		// A activity não é mais visível.
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// A activity foi destruída.
	}
}