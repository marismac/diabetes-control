package com.android.diabetescontrol.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;

public class consultaRegistroActivity extends Activity {
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerCategoria = null;
	private Button buttonData = null;
	final Calendar c = Calendar.getInstance();
	private int mYear;
	private int mMonth;
	private int mDay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.consultaregistros);
		setContentView(R.layout.filtrosconsulta);
		setCampos();
		runListeners();
		carregaSpinners();

		// List<Map<String, String>> regs = getRegistrosList();
		//
		// String[] from = { "Master", "Detail" };
		// int[] to = { android.R.id.text1, android.R.id.text2 };
		//
		// SimpleAdapter ad = new SimpleAdapter(this, regs,
		// android.R.layout.simple_list_item_2, from, to);
		// ListView lv = (ListView) findViewById(android.R.id.list);
		// lv.setAdapter(ad);
	}

	private void runListeners() {
		buttonData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	private void setCampos() {
		buttonData = (Button) findViewById(R.id.btData);
		spinnerCategoria = (Spinner) findViewById(R.id.spCategoria);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();
	}

	private void updateDisplay() {
		buttonData.setText(new StringBuilder().append(pad(mDay)).append("/")
				.append(pad(mMonth + 1)).append("/").append(mYear).append(" "));
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
				this, R.array.tipoCat_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategoria.setAdapter(adapter);
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

	// private List<Map<String, String>> getRegistrosList() {
	// List<Map<String, String>> l = new ArrayList<Map<String, String>>();
	// RegistroDAO regDAO = new RegistroDAO(this);
	// Map<String, String> m = null;
	// regDAO.open();
	// Cursor c = regDAO.consultarTodosRegistrosV1();
	// c.moveToFirst();
	// while (!c.isAfterLast()) {
	// m = new HashMap<String, String>();
	// Registro reg = regDAO.deCursorParaRegistro(c);
	// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	// String formattedDate = sdf.format(reg.getDatahora());
	// m.put("Master", reg.getTipo().toString() + ": "
	// + reg.getValor().toString());
	// m.put("Detail", formattedDate + " - "
	// + reg.getCategoria().toString());
	// l.add(m);
	// c.moveToNext();
	// }
	// regDAO.close();
	// return l;
	// }

}
