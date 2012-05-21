package com.android.diabetescontrol.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Utils;
import com.android.diabetescontrol.webservice.PacienteWS;
import com.android.diabetescontrol.webservice.RegistroWS;

public class CadastroPacienteActivity extends Activity {
	final Calendar c = Calendar.getInstance();
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerSexo = null;
	private Button buttonSalvar = null;
	private Button buttonData = null;
	private int mYear;
	private int mMonth;
	private int mDay;
	private static Context ctx;
	private EditText editTextNome = null;
	private EditText editTextEmail = null;
	private EditText editTextCodPac = null;
	private EditText editTextSenhaPac = null;
	private Paciente paciente = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		paciente = new Paciente();
		ctx = this;
		setContentView(R.layout.cad_paciente);
		setCampos();
		carregaSpinners();
		runListeners();
		if (!isFirst()) {
			setCamposPaciente();
		}
	}

	private void setCamposPaciente() {
		editTextNome.setText(paciente.getNome());
		editTextEmail.setText(paciente.getEmail());
		editTextCodPac.setText(paciente.getCodPaciente());
		editTextSenhaPac.setText(paciente.getSenhaPaciente());
		buttonData.setText(new StringBuilder()
				.append(paciente.getDatanascimento().getDate()).append("/")
				.append(paciente.getDatanascimento().getMonth() + 1)
				.append("/")
				.append(paciente.getDatanascimento().getYear() + 1900)
				.append(" "));
		spinnerSexo
				.setSelection(paciente.getSexo().equals("Masculino") ? 0 : 1);
	}

	private boolean isFirst() {
		PacienteDAO pacDAO = new PacienteDAO(this);
		pacDAO.open();
		@SuppressWarnings("static-access")
		Cursor c = pacDAO.consultarPacientesWhereOrder(pacDAO.COLUNA_SENHAPAC
				+ " IS NOT NULL", pacDAO.COLUNA_NOME + " ASC");
		c.moveToFirst();
		if (c.isFirst()) {
			paciente = pacDAO.deCursorParaPaciente(c);
			pacDAO.close();
			return false;
		}
		pacDAO.close();
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

	private void isObrigatorio(EditText edittext) {
		if ("".equals(edittext.getText().toString())) {
			edittext.setError("Campo Obrigatório");
			edittext.setFocusable(true);
		}
	}

	private Paciente getValoresTela() {
		isObrigatorio(editTextNome);
		isObrigatorio(editTextCodPac);
		isObrigatorio(editTextSenhaPac);
		paciente.setDatanascimento(new Date(mYear - 1900, mMonth, mDay));
		paciente.setCodPaciente(editTextCodPac.getText().toString());
		paciente.setNome(editTextNome.getText().toString());
		paciente.setEmail(editTextEmail.getText().toString());
		paciente.setSexo(spinnerSexo.getSelectedItem().toString());
		paciente.setSenhaPaciente(editTextSenhaPac.getText().toString());
		return paciente;
	}

	private void salvar() {
		paciente = getValoresTela();
		PacienteDAO pacDao = new PacienteDAO(this);
		pacDao.open();
		if (paciente != null) {
			if (paciente.getId() == null) {
				pacDao.criarPaciente(paciente);
			} else {
				pacDao.atualizarPaciente(paciente);
			}

		}
		pacDao.close();
		Utils.criaAlertSalvar(ctx);
		if (Utils
				.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
				&& Utils.isSelectSynchronize(ctx)) {
			new PacienteWS(paciente, this).sincPaciente();
		}
	}

	private void updateDisplay() {
		buttonData.setText(new StringBuilder().append(mDay).append("/")
				.append(mMonth + 1).append("/").append(mYear).append(" "));
	}

	private void carregaSpinners() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.sexo_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSexo.setAdapter(adapter);

	}

	private void setCampos() {
		spinnerSexo = (Spinner) findViewById(R.id.spSexo);
		buttonSalvar = (Button) findViewById(R.id.btSalvar);
		buttonData = (Button) findViewById(R.id.btData);
		editTextNome = (EditText) findViewById(R.id.etNome);
		editTextEmail = (EditText) findViewById(R.id.etEmail);
		editTextCodPac = (EditText) findViewById(R.id.etCodPaciente);
		editTextSenhaPac = (EditText) findViewById(R.id.etSenha);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();
	}

}
