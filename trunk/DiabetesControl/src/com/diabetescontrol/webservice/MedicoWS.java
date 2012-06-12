package com.diabetescontrol.webservice;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.model.Medico;

public class MedicoWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "addMedico";
	private Medico med;
	private Context ctx;
	private SoapObject request;

	public MedicoWS(Medico med, Context ctx) {
		this.med = med;
		this.ctx = ctx;
		this.request = new SoapObject(namespace, method_name);
	}

	public void sincMedico() {

		request.addProperty("nomeMed", med.getNome());
		request.addProperty("emailMed", med.getEmail());
		request.addProperty("registroMed", med.getRegistro());
		request.addProperty("codMedico", med.getCodWS());
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
