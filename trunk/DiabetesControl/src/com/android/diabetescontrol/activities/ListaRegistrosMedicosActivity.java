package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.android.diabetescontrol.adapters.RegistroAdapter;
import com.android.diabetescontrol.business.RegistrosMedicosBusiness;
import com.android.diabetescontrol.webservice.RegistroWS;

public class ListaRegistrosMedicosActivity extends ListActivity {
	String codPaciente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent().getExtras().get("codPaciente") != null
				&& !"".equals(getIntent().getExtras().get("codPaciente")
						.toString())) {
			codPaciente = (getIntent().getExtras().get("codPaciente")
					.toString());
			new RegistroWS(this).sincRegistrosMedico(codPaciente);
			setListAdapter(new RegistroAdapter(this,
					new RegistrosMedicosBusiness().getRegistrosPaciente(this,
							codPaciente)));
		}
		super.onCreate(savedInstanceState);
	}
}