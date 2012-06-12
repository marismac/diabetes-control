package com.diabetescontrol.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.diabetescontrol.activities.R;
import com.diabetescontrol.business.GlicoseMediaBusiness;
import com.diabetescontrol.database.RegistroDAO;

public class RelatorioGlicoseMediaActivity extends Activity {
	private TextView tvValorHoje;
	private TextView tvValorOntem;
	private TextView tvValorSemana;
	private TextView tvValorSemanaPass;
	private TextView tvValorMes;
	private TextView tvValorMesPass;
	private TextView tvValorAno;
	private TextView tvValorTotal;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rel_glicosemedia);
		inicializaObjetos();
		carregaValores();
	}

	private void carregaValores() {
		GlicoseMediaBusiness gmb = new GlicoseMediaBusiness();
		RegistroDAO regDAO = new RegistroDAO(this);
		gmb.getValorMedio(regDAO);
		tvValorHoje.setText(gmb.getValorMedioHoje());
		tvValorOntem.setText(gmb.getValorMedioOntem());
		tvValorSemana.setText(gmb.getValorMedioSemana());
		tvValorSemanaPass.setText(gmb.getValorMedioSemanaPassada());
		tvValorMes.setText(gmb.getValorMedioMes());
		tvValorMesPass.setText(gmb.getValorMedioMesPassado());
		tvValorAno.setText(gmb.getValorMedioAnual());
		tvValorTotal.setText(gmb.getValorMedioTotal());
	}

	private void inicializaObjetos() {
		tvValorHoje = (TextView) findViewById(R.id.tvHojeValor);
		tvValorOntem = (TextView) findViewById(R.id.tvOntemValor);
		tvValorSemana = (TextView) findViewById(R.id.tvSemanaValor);
		tvValorSemanaPass = (TextView) findViewById(R.id.tvSemanaPassValor);
		tvValorMes = (TextView) findViewById(R.id.tvMesValor);
		tvValorMesPass = (TextView) findViewById(R.id.tvMesPassValor);
		tvValorAno = (TextView) findViewById(R.id.tvAnoPassValor);
		tvValorTotal = (TextView) findViewById(R.id.tvGeralValor);
	}

}
