package com.android.diabetescontrol.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.android.diabetescontrol.activities.testeWSActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ConvertService {
	private static final String SOAP_ACTION = "http://tempuri.org/CelsiusToFahrenheit";
	private static final String METHOD_NAME = "CelsiusToFahrenheit";
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	private ProgressDialog progressDialog;
	
	// public void isConnected() throws Exception {
	// NetworkInfo info = null;
	// info = ((ConvertService)
	// this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	// if (info == null || !info.isConnected()) {
	// throw new Exception("Verifique sua conexão com a Internet.");
	// }
	// if (info.isRoaming()) {
	// throw new Exception("Verifique sua conexão com a Internet.");
	// }
	// }

	public String Convert() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("Celsius", "30");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			return result.toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}