package com.android.diabetescontrol.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ConvertService {
	private static final String SOAP_ACTION = "http://zbra.com.br/springtutorial/Convert";
	private static final String METHOD_NAME = "Convert";
	private static final String NAMESPACE = "http://zbra.com.br/springtutorial";
	private static final String URL = "http://192.168.10.103/SpringTutorialService/CurrencyServiceWS.asmx";

	public String Convert(String fromCurrency, String toCurrency, String amount) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("from", fromCurrency);
		request.addProperty("to", toCurrency);
		request.addProperty("value", amount);

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