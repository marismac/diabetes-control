package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GraficosPacienteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GraficoLinha gl = new GraficoLinha();
		Intent liIntent = gl.getIntent(this);
		startActivity(liIntent);
	}

}
