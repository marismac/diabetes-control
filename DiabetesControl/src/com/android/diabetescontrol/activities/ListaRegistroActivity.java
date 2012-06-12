package com.android.diabetescontrol.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;
import com.android.diabetescontrol.util.Utils;

public class ListaRegistroActivity extends ListActivity {
	private TextView textSem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_registros);
		String dataFiltro = "01/01/1900";
		String tipoCategoria = "%";
		if (getIntent().getExtras().get("dataFiltro") != null
				&& !"".equals(getIntent().getExtras().get("dataFiltro"))) {
			dataFiltro = (String) getIntent().getExtras().get("dataFiltro");
		}
		if (getIntent().getExtras().get("tipoCategoria") != null
				&& !"".equals(getIntent().getExtras().get("tipoCategoria"))) {
			tipoCategoria = (String) getIntent().getExtras().get(
					"tipoCategoria");
		}

		List<Map<String, String>> regs = getRegistrosList(dataFiltro,
				tipoCategoria);
		String[] from = { "Master", "Detail" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter ad = new SimpleAdapter(this, regs,
				android.R.layout.simple_list_item_2, from, to);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(ad);

	}

	private List<Map<String, String>> getRegistrosList(String dataFiltro,
			String tipoCategoriaFiltro) {
		textSem = (TextView) findViewById(R.id.tvSemRes);
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		RegistroDAO regDAO = new RegistroDAO(this);
		Map<String, String> m = null;
		regDAO.open();
		Cursor c = regDAO.consultarRegistrosPorTipo(tipoCategoriaFiltro);
		c.moveToFirst();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdhf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		while (!c.isAfterLast()) {
			m = new HashMap<String, String>();
			Registro reg = regDAO.deCursorParaRegistro(c);
			String formattedDate = sdf.format(reg.getDataHora());
			System.out.println(dataFiltro + " - " + formattedDate + " - "
					+ reg.getDataHora());
			if (dataFiltro.equals(formattedDate)
					&& reg.getModoUser().equals(Utils.tipo_modo(this))) {
				textSem.setVisibility(View.INVISIBLE);
				m.put("Master", reg.getTipo().toString() + ": "
						+ reg.getValor().toString());
				String formattedDateHour = sdhf.format(reg.getDataHora());
				m.put("Detail", formattedDateHour + " - "
						+ reg.getCategoria().toString());
				l.add(m);
			}
			c.moveToNext();
		}
		regDAO.close();
		return l;
	}
}
