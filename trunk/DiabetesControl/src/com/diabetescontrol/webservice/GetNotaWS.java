package com.diabetescontrol.webservice;

import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.database.NotaRegistroMedicoDAO;
import com.diabetescontrol.model.NotaRegistroMedico;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.Utils;

public class GetNotaWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "";
	private Context ctx;
	private SoapObject request;
	private String soap_action = "";
	private String url = "";

	public GetNotaWS(Context ctx) {
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincNotasMedico(String codPaciente) {
		this.method_name = Constante.METODO_WS_GETNOTAMEDICO;
		this.request = new SoapObject(namespace, method_name);
		request.addProperty("codPaciente", codPaciente);
		new servicoAsyncTask().execute();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private String mensagem = "";

		@Override
		protected Void doInBackground(Void... params) {
			mensagem = getRegistrosService();
			progressDialog.cancel();
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
			if (!"sucess".equals(mensagem)) {
				Utils.criarAlertaErro(ctx, "Não foi possível se conectar à "
						+ Utils.URL_WS(ctx));
			}
		}
	}

	private String getRegistrosService() {
		String resultado = "sucess";
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
				NotaRegistroMedico nrm = null;
				int qtde = response.getPropertyCount();
				for (int i = 0; i < qtde; i++) {
					SoapObject property = (SoapObject) response.getProperty(i);
					if ("sucess".equals(property.getProperty(0).toString())) {
						nrm = new NotaRegistroMedico();
						nrm.setDescricao(property.getProperty(1).toString());
						nrm.setInfoRegistro(property.getProperty(2).toString());
						nrm.setCodPaciente(property.getProperty(3).toString());
						nrm.setIdRegistro(Integer.valueOf(property.getProperty(
								4).toString()));
						nrm.setTipoUser(Utils.tipo_modo(ctx));
						nrm.setSincronizado("S");
						insereNota(nrm);
					}
				}
			}
		} catch (SocketTimeoutException e) {
			resultado = "error";
			e.printStackTrace();
		} catch (Exception ex) {
			resultado = "error";
			ex.printStackTrace();
		}
		return resultado;
	}

	private void insereNota(NotaRegistroMedico nrm) {
		NotaRegistroMedicoDAO nrmDao = new NotaRegistroMedicoDAO(ctx);
		nrmDao.open();
		nrmDao.criarNotaRegistroMedico(nrm);
		nrmDao.close();
	}
}
