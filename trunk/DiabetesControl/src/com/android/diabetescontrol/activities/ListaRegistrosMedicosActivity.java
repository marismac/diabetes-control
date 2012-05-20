package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.android.diabetescontrol.adapters.RegistroMedicoAdapter;
import com.android.diabetescontrol.business.RegistrosMedicosBusiness;

public class ListaRegistrosMedicosActivity extends ListActivity {
	String codPaciente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent().getExtras().get("codPaciente") != null
				&& !"".equals(getIntent().getExtras().get("codPaciente").toString())) {
			codPaciente = (getIntent().getExtras().get("codPaciente")
					.toString());
			setListAdapter(new RegistroMedicoAdapter(this,
					new RegistrosMedicosBusiness().getRegistrosMedicosPaciente(this, codPaciente)));
		}
		super.onCreate(savedInstanceState);
	}
}