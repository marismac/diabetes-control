package com.diabetescontrol.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.diabetescontrol.adapters.PacienteAdapter;
import com.diabetescontrol.business.PacientesBusiness;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.GetRegistroWS;

public class ListaPacientesActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Paciente> lista = new PacientesBusiness()
				.getPacientesDoMedico(this);
		setListAdapter(new PacienteAdapter(this, lista));
		for (Paciente pac : lista) {
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(this)) {
				new GetRegistroWS(this).sincRegistrosMedico(pac
						.getCodPaciente());
			}
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Paciente paciente = (Paciente) l.getAdapter().getItem(position);

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().get("origem") != null
				&& "G".equals(getIntent().getExtras().get("origem"))) {
			// Entra aqui se for para selecionar o paciente para os GRÁFICOS
			Intent i = new Intent(ListaPacientesActivity.this,
					SelectGraficosActivity.class);
			Paciente.setCODIGOPACIENTE(paciente.getCodPaciente());
			startActivity(i);
		} else if (getIntent().getExtras() != null
				&& getIntent().getExtras().get("origem") != null
				&& "R".equals(getIntent().getExtras().get("origem"))) {
			// Entra aqui se for para selecionar o paciente para os RELATÓRIOS
			Intent i = new Intent(ListaPacientesActivity.this,
					SelectRelatoriosActivity.class);
			Paciente.setCODIGOPACIENTE(paciente.getCodPaciente());
			startActivity(i);
		} else {
			Intent i = new Intent(ListaPacientesActivity.this,
					ListaRegistrosMedicosActivity.class);
			Paciente.setCODIGOPACIENTE(paciente.getCodPaciente());
			startActivity(i);
		}
		super.onListItemClick(l, v, position, id);
	}
}