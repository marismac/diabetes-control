package com.diabetescontrol.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.diabetescontrol.activities.R;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.AddPacienteWS;

public class CadastroMedicoDoPaciente extends Activity {
	private Button buttonAdicionar = null;
	private static Context ctx;
	private EditText editTextCod = null;
	private EditText editTextSenha = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_adicionapacientesdomedico);
		ctx = this;
		setCampos();
		runListeners();

	}

	private void runListeners() {
		buttonAdicionar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				salvar();
			}
		});

	}

	private void setCampos() {
		buttonAdicionar = (Button) findViewById(R.id.btSalvar);
		editTextCod = (EditText) findViewById(R.id.etCodMedicoPac);
		editTextSenha = (EditText) findViewById(R.id.etSenhaMedicoPac);
	}

	private void salvar() {
		if (!Utils.isPrenchido(editTextCod)) {
			return;
		}
		if (!Utils.isPrenchido(editTextSenha)) {
			return;
		}
		if (Utils
				.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
				&& Utils.isSelectSynchronize(ctx)) {
			new AddPacienteWS(new Paciente(null, null, null, null, null,
					editTextCod.getText().toString(), editTextSenha.getText()
							.toString()), this).sincPacienteDoMedico();
		} else {
			Utils.criarAlertaErro(ctx,
					"Para adicionar pacientes é necessário sincronização!");
		}
	}
}