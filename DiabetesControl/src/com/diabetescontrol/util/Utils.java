package com.diabetescontrol.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.widget.EditText;

import com.diabetescontrol.activities.R;

public class Utils {
	public static void criaAlertSalvar(Context ctx, String msg) {
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
		if (msg == null) {
			msg = "Dados salvos!";
		}
		alertDialog.setTitle(msg);
		alertDialog.setIcon(R.drawable.save);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alertDialog.show();
	}

	public static Timestamp stringToTimestamp(String dataString)
			throws ParseException {
		Long dataLong = Long.valueOf(dataString);
		Date date = new Date(dataLong);
		Timestamp timest = new Timestamp(date.getTime());
		return timest;

	}

	public static void criarAlertaErro(Context ctx, String msg) {
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
		alertDialog.setTitle(msg);
		alertDialog.setIcon(R.drawable.alerta);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alertDialog.show();
	}

	public static boolean isSelectSynchronize(Context ctx) {
		SharedPreferences prefs = null;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getBoolean("webservice", false);
	}

	public static boolean isPaciente(Context ctx) {
		SharedPreferences prefs = null;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		if ("P".equals(prefs.getString("tipomodo", "P"))) {
			return true;
		}
		return false;
	}

	public static String tipo_modo(Context ctx) {
		SharedPreferences prefs = null;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getString("tipomodo", "P");
	}

	public static final String URL_WS(Context ctx) {
		String url = PreferenceManager.getDefaultSharedPreferences(ctx)
				.getString("endereco_ws", "192.168.2.2");
		return "http://" + url + ":8080/DiabetesWS/DiabetesWS?WSDL";
	}

	public static boolean isPrenchido(EditText edittext) {
		if ("".equals(edittext.getText().toString())) {
			edittext.setError("Campo obrigatório.");
			edittext.setFocusable(true);
			return false;
		}
		return true;
	}

	public static boolean existConnectionInternet(
			ConnectivityManager connectivity) {
		boolean lblnRet = false;
		try {
			ConnectivityManager cm = connectivity;
			if (cm.getActiveNetworkInfo() != null
					&& cm.getActiveNetworkInfo().isAvailable()
					&& cm.getActiveNetworkInfo().isConnected()) {
				lblnRet = true;
			} else {
				lblnRet = false;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return lblnRet;
	}
}
