package com.diabetescontrol.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.diabetescontrol.activities.R;
import com.diabetescontrol.database.PacienteDAO;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.CadPacienteWS;

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
		if (Paciente.getCODIGOPACIENTE() != null) {
			editTextCodPac.setEnabled(false);
		}
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
		buttonData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	private boolean isPrenchido(EditText edittext) {
		if ("".equals(edittext.getText().toString())) {
			edittext.setError("Campo Obrigatório");
			edittext.setFocusable(true);
			edittext.setBackgroundColor(Color.BLACK);
			return false;
		}
		return true;
	}

	private Paciente getValoresTela() {
		if (!isPrenchido(editTextNome)) {
			return null;
		}
		if (!isPrenchido(editTextCodPac)) {
			return null;
		}
		if (!isPrenchido(editTextSenhaPac)) {
			return null;
		}
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
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(ctx)) {
				new CadPacienteWS(paciente, this).sincPaciente();
			} else {
				Utils.criarAlertaErro(ctx,
						"Para realizar esse cadastro é necessária sincronização!");
			}
		}
		pacDao.close();
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

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

}
