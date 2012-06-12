package com.android.diabetescontrol.webservice;

import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Constante;
import com.android.diabetescontrol.util.Utils;

public class CadPacienteWS {
	private String namespace = "http://servico.diabetes.com/";
	private Paciente pac;
	private Context ctx;
	private SoapObject request;
	private String soap_action = "";
	private String url = "";
	private String mensagem = "";

	public CadPacienteWS(Paciente pac, Context ctx) {
		this.pac = pac;
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	private void salvaPaciente(Paciente pac) {
		PacienteDAO pacDao = new PacienteDAO(ctx);
		pacDao.open();
		if (pac.getId() == null) {
			pacDao.criarPaciente(pac);
		} else {
			pacDao.atualizarPaciente(pac);
		}
		pacDao.close();
	}

	public void sincPaciente() {
		this.request = new SoapObject(namespace, Constante.METODO_WS_CADPACIENTE);
		request.addProperty("nomePac", pac.getNome());
		request.addProperty("emailPac", pac.getEmail());
		request.addProperty("codPaciente", pac.getCodPaciente());
		request.addProperty("sexoPac", pac.getSexo());
		request.addProperty("nascimentoPac", pac.getDatanascimento());
		request.addProperty("senhaPac", pac.getSenhaPaciente());
		if (pac.getId() == null) {
			request.addProperty("update", "N");
		} else {
			request.addProperty("update", "S");
		}
		new servicoAsyncTask().execute();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			mensagem = setPacienteService();
			progressDialog.cancel();
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(ctx);
			progressDialog.setMessage("Sincronizando");
			progressDialog.show();
		}
		
		private void carregaCodPaciente() {
			PacienteDAO pacDao = new PacienteDAO(ctx);
			pacDao.open();
			Paciente.setCODIGOPACIENTE(pacDao.getCodPac());
			pacDao.close();
		}

		@Override
		protected void onPostExecute(Void result) {
			if (mensagem != null) {
				if ("sucess".equals(mensagem)) {
					salvaPaciente(pac);
					Utils.criaAlertSalvar(ctx, null);
					carregaCodPaciente();
				} else if ("internet".equals(mensagem)) {
					Utils.criarAlertaErro(
							ctx,
							"Não foi possível se conectar à "
									+ Utils.URL_WS(ctx));
				} else if ("duplicado".equals(mensagem)) {
					Utils.criarAlertaErro(ctx,
							"O Código de Paciente está em uso. Informe outro!");
				} else {
					Utils.criarAlertaErro(ctx,
							"Não foi possível salvar o registro! Verifique a internet!");
				}
			}
		}
	}

	private String setPacienteService() {
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
