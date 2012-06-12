package com.android.diabetescontrol.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.android.diabetescontrol.adapters.RegistroAdapter;
import com.android.diabetescontrol.business.RegistrosMedicosBusiness;
import com.android.diabetescontrol.database.NotaRegistroMedicoDAO;
import com.android.diabetescontrol.model.NotaRegistroMedico;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;
import com.android.diabetescontrol.webservice.CadNotaWS;
import com.android.diabetescontrol.webservice.GetRegistroWS;

public class ListaRegistrosMedicosActivity extends ListActivity {
	String codPaciente;
	Context ctx = this;
	Boolean salvar = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent().getExtras().get("codPaciente") != null
				&& !"".equals(getIntent().getExtras().get("codPaciente")
						.toString())) {
			codPaciente = (getIntent().getExtras().get("codPaciente")
					.toString());
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(ctx)) {
				new GetRegistroWS(this).sincRegistrosMedico(codPaciente);
			}
			setListAdapter(new RegistroAdapter(this,
					new RegistrosMedicosBusiness().getRegistrosPaciente(this,
							codPaciente)));
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Registro registro = (Registro) l.getAdapter().getItem(position);
		final NotaRegistroMedico nrm = new NotaRegistroMedico();
		nrm.setIdRegistro(registro.getId());
		nrm.setSincronizado("N");
		nrm.setTipoUser(Utils.tipo_modo(this));

		// Inicia criação da AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Nota sobre o Registro");
		builder.setMessage(registro.getTipo() + " ( "
				+ registro.getValor().toString() + " " + registro.getUnidade()
				+ " ) ");
		builder.setCancelable(true);
		final EditText notaRegistro = new EditText(this);
		builder.setView(notaRegistro);
		builder.setPositiveButton("Salvar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (notaRegistro != null
								&& !"".equals(notaRegistro.getText())) {
							nrm.setDescricao(notaRegistro.getText().toString());
							NotaRegistroMedicoDAO nrmDao = new NotaRegistroMedicoDAO(
									ctx);
							nrmDao.open();
							nrmDao.criarNotaRegistroMedico(nrm);
							nrmDao.close();
							salvar = true;
						}
					}
				});
		builder.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.create();
		builder.show();
		// Fecha criação da AlertDialog
		if (salvar) {
			if (Utils
					.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
					&& Utils.isSelectSynchronize(ctx)) {
				new CadNotaWS(nrm, ctx).sincNota();
			}
		}
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.configura) {
			Intent i = new Intent(ListaRegistrosMedicosActivity.this,
					PreferenciasActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}