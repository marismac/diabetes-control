package com.diabetescontrol.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.diabetescontrol.database.ContextoDados;

public class DiabetesControlActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btAdicionar;
	private Button btConsultar;
	private Button btConsultarDB;

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
		btConsultarDB = (Button) findViewById(R.id.btConsultarDB);
	}

	private void carregaListeners() {
		btAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(DiabetesControlActivity.this,
						RegistroActivity.class);
				startActivity(i);
			}
		});
		btConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(DiabetesControlActivity.this,
						ConsultaRegistroActivity.class);
				startActivity(i);
			}
		});
		btConsultarDB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				carregar();
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