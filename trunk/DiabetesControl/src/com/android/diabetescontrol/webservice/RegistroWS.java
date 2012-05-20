package com.android.diabetescontrol.webservice;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.diabetescontrol.model.Registro;

public class RegistroWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "addRegistro";
	private Registro reg;
	private Context ctx;
	private SoapObject request;

	public RegistroWS(Registro reg, Context ctx) {
		this.reg = reg;
		this.ctx = ctx;
		this.request = new SoapObject(namespace, method_name);
	}

	public void sincRegistro() {
		request.addProperty("tipo", reg.getTipo());
		request.addProperty("categoria", reg.getCategoria());
		request.addProperty("valor", reg.getValor());
		// request.addProperty("datahora", reg.getDatahora());
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
