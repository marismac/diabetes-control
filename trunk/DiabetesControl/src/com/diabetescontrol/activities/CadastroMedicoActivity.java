package com.diabetescontrol.activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.diabetescontrol.activities.R;
import com.diabetescontrol.database.MedicoDAO;
import com.diabetescontrol.model.Medico;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.MedicoWS;

public class CadastroMedicoActivity extends Activity {
	private Button buttonSalvar = null;
	private static Context ctx;
	private EditText editTextNome = null;
	private EditText editTextEmail = null;
	private EditText editTextCodMed = null;
	private EditText editTextRegistro = null;
	private Medico medico = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		medico = new Medico();
		ctx = this;
		setContentView(R.layout.cad_medico);
		setCampos();
		runListeners();
		if (!isFirst()) {
			setCamposMedico();
		}
	}

	private void setCamposMedico() {
		editTextNome.setText(medico.getNome());
		editTextEmail.setText(medico.getEmail());
		editTextCodMed.setText(medico.getCodWS());
		editTextRegistro.setText(medico.getRegistro());
	}

	private boolean isFirst() {
		MedicoDAO medDAO = new MedicoDAO(this);
		medDAO.open();
		@SuppressWarnings("static-access")
		Cursor c = medDAO.consultarMedicosWhereOrder(medDAO.COLUNA_CODWS
				+ " IS NOT NULL", medDAO.COLUNA_NOME + " ASC");
		c.moveToFirst();
		if (c.isFirst()) {
			medico = medDAO.deCursorParaMedico(c);
			medDAO.close();
			return false;
		}
		medDAO.close();
		return true;
	}

	private void salvar() {
		medico = getValoresTela();
		MedicoDAO medDao = new MedicoDAO(this);
		medDao.open();
		if (medico != null) {
			if (medico.getId() == null) {
				medDao.criarMedico(medico);
			} else {
				medDao.atualizarMedico(medico);
			}

			Utils.criaAlertSalvar(ctx, null);
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(ctx)) {
				new MedicoWS(medico, this).sincMedico();
			}
		}
		medDao.close();

	}

	private Medico getValoresTela() {
		if (!isPrenchido(editTextNome)) {
			return null;
		}
		if (!isPrenchido(editTextCodMed)) {
			return null;
		}
		if (!isPrenchido(editTextEmail)) {
			return null;
		}
		medico.setNome(editTextNome.getText().toString());
		medico.setEmail(editTextEmail.getText().toString());
		medico.setRegistro(editTextRegistro.getText().toString());
		medico.setCodWS(editTextCodMed.getText().toString());
		return medico;
	}

	private boolean isPrenchido(EditText edittext) {
		if ("".equals(edittext.getText().toString())) {
			edittext.setError("Campo Obrigatório");
			edittext.setFocusable(true);
			return false;
		}
		return true;
	}

	private void runListeners() {
		buttonSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				salvar();

			}
		});
	}

	private void setCampos() {
		editTextNome = (EditText) findViewById(R.id.etNome);
		editTextEmail = (EditText) findViewById(R.id.etEmail);
		editTextCodMed = (EditText) findViewById(R.id.etCodMedico);
		editTextRegistro = (EditText) findViewById(R.id.etRegMedico);
		buttonSalvar = (Button) findViewById(R.id.btSalvar);
	}

}
