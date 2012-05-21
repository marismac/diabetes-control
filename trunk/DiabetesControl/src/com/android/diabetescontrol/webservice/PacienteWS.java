package com.android.diabetescontrol.webservice;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.diabetescontrol.model.Paciente;

public class PacienteWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "addPaciente";
	private Paciente pac;
	private Context ctx;
	private SoapObject request;

	public PacienteWS(Paciente pac, Context ctx) {
		this.pac = pac;
		this.ctx = ctx;
		this.request = new SoapObject(namespace, method_name);
	}

	public void sincPaciente() {

		request.addProperty("nomePac", pac.getNome());
		request.addProperty("emailPac", pac.getEmail());
		request.addProperty("codPaciente", pac.getCodPaciente());
		request.addProperty("sexoPac", pac.getSexo());
		request.addProperty("nascimentoPac", pac.getDatanascimento());
		request.addProperty("senhaPac", pac.getSenhaPaciente());
		new servicoAsyncTask().execute();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			new Service(ctx, "").execute(request);
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ctx);
			progressDialog.setMessage("Sincronizando");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.cancel();
		}

	}
}
