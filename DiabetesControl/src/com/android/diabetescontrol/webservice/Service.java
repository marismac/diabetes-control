package com.android.diabetescontrol.webservice;

import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;

import com.android.diabetescontrol.util.Utils;

public class Service {
	private String soap_action = "";
	private String url = "";

	public Service(Context ctx, String soap_action) {
		this.soap_action = soap_action;
		this.url = Utils.URL_WS(ctx);
	}

	@SuppressWarnings("unchecked")
	public String[] execute(SoapObject request) {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		MarshalDate md = new MarshalDate();
		md.register(envelope);
		envelope.setOutputSoapObject(request);
		try {
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			androidHttpTransport.call(soap_action, envelope);
			// SoapObject result = (SoapObject) envelope.getResponse();
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}