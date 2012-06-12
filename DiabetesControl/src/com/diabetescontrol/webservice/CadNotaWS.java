package com.diabetescontrol.webservice;

import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.database.NotaRegistroMedicoDAO;
import com.diabetescontrol.model.NotaRegistroMedico;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.Utils;

public class CadNotaWS {
	private String namespace = "http://servico.diabetes.com/";
	private NotaRegistroMedico nrm;
	private String soap_action = "";
	private String url = "";
	private Context ctx;
	private SoapObject request;
	String mensagem = "";

	public CadNotaWS(NotaRegistroMedico nrm, Context ctx) {
		this.nrm = nrm;
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincNota() {
		this.request = new SoapObject(namespace,
				Constante.METODO_WS_CADNOTAMEDICO);
		request.addProperty("descricao", nrm.getDescricao());
		request.addProperty("infoRegistro", nrm.getInfoRegistro());
		request.addProperty("codPaciente", nrm.getCodPaciente());
		request.addProperty("idCelular", nrm.getIdRegistro());
		new servicoAsyncTask().execute();

	}

	// Método utilizado para marcar as notas como Sincronizadas
	private void salvaNota(NotaRegistroMedico nrm) {
		NotaRegistroMedicoDAO nrmDao = new NotaRegistroMedicoDAO(ctx);
		nrm.setSincronizado("S");
		nrmDao.open();
		nrmDao.atualizaNota(nrm);
		nrmDao.close();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			mensagem = cadNotaService();
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
			if (mensagem != null) {
				if ("sucess".equals(mensagem)) {
					salvaNota(nrm);
				} else if ("internet".equals(mensagem)) {
					Utils.criarAlertaErro(
							ctx,
							"Não foi possível se conectar à "
									+ Utils.URL_WS(ctx));
				} else {
					Utils.criarAlertaErro(ctx,
							"Não foi possível salvar a nota!");
				}
			}
		}
	}

	private String cadNotaService() {
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
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "internet";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
