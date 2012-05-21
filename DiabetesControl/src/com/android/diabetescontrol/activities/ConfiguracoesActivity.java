package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.diabetescontrol.util.Utils;

public class ConfiguracoesActivity extends Activity {
	private Button btCadastrarPaciente;
	private Button btCadastrarMedico;
	private Button btPreferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_configuracoes);
		inicializaObjetos();
		carregaListeners();
		if (Utils.isPaciente(this)) {
			btCadastrarMedico.setVisibility(View.INVISIBLE);
		} else {
			btCadastrarPaciente.setVisibility(View.INVISIBLE);
		}
	}

	private void carregaListeners() {
		btCadastrarPaciente.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ConfiguracoesActivity.this,
						CadastroPacienteActivity.class);
				startActivity(i);
			}
		});
		btCadastrarMedico.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ConfiguracoesActivity.this,
						CadastroMedicoActivity.class);
				startActivity(i);
			}
		});
		btPreferencias.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(ConfiguracoesActivity.this,
						PreferenciasActivity.class);
				startActivity(i);
			}
		});
	}

	private void inicializaObjetos() {
		btCadastrarPaciente = (Button) findViewById(R.id.btPaciente);
		btCadastrarMedico = (Button) findViewById(R.id.btMedico);
		btPreferencias = (Button) findViewById(R.id.btConfigAvanc);
	}
}
