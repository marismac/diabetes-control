package com.android.diabetescontrol.principal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.diabetescontrol.activities.R;
import com.android.diabetescontrol.activities.consultaRegistroActivity;
import com.android.diabetescontrol.activities.cadastroRegistroActivity;
import com.android.diabetescontrol.database.ContextoDados;

public class principalActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btAdicionar;
	private Button btConsultar;

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
						consultaRegistroActivity.class);
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
		// A activity foi destru�da.
	}

	@Override
	protected void onResume() {
		super.onResume();
		// A activity tornou-se vis�vel.
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Outra activity tem o foco na aplica��o.
	}

	@Override
	protected void onStop() {
		super.onStop();
		// A activity n�o � mais vis�vel.
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// A activity foi destru�da.
	}
}