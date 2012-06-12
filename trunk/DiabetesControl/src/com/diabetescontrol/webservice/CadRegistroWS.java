package com.diabetescontrol.webservice;

import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Utils;

public class CadRegistroWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "";
	private Registro registro;
	private Context ctx;
	private SoapObject request;
	private String soap_action = "";
	private String url = "";

	public CadRegistroWS(Context ctx) {
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincRegistro(Registro reg) {
		Date datahora = new Date(reg.getDataHora().getTime());
		this.method_name = "addRegistro";
		this.request = new SoapObject(namespace, method_name);
		registro = reg;
		request.addProperty("tipo", reg.getTipo());
		request.addProperty("categoria", reg.getCategoria());
		request.addProperty("valor", reg.getValor());
		request.addProperty("datahora", datahora);
		request.addProperty("codpaciente", reg.getCodPaciente());
		request.addProperty("unidade", reg.getUnidade());
		request.addProperty("idcelular", reg.getId());
		request.addProperty("idmedicamento", reg.getMedicamento());
		request.addProperty("valorpressao", reg.getValorPressao());
		new servicoAsyncTask().execute();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			String resultado = getRegistrosService();
			if ("sucess".equals(resultado)) {
				RegistroDAO regDao = new RegistroDAO(ctx);
				regDao.open();
				registro.setSincronizado("S");
				regDao.atualizaRegistro(registro);
				regDao.close();
			}
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

	private String getRegistrosService() {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		MarshalDate md = new MarshalDate();
		MarshalFloat mf = new MarshalFloat();
		mf.register(envelope);
		md.register(envelope);
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			androidHttpTransport.call(soap_action, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			return result.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
