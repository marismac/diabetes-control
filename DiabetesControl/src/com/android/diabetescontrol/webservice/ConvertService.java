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
	private static final String SOAP_ACTION = "";
	private static final String METHOD_NAME = "add";
	private static final String NAMESPACE = "http://servico.diabetes.com/";
	private static final String URL = "http://192.168.1.100:8080//DiabetesWS/DiabetesWS?WSDL";

	@SuppressWarnings("deprecation")
	public String Convert() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		System.out.println("P1");
		request.addProperty("i", 2);
		request.addProperty("j", 1);
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