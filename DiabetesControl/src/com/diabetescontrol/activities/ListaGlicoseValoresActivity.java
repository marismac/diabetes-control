package com.diabetescontrol.activities;

import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.diabetescontrol.business.GlicoseValorMinMaxBusiness;

public class ListaGlicoseValoresActivity extends ListActivity {
	private TextView textSem;
	private Double valorMax;
	private Double valorMin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_registros);
		if (getIntent().getExtras().get("valorMax") != null
				&& !"".equals(getIntent().getExtras().get("valorMax")
						.toString())) {
			valorMax = Double.valueOf(getIntent().getExtras().get("valorMax")
					.toString());
		}
		if (getIntent().getExtras().get("valorMin") != null
				&& !"".equals(getIntent().getExtras().get("valorMin")
						.toString())) {
			valorMin = Double.valueOf(getIntent().getExtras().get("valorMin")
					.toString());
		}

		List<Map<String, String>> regs = new GlicoseValorMinMaxBusiness()
				.getUltimosRegistrosList(this, valorMax, valorMin);
		if (regs != null && !regs.isEmpty()) {
			textSem = (TextView) findViewById(R.id.tvSemRes);
			textSem.setVisibility(View.INVISIBLE);
			String[] from = { "Master", "Detail" };
			int[] to = { android.R.id.text1, android.R.id.text2 };

			SimpleAdapter ad = new SimpleAdapter(this, regs,
					android.R.layout.simple_list_item_2, from, to);
			ListView lv = (ListView) findViewById(android.R.id.list);
			lv.setAdapter(ad);
			lv.setCacheColorHint(0);
		}
	}
}