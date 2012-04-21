package com.android.diabetescontrol.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import com.android.diabetescontrol.webservice.ConvertService;

public class testeWSActivity extends Activity {
	EditText textView = null;
	String resultado = null;
	ConvertService service = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		textView = (EditText) findViewById(R.id.tVResult);
		service = new ConvertService();
		new ChamaWSAsyncTask().execute();

	}

	class ChamaWSAsyncTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(Void... params) {
			service.Convert();
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(testeWSActivity.this);
			progressDialog.setMessage("Verificando...");
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			System.out.println("Teste");
			System.out.println(resultado);
			textView.setText(resultado);
		}

	}
}
