package com.android.diabetescontrol.webservice;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.diabetescontrol.database.PacienteDAO;
import com.android.diabetescontrol.model.Paciente;
import com.android.diabetescontrol.util.Utils;

public class PacienteWS {
	private String namespace = "http://servico.diabetes.com/";
	private Paciente pac;
	private Paciente paciente;
	private Context ctx;
	private SoapObject request;
	String[] mensagens = new String[10];

	public PacienteWS(Paciente pac, Context ctx) {
		this.pac = pac;
		this.ctx = ctx;
	}

	public void sincPacienteDoMedico() {
		this.request = new SoapObject(namespace, "addPacientedoMedico");
		request.addProperty("codPaciente", pac.getCodPaciente());
		request.addProperty("senhaPaciente", pac.getSenhaPaciente());
		new servicoAsyncTask().execute();
	}

	public Paciente objectToPaciente(String[] results) {
		Paciente pac = new Paciente();
		// pac.setId(Integer.valueOf(results[1]));
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
			mensagens = new Service(ctx, "").execute(request);
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
					paciente = objectToPaciente(mensagens);
					Utils.criaAlertSalvar(ctx, null);
				} else {
					Utils.criarAlertaErro(ctx, mensagens[0]);
				}
			}
		}
	}
}
