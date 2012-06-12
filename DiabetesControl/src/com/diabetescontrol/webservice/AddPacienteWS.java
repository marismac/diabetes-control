package com.diabetescontrol.webservice;

import java.net.SocketTimeoutException;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.database.PacienteDAO;
import com.diabetescontrol.model.Paciente;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.Utils;

public class AddPacienteWS {
	private String namespace = "http://servico.diabetes.com/";
	private Paciente pac;
	private String soap_action = "";
	private String url = "";
	private Context ctx;
	private SoapObject request;
	String[] mensagens = new String[10];

	public AddPacienteWS(Paciente pac, Context ctx) {
		this.pac = pac;
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincPacienteDoMedico() {
		this.request = new SoapObject(namespace,
				Constante.METODO_WS_ADDPACIENTEMEDICO);
		request.addProperty("codPaciente", pac.getCodPaciente());
		request.addProperty("senhaPaciente", pac.getSenhaPaciente());
		PacienteDAO pacDao = new PacienteDAO(ctx);
		pacDao.open();
		if (pacDao.consultarPacienteCodPac(pac.getCodPaciente()) != null) {
			Utils.criarAlertaErro(ctx,
					"O paciente já foi adicionado " + pac.getCodPaciente());

		} else {
			new servicoAsyncTask().execute();
		}

	}

	public Paciente objectToPaciente(String[] results) {
		Paciente pac = new Paciente();
		pac.setNome(results[1]);
		pac.setEmail(results[2]);
		pac.setCodPaciente(results[3]);
		salvaPaciente(pac);
		return pac;
	}

	private void salvaPaciente(Paciente pac) {
		PacienteDAO pacDao = new PacienteDAO(ctx);
		pacDao.open();
		pacDao.criarPaciente(pac);
		pacDao.close();
	}

	class servicoAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			mensagens = setPacienteMedicoService();
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
			if (mensagens != null) {
				if ("sucess".equals(mensagens[0].toString())) {
					objectToPaciente(mensagens);
					Utils.criaAlertSalvar(ctx, null);
				} else if ("internet".equals(mensagens[0].toString())) {
					Utils.criarAlertaErro(
							ctx,
							"Não foi possível se conectar à "
									+ Utils.URL_WS(ctx));
				} else {
					Utils.criarAlertaErro(ctx, mensagens[0]);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String[] setPacienteMedicoService() {
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
			Vector<Object> rs = (java.util.Vector<Object>) envelope
					.getResponse();
			String[] mensagens = new String[20];
			if (rs != null) {
				int i = 0;
				for (Object cs : rs) {
					if (cs != null) {
						mensagens[i] = cs.toString();
					}
					i++;
				}
			}
			return mensagens;
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			mensagens[0] = "internet";
			return mensagens;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
