package com.diabetescontrol.webservice;

import java.net.SocketTimeoutException;
import java.text.ParseException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.Utils;

public class GetRegistroWS {
	private String namespace = "http://servico.diabetes.com/";
	private String method_name = "";
	private Context ctx;
	private SoapObject request;
	private String soap_action = "";
	private String url = "";

	public GetRegistroWS(Context ctx) {
		this.ctx = ctx;
		this.url = Utils.URL_WS(ctx);
	}

	public void sincRegistrosMedico(String codPaciente) {
		this.method_name = Constante.METODO_WS_GETREGISTROSPACIENTE;
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
				Registro registro = null;
				int qtde = response.getPropertyCount();
				for (int i = 0; i < qtde; i++) {
					SoapObject property = (SoapObject) response.getProperty(i);
					if ("sucess".equals(property.getProperty(0).toString())) {
						registro = new Registro();
						// É pego primeiro para realizar as validações
						// porteriores.
						registro.setTipo(property.getProperty(3).toString());
						if (Constante.TIPO_PRESSAO.equals(registro.getTipo())) {
							registro.setValorPressao(property.getProperty(1)
									.toString());
						} else {
							registro.setValor(Float.valueOf(property
									.getProperty(1).toString()));
						}
						registro.setCategoria(property.getProperty(2)
								.toString());
						registro.setCodPaciente(property.getProperty(4)
								.toString());
						registro.setUnidade(property.getProperty(5).toString());
						registro.setCodCelPac(Integer.valueOf(property
								.getProperty(6).toString()));
						registro.setDataHora(Utils.stringToTimestamp(property
								.getProperty(7).toString()));
						if (Constante.TIPO_MEDICAMENTO.equals(registro
								.getTipo())) {
							registro.setMedicamento(Integer.valueOf(property
									.getProperty(8).toString()));
						} else {
							registro.setMedicamento(null);
						}
						registro.setSincronizado("S");
						registro.setModoUser(Utils.tipo_modo(ctx));
						insereRegistro(registro);
					}
				}
			}
		} catch (ParseException e) {
			resultado = "error";
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			resultado = "error";
			e.printStackTrace();
		} catch (Exception ex) {
			resultado = "error";
			ex.printStackTrace();
		}
		return resultado;
	}

	private void insereRegistro(Registro reg) {
		RegistroDAO regDao = new RegistroDAO(ctx);
		regDao.open();
		regDao.criarRegistro(reg);
		regDao.close();
	}
}
