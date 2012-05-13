package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.diabetescontrol.adapters.PacienteAdapter;
import com.android.diabetescontrol.business.PacientesBusiness;

public class ListaPacientes extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new PacienteAdapter(this, new PacientesBusiness().getPacientesDoMedico(this)));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		l.getAdapter().getItem(position);

		super.onListItemClick(l, v, position, id);
	}
}