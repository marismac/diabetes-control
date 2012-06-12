package com.diabetescontrol.activities;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.diabetescontrol.adapters.NotaRegistroMedicoAdapter;
import com.diabetescontrol.business.NotasRegistrosMedicosBusiness;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.GetNotaWS;

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