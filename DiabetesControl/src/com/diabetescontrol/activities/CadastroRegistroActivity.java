package com.diabetescontrol.activities;

import java.sql.Timestamp;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.diabetescontrol.database.MedicamentoDAO;
import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Medicamento;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.CadRegistroWS;

public class CadastroRegistroActivity extends Activity {

	static final int TIME_DIALOG_ID = 1;
	static final int DATE_DIALOG_ID = 0;
	private Spinner spinnerTipo = null;
	private Spinner spinnerCategoria = null;
	private Spinner spinnerMedicamento = null;
	private TableRow trMed = null;
	private TableRow trMedSp = null;
	private Button buttonSalvar = null;
	private Button buttonData = null;
	private Button buttonHora = null;
	private TextView unidade = null;
	private EditText editTextValor = null;
	private EditText editTextValorPressao = null;
	final Calendar c = Calendar.getInstance();
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private static Context ctx;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_registro);
		ctx = this;
		setCampos();
		carregaSpinners();
		runListeners();
		if (isEditar()) {
			setValoresTela();
		}
	}

	private Boolean isEditar() {
		if (getIntent().getExtras() != null
				&& getIntent().getExtras().get("editar") != null
				&& (Boolean) getIntent().getExtras().get("editar")) {
			return true;
		}
		return false;
	}

	private void setValoresTela() {
		String data = "";
		String hora = "";
		data = getIntent().getExtras().get("dataHoraRegistro").toString()
				.substring(0, 10);
		hora = getIntent().getExtras().get("dataHoraRegistro").toString()
				.substring(11, 16);
		buttonData.setText(data);
		buttonHora.setText(hora);
		spinnerTipo.setSelection(selecionaSpinnerTipo(getIntent().getExtras()
				.get("tipoRegistro").toString()));
		spinnerCategoria.setSelection(selecionaSpinnerCategoria(getIntent()
				.getExtras().get("categoriaRegistro").toString()));
		if (getIntent().getExtras().get("medicamentoRegistro") != null) {
			spinnerMedicamento
					.setSelection(selecionaSpinnerMedicamento(getIntent()
							.getExtras().get("medicamentoRegistro").toString()));
		}
		if (getIntent().getExtras().get("valorRegistro") != null) {
			editTextValor.setText(getIntent().getExtras().get("valorRegistro")
					.toString());
		}
		if (getIntent().getExtras().get("valorPressaoRegistro") != null) {
			editTextValorPressao.setText(getIntent().getExtras()
					.get("valorPressaoRegistro").toString());
		}
		unidade.setText(getIntent().getExtras().get("unidadeRegistro")
				.toString());

	}

	private Integer selecionaSpinnerTipo(String text) {
		if ("Glicose".equals(text)) {
			return 0;
		} else if ("Medicamento".equals(text)) {
			return 1;
		} else if ("Peso".equals(text)) {
			return 2;
		} else if ("Press�o".equals(text)) {
			return 3;
		} else if ("Pulso".equals(text)) {
			return 4;
		} else if ("Gordura".equals(text)) {
			return 5;
		} else if ("HbA1c".equals(text)) {
			return 6;
		} else {
			return 7;
		}
	}

	private Integer selecionaSpinnerCategoria(String text) {
		if ("Antes do Caf�".equals(text)) {
			return 0;
		} else if ("Depois do Caf�".equals(text)) {
			return 1;
		} else if ("Antes do Almo�o".equals(text)) {
			return 2;
		} else if ("Depois da Almo�o".equals(text)) {
			return 3;
		} else if ("Antes do Lanche".equals(text)) {
			return 4;
		} else if ("Depois do Lanche".equals(text)) {
			return 5;
		} else if ("Antes do Almo�o".equals(text)) {
			return 6;
		} else if ("Depois do Lanche".equals(text)) {
			return 7;
		}
		return 8;
	}

	private Integer selecionaSpinnerMedicamento(String text) {
		for (Medicamento med : Medicamento.LIST_MEDICAMENTOS) {
			if (med.getTipo().equals(text)) {
				return med.getId();
			}
		}
		return 0;
	}

	private void setCampos() {
		spinnerTipo = (Spinner) findViewById(R.id.spTipo);
		spinnerCategoria = (Spinner) findViewById(R.id.spCategoria);
		spinnerMedicamento = (Spinner) findViewById(R.id.spMedicamentos);
		trMed = (TableRow) findViewById(R.id.trMedicamentosTv);
		trMedSp = (TableRow) findViewById(R.id.trMedicamentos);
		buttonSalvar = (Button) findViewById(R.id.btSalvar);
		buttonData = (Button) findViewById(R.id.btData);
		buttonHora = (Button) findViewById(R.id.btHora);
		editTextValor = (EditText) findViewById(R.id.etValor);
		unidade = (TextView) findViewById(R.id.tvUnidade);
		editTextValorPressao = (EditText) findViewById(R.id.etValor);

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateDisplay();
	}

	private void carregaSpinners() {

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.tipoReg_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapter);

		adapter = ArrayAdapter.createFromResource(this, R.array.tipoCat_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategoria.setAdapter(adapter);

		MedicamentoDAO medDao = new MedicamentoDAO(this);
		medDao.open();
		Cursor c = medDao.consultarMedicamentos();
		c.moveToFirst();
		String[] from = new String[] { "TIPO" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMedicamento.setAdapter(adapter2);
		medDao.close();
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

		spinnerTipo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int valor = (int) spinnerTipo.getSelectedItemId();
				switch (valor) {
				case 0:
					selectTipoGlicose();
					break;
				case 1:
					selectTipoMedicamento();
					break;
				case 2:
					selectTipoPeso();
					break;
				case 3:
					selectTipoPressao();
					break;
				case 4:
					selectTipoPulso();
					break;
				case 5:
					selectTipoGordura();
					break;
				case 6:
					selectTipoHbA1c();
					break;
				default:
					selectTipoGlicose();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}

		});

	}

	private void selectTipoMedicamento() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_medicamento", "3mL"));
		trMed.setVisibility(View.VISIBLE);
		trMedSp.setVisibility(View.VISIBLE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void selectTipoGlicose() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_glicose", "mmol/l"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void selectTipoPeso() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_peso", "kg"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void selectTipoPressao() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_pressao", "mmHg"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint("15 - 20");
		editTextValor.setInputType(InputType.TYPE_CLASS_TEXT);
	}

	private void selectTipoPulso() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_pulso", "bpm"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void selectTipoHbA1c() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_hba1c", "%"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void selectTipoGordura() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		unidade.setText(prefs.getString("unidade_gordura", "qtde"));
		trMed.setVisibility(View.GONE);
		trMedSp.setVisibility(View.GONE);
		editTextValor.setHint(null);
		editTextValor.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void Salvar() {
		Registro reg = getValoresTela();
		if (reg != null) {
			RegistroDAO regDao = new RegistroDAO(this);
			regDao.open();
			if (isEditar()) {
				Integer codReg = (Integer) getIntent().getExtras().get(
						"idRegistro");
				reg.setId(codReg);
				regDao.atualizaRegistro(reg);
			} else {
				reg.setCodPaciente(Paciente.getCODIGOPACIENTE());
				reg.setId(regDao.criarRegistro(reg));
			}
			regDao.close();
			Utils.criaAlertSalvar(ctx, null);
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(ctx)
					&& Paciente.getCODIGOPACIENTE() != null) {
				new CadRegistroWS(this).sincRegistro(reg);
			}
		}
	}

	private Registro getValoresTela() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		if ("".equals(editTextValor.getText().toString())
				&& !"Press�o".equals(spinnerTipo.getSelectedItem().toString())) {
			editTextValor.setFocusable(true);
			editTextValor.setError("Obrigat�rio");
			editTextValor.setBackgroundColor(Color.RED);
			return null;
		} else if ("".equals(editTextValorPressao.getText().toString())
				&& "Press�o".equals(spinnerTipo.getSelectedItem().toString())) {
			editTextValorPressao.setFocusable(true);
			editTextValorPressao.setError("Obrigat�rio");
			return null;
		}
		Registro reg = new Registro();
		Timestamp timestamp = new Timestamp(mYear - 1900, mMonth, mDay, mHour,
				mMinute, 0, 0);
		reg.setDataHora(timestamp);
		if ("Medicamento".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setMedicamento(spinnerMedicamento.getSelectedItemPosition() + 1);
			reg.setUnidade(prefs.getString("unidade_medicamento", "qtde"));
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
		} else if ("Press�o".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setValorPressao(editTextValorPressao.getText().toString());
			reg.setUnidade(prefs.getString("unidade_pressao", "qtde"));
		} else if ("Glicose".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
			reg.setUnidade(prefs.getString("unidade_glicose", "qtde"));
		} else if ("Peso".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setUnidade(prefs.getString("unidade_peso", "qtde"));
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
		} else if ("Gordura".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setUnidade(prefs.getString("unidade_gordura", "qtde"));
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
		} else if ("Pulso".equals(spinnerTipo.getSelectedItem().toString())) {
			reg.setUnidade(prefs.getString("unidade_pulso", "qtde"));
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
		} else {
			reg.setValor(Float.valueOf(editTextValor.getText().toString()));
			reg.setUnidade(prefs.getString("unidade_nova", "qtde"));
		}
		reg.setCategoria(spinnerCategoria.getSelectedItem().toString());
		reg.setTipo(spinnerTipo.getSelectedItem().toString());
		reg.setSincronizado("N");
		reg.setModoUser("P");
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