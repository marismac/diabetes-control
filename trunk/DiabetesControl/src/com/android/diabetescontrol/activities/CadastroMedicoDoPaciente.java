package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Utils;
import com.android.diabetescontrol.webservice.PacienteWS;

public class CadastroMedicoDoPaciente extends Activity {
	private Button buttonAdicionar = null;
	private static Context ctx;
	private EditText editTextCod = null;
	private EditText editTextSenha = null;

	// private Paciente paciente = null;

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

	private void getValoresTela() {
		if (!Utils.isPrenchido(editTextCod)) {
			return;
		}
		if (!Utils.isPrenchido(editTextSenha)) {
			return;
		}
	}

	private void salvar() {
		getValoresTela();
		if (Utils
				.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
				&& Utils.isSelectSynchronize(ctx)) {
			Paciente paciente = new PacienteWS(new Paciente(null, null, null,
					null, null, editTextCod.getText().toString(), editTextSenha
							.getText().toString()), this)
					.sincPacienteDoMedico();
			if (paciente == null) {
				Utils.criarAlertaErro(ctx,
						"Código e Senha do Paciente inexistentes!");
			} else {
				PacienteDAO pacDao = new PacienteDAO(ctx);
				pacDao.open();
				pacDao.criarPaciente(paciente);
				pacDao.close();
				Utils.criaAlertSalvar(ctx);
			}
		} else {
			Utils.criarAlertaErro(ctx,
					"É necessário conexão disponível para adicionar o paciente!");
		}
	}
}