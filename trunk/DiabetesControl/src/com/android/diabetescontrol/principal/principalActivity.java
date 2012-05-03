package com.android.diabetescontrol.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.diabetescontrol.activities.R;
import com.android.diabetescontrol.activities.consultaActivity;
import com.android.diabetescontrol.activities.consultaRegistroDiaActivity;
import com.android.diabetescontrol.activities.cadastroRegistroActivity;
import com.android.diabetescontrol.activities.graficosActivity;
import com.android.diabetescontrol.activities.testeWSActivity;
import com.android.diabetescontrol.database.ContextoDados;

public class principalActivity extends Activity {
	private Button btAdicionar;
	private Button btConsultar;
	private Button btGraficos;
	private Button btTesteWS;

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
	}

	private void carregaListeners() {
		btAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(principalActivity.this,
						cadastroRegistroActivity.class);
				startActivity(i);
			}
		});
		btConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(principalActivity.this,
						consultaActivity.class);
				startActivity(i);
			}
		});
		btGraficos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(principalActivity.this,
						graficosActivity.class);
				startActivity(i);
			}
		});
		btTesteWS.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(principalActivity.this,
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
		super.onDestroy();
		// A activity foi destruída.
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