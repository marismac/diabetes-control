package com.android.diabetescontrol.webservice;

import java.text.ParseException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;

public class RegistroWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "";
	private Registro reg;
	private Context ctx;
	private SoapObject request;
	private String soap_action = "";
	private String url = "";

	public RegistroWS(Context ctx) {
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincRegistro(Registro reg) {
		this.method_name = "addRegistro";
		this.request = new SoapObject(namespace, method_name);
		this.reg = reg;
		request.addProperty("tipo", reg.getTipo());
		request.addProperty("categoria", reg.getCategoria());
		request.addProperty("valor", reg.getValor());
		// request.addProperty("datahora", reg.getDatahora());
		new servicoAsyncTask().execute();
	}

	public void sincRegistrosMedico(String codPaciente) {
		this.method_name = "getRegistrosPaciente";
		this.request = new SoapObject(namespace, method_name);
		request.addProperty("codPaciente", codPaciente);
		new servicoAsyncTask().execute();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			getRegistrosService();
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

	private void getRegistrosService() {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		MarshalDate md = new MarshalDate();
		md.register(envelope);
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			androidHttpTransport.call(soap_action, envelope);
			SoapObject response = (SoapObject) envelope.bodyIn;

			if (response != null) {
				Registro registro = null;
				int qtde = response.getPropertyCount();
				for (int i = 0; i < qtde; i++) {
					SoapObject property = (SoapObject) response.getProperty(i);
					if ("sucess".equals(property.getProperty(0).toString())) {
						registro = new Registro();
						registro.setValor(Float.valueOf(property.getProperty(1)
								.toString()));
						registro.setCategoria(property.getProperty(2)
								.toString());
						registro.setTipo(property.getProperty(3).toString());
						registro.setCodPaciente(property.getProperty(4)
								.toString());
						registro.setUnidade(property.getProperty(5).toString());
						registro.setCodCelPac(Integer.valueOf(property
								.getProperty(6).toString()));
						registro.setDataHora(Utils.stringToTimestamp(property
								.getProperty(7).toString()));
						registro.setSincronizado("S");
						registro.setModoUser(Utils.tipo_modo(ctx));
						insereRegistro(registro);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void insereRegistro(Registro reg) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		regDao.open();
		regDao.criarRegistro(reg);
		regDao.close();
	}
}
