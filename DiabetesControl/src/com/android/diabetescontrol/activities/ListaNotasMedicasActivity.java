package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.android.diabetescontrol.adapters.NotaRegistroMedicoAdapter;
import com.android.diabetescontrol.business.NotasRegistrosMedicosBusiness;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Utils;
import com.android.diabetescontrol.webservice.GetNotaWS;

public class ListaNotasMedicasActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Utils
				.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
				&& Utils.isSelectSynchronize(this)
				&& Paciente.getCODIGOPACIENTE() != null && Utils.isPaciente(this)) {
			new GetNotaWS(this).sincNotasMedico(Paciente.getCODIGOPACIENTE());
		}
		setListAdapter(new NotaRegistroMedicoAdapter(this,
				new NotasRegistrosMedicosBusiness()
						.getNotasRegistrosPaciente(this)));
		super.onCreate(savedInstanceState);
	}
}