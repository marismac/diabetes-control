package com.android.diabetescontrol.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;

public class consultaRegistroActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultaregistros);
		List<Map<String, String>> regs = getRegistrosList();

		String[] from = { "Master", "Detail" };
		int[] to = { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter ad = new SimpleAdapter(this, regs,
				android.R.layout.simple_list_item_2, from, to);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(ad);
	}

	private List<Map<String, String>> getRegistrosList() {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		RegistroDAO regDAO = new RegistroDAO(this);
		Map<String, String> m = null;
		regDAO.open();
		Cursor c = regDAO.consultarTodosRegistrosV1();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			m = new HashMap<String, String>();
			Registro reg = regDAO.deCursorParaRegistro(c);
			m.put("Master", reg.getDatahora().toString() + " V: "
					+ reg.getValor().toString());
			m.put("Detail", reg.getTipo().toString() + " - "
					+ reg.getCategoria().toString());
			l.add(m);
			c.moveToNext();
		}
		regDAO.close();
		return l;
	}

}
