package com.diabetescontrol.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.diabetescontrol.adapters.NotaRegistroMedicoAdapter;
import com.diabetescontrol.business.NotasRegistrosMedicosBusiness;

public class ListaNotasRegistrosMedicosActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setListAdapter(new NotaRegistroMedicoAdapter(this,
				new NotasRegistrosMedicosBusiness().getNotasRegistrosPaciente(this)));
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.configura) {
			Intent i = new Intent(ListaNotasRegistrosMedicosActivity.this,
					PreferenciasActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}