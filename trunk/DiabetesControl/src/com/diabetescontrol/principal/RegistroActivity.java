package com.diabetescontrol.principal;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class RegistroActivity extends Activity {

	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerTipo = null;
	private Spinner spinnerCategoria = null;
	private Button buttonSalvar = null;
	private Button buttonLimpar = null;
	private Button buttonData = null;
	private Button buttonHora = null;
	private EditText editTextValor = null;
	final Calendar c = Calendar.getInstance();
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		setValorCampos();
		carregaSpinners();
		runListeners();
	}

	private void setValorCampos() {
		spinnerTipo = (Spinner) findViewById(R.id.spTipo);
		spinnerCategoria = (Spinner) findViewById(R.id.spCategoria);
		buttonSalvar = (Button) findViewById(R.id.btSalvar);
		buttonLimpar = (Button) findViewById(R.id.btLimpar);
		buttonData = (Button) findViewById(R.id.btData);
		buttonHora = (Button) findViewById(R.id.btHora);
		editTextValor = (EditText) findViewById(R.id.etValor);

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		// display the current date (this method is below)
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
	}

	private void updateDisplay() {
		buttonData.setText(new StringBuilder().append(mMonth + 1).append("/")
				.append(mDay).append("/").append(mYear).append(" "));
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
