package com.diabetescontrol.activities;

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

import com.diabetescontrol.adapters.RegistroAdapter;
import com.diabetescontrol.business.RegistrosMedicosBusiness;
import com.diabetescontrol.database.NotaRegistroMedicoDAO;
import com.diabetescontrol.model.NotaRegistroMedico;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.Utils;
import com.diabetescontrol.webservice.CadNotaWS;
import com.diabetescontrol.webservice.GetRegistroWS;

public class ListaRegistrosMedicosActivity extends ListActivity {
	String codPaciente;
	Context ctx = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Paciente.getCODIGOPACIENTE() != null) {
			codPaciente = Paciente.getCODIGOPACIENTE();
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
		nrm.setIdRegistro(registro.getCodCelPac());
		nrm.setCodPaciente(registro.getCodPaciente());
		nrm.setSincronizado("N");
		nrm.setTipoUser(Utils.tipo_modo(this));
		if (Constante.TIPO_PRESSAO.equals(registro.getTipo())) {
			nrm.setInfoRegistro(registro.getValorPressao() + " "
					+ registro.getUnidade() + " de " + registro.getTipo());
		} else {
			nrm.setInfoRegistro(registro.getValor().toString() + " "
					+ registro.getUnidade() + " de " + registro.getTipo());
		}

		// Inicia criação da AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Nota sobre o Registro");
		builder.setMessage(nrm.getInfoRegistro());
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
							if (Utils
									.existConnectionInternet((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
									&& Utils.isSelectSynchronize(ctx)) {
								new CadNotaWS(nrm, ctx).sincNota();
							}
							Utils.criaAlertSalvar(ctx, null);
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