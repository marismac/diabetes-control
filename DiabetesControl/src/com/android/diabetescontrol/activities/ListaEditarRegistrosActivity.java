package com.android.diabetescontrol.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.diabetescontrol.adapters.EditRegistroAdapter;
import com.android.diabetescontrol.model.Registro;

public class ListaEditarRegistrosActivity extends ListActivity {
	Context ctx = this;
	Boolean salvar = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setListAdapter(new EditRegistroAdapter(this, new Registro().lista(ctx,
				null, "50")));
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onRestart() {
		setListAdapter(new EditRegistroAdapter(this, new Registro().lista(ctx,
				null, "50")));
		super.onRestart();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Registro registro = (Registro) l.getAdapter().getItem(position);
		Intent i = new Intent(ListaEditarRegistrosActivity.this,
				CadastroRegistroActivity.class);

		i.putExtra("editar", true);
		i.putExtra("idRegistro", registro.getId());
		i.putExtra("valorRegistro", registro.getValor());
		i.putExtra("valorPressaoRegistro", registro.getValorPressao());
		i.putExtra("categoriaRegistro", registro.getCategoria());
		i.putExtra("tipoRegistro", registro.getTipo());
		i.putExtra("dataHoraRegistro", registro.getDataHora());
		i.putExtra("unidadeRegistro", registro.getUnidade());
		i.putExtra("medicamentoRegistro", registro.getMedicamento());

		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.configura) {
			Intent i = new Intent(ListaEditarRegistrosActivity.this,
					PreferenciasActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}