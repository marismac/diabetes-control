package com.android.diabetescontrol.activities;

import java.sql.Timestamp;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;
import com.android.diabetescontrol.webservice.RegistroWS;

public class CadastroRegistroActivity extends Activity {

	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerTipo = null;
	private Spinner spinnerCategoria = null;
	private Button buttonSalvar = null;
	private Button buttonData = null;
	private Button buttonHora = null;
	private EditText editTextValor = null;
	final Calendar c = Calendar.getInstance();
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private static Context ctx;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		ctx = this;
		setCampos();
		carregaSpinners();
		runListeners();
	}

	private void setCampos() {
		spinnerTipo = (Spinner) findViewById(R.id.spTipo);
		spinnerCategoria = (Spinner) findViewById(R.id.spCategoria);
		buttonSalvar = (Button) findViewById(R.id.btSalvar);
		buttonData = (Button) findViewById(R.id.btData);
		buttonHora = (Button) findViewById(R.id.btHora);
		editTextValor = (EditText) findViewById(R.id.etValor);

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateDisplay();
	}

	private void carregaSpinners() {
		// Preenche o Spinner de Tipo com o valor do strings.xml
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.tipoReg_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapter);
		// Preenche o Spinner de Categoria com o valor do strings.xml
		adapter = ArrayAdapter.createFromResource(this, R.array.tipoCat_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategoria.setAdapter(adapter);
	}

	private void runListeners() {
		buttonData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		buttonHora.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		buttonSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Salvar();

			}
		});
	}

	private void Salvar() {
		Registro reg = getValoresTela();
		RegistroDAO regDao = new RegistroDAO(this);
		regDao.open();
		if (reg != null) {
			regDao.criarRegistro(reg);
		}
		regDao.close();
		Utils.criaAlertSalvar(ctx, null);
		if (Utils
				.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))) {
			//new RegistroWS(reg, this).sincRegistro();
		}
	}

	private Registro getValoresTela() {
		if ("".equals(editTextValor.getText().toString())) {
			editTextValor.setError("Campo Obrigatório");
			editTextValor.setFocusable(true);
			return null;
		}
		Registro reg = new Registro();
		Timestamp timestamp = new Timestamp(mYear - 1900, mMonth, mDay, mHour,
				mMinute, 0, 0);
		reg.setDataHora(timestamp);
		reg.setCategoria(spinnerCategoria.getSelectedItem().toString());
		reg.setTipo(spinnerTipo.getSelectedItem().toString());
		reg.setValor(Float.valueOf(editTextValor.getText().toString()));
		// System.out.println("******** INICIO Registros Inseridos ******");
		// System.out.println(reg.getTipo());
		// System.out.println(reg.getCategoria());
		// System.out.println(reg.getValor());
		// System.out.println(reg.getDatahora());
		// System.out.println("******** FIM Registros Inseridos ******");
		return reg;
	}

	private void updateDisplay() {
		buttonData.setText(new StringBuilder().append(mDay).append("/")
				.append(mMonth + 1).append("/").append(mYear).append(" "));
		buttonHora.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					true);
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
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay();
		}
	};
}