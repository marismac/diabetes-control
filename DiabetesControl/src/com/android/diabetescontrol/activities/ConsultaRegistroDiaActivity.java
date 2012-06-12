package com.android.diabetescontrol.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class ConsultaRegistroDiaActivity extends Activity {
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerTipo = null;
	private Button buttonData = null;
	private Button buttonConsultar = null;
	final Calendar c = Calendar.getInstance();
	private int mYear;
	private int mMonth;
	private int mDay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filtro_consultadia);
		setCampos();
		runListeners();
		carregaSpinners();
	}

	private void runListeners() {
		buttonData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		buttonConsultar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ConsultaRegistroDiaActivity.this,
						ListaRegistroActivity.class);
				i.putExtra("dataFiltro", buttonData.getText());
				i.putExtra("tipoCategoria", spinnerTipo.getSelectedItem()
						.toString());
				startActivity(i);
			}
		});
	}

	private void setCampos() {
		buttonData = (Button) findViewById(R.id.btData);
		buttonConsultar = (Button) findViewById(R.id.btConsultarLista);
		spinnerTipo = (Spinner) findViewById(R.id.spTipo);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();
	}

	private void updateDisplay() {
		buttonData.setText(new StringBuilder().append(pad(mDay)).append("/")
				.append(pad(mMonth + 1)).append("/").append(mYear));
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
		}

		return null;
	}

	private void carregaSpinners() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.tipoReg_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapter);
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
