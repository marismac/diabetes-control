package com.android.diabetescontrol.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.android.diabetescontrol.activities.testeWSActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class ConvertService {
	// private static final String SOAP_ACTION =
	// "http://tempuri.org/CelsiusToFahrenheit";
	private static final String SOAP_ACTION = "";
	// private static final String METHOD_NAME = "CelsiusToFahrenheit";
	private static final String METHOD_NAME = "createOrUpdateRegistro";
	private static final String NAMESPACE = "http://server.soap.codelab.appengine.google.com/";
	// private static final String URL =
	// "http://www.w3schools.com/webservices/tempconvert.asmx";
	private static final String URL = "http://diabetescontrolws.appspot.com/EntityAPIService.wsdl";
	private ProgressDialog progressDialog;

	// public void isConnected() throws Exception {
	// NetworkInfo info = null;
	// info = ((ConvertService)
	// this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	// if (info == null || !info.isConnected()) {
	// throw new Exception("Verifique sua conex�o com a Internet.");
	// }
	// if (info.isRoaming()) {
	// throw new Exception("Verifique sua conex�o com a Internet.");
	// }
	// }

	@SuppressWarnings("deprecation")
	public String Convert() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		System.out.println("P1");

		PropertyInfo propInfo = new PropertyInfo();
		propInfo.name = "arg0";
		propInfo.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo, "30");
		PropertyInfo propInfo1 = new PropertyInfo();
		propInfo1.name = "arg1";
		propInfo1.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo1, "30");
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.name = "arg2";
		propInfo2.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo2, "30");
		PropertyInfo propInfo3 = new PropertyInfo();
		propInfo3.name = "arg3";
		propInfo3.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo3, "30");
		PropertyInfo propInfo4 = new PropertyInfo();
		propInfo4.name = "arg4";
		propInfo4.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo4, "30");
		PropertyInfo propInfo5 = new PropertyInfo();
		propInfo5.name = "arg5";
		propInfo5.type = PropertyInfo.STRING_CLASS;
		request.addProperty(propInfo5, "30");
		System.out.println("P2");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		System.out.println("P3");
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true;
			System.out.println("P4");
			androidHttpTransport.call(SOAP_ACTION, envelope);
			System.out.println("P5");
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
			System.out.println("P6");
			System.out.println(result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}