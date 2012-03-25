package com.diabetescontrol.principal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DiabetesControlActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btAdicionar;
	private Button btConsultar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		inicializaObjetos();
		carregaListeners();
	}

	private void inicializaObjetos() {
		btAdicionar = (Button) findViewById(R.id.btAdicionar);
		btConsultar = (Button) findViewById(R.id.btConsultar);
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