package com.diabetescontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConsultaGlicoseValoresActivity extends Activity {
	private EditText editTextValorMin = null;
	private EditText editTextValorMax = null;
	private Button buttonConsultar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filtros_consultaglicosvalor);
		setCampos();
		runListeners();
	}

	private void runListeners() {
		buttonConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (editTextValorMin.getText() == null
						|| "".equals(editTextValorMin.getText().toString())) {
					editTextValorMin.setFocusable(true);
					editTextValorMin.setError("Obrigatório");
				} else if (editTextValorMax.getText() == null
						|| "".equals(editTextValorMax.getText().toString())) {
					editTextValorMax.setFocusable(true);
					editTextValorMax.setError("Obrigatório");
				} else {
					Intent i = new Intent(ConsultaGlicoseValoresActivity.this,
							ListaGlicoseValoresActivity.class);
					i.putExtra("valorMin", editTextValorMin.getText());
					i.putExtra("valorMax", editTextValorMax.getText());
					startActivity(i);
				}
			}
		});
	}

	private void setCampos() {
		editTextValorMin = (EditText) findViewById(R.id.etValorMin);
		editTextValorMax = (EditText) findViewById(R.id.etValorMax);
		buttonConsultar = (Button) findViewById(R.id.btConsultarLista);
	}
}