package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.diabetescontrol.adapters.PacienteAdapter;
import com.android.diabetescontrol.business.PacientesBusiness;
import com.android.diabetescontrol.model.Paciente;

public class ListaPacientesActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new PacienteAdapter(this,
				new PacientesBusiness().getPacientesDoMedico(this)));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Paciente paciente = (Paciente) l.getAdapter().getItem(position);
		Intent i = new Intent(ListaPacientesActivity.this,
				ListaRegistrosMedicosActivity.class);
		i.putExtra("codPaciente", paciente.getCodPaciente());
		startActivity(i);
		super.onListItemClick(l, v, position, id);
	}
}