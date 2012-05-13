package com.android.diabetescontrol.util;

import com.android.diabetescontrol.activities.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CadastrosUtil {
	public static final void criaAlertSalvar(Context ctx) {
		AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
		alertDialog.setTitle("Dados salvos!");
		alertDialog.setIcon(R.drawable.save);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		alertDialog.show();
	}
	
	public final boolean isPaciente(Context ctx) {
		SharedPreferences prefs = null;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		if ("P".equals(prefs.getString("tipomodo", "P"))) {
			return true;
		}
		return false;
	}
	
	

}
