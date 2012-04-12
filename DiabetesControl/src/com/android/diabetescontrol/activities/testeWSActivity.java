package com.android.diabetescontrol.activities;

import com.android.diabetescontrol.webservice.ConvertService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class testeWSActivity extends Activity {
	private TextView textView = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);
		textView = (TextView) findViewById(R.id.tVResult);
		ConvertService service = new ConvertService();
	    String result = service.Convert();
	    textView.setText(result);	
	}
	
	
}
