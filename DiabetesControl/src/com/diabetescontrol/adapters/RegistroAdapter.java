package com.diabetescontrol.adapters;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Constante;

public class RegistroAdapter extends BaseAdapter {

	private Context ctx;
	private List<Registro> lista;

	public RegistroAdapter(Context ctx, List<Registro> lista) {
		this.ctx = ctx;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Registro registro = lista.get(position);
		LayoutInflater layout = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(
				com.diabetescontrol.activities.R.layout.lista_registromedico,
				null);

		TextView txtID = (TextView) v
				.findViewById(com.diabetescontrol.activities.R.id.tvId);
		txtID.setText(registro.getId().toString());
		txtID.setVisibility(View.INVISIBLE);

		TextView txtNome = (TextView) v
				.findViewById(com.diabetescontrol.activities.R.id.tvPrinc);
		if (Constante.TIPO_PRESSAO.equals(registro.getTipo())) {
			txtNome.setText(registro.getValorPressao() + " "
					+ registro.getUnidade());
		} else {
			txtNome.setText(registro.getValor().toString() + " "
					+ registro.getUnidade());
		}

		TextView txtCod = (TextView) v
				.findViewById(com.diabetescontrol.activities.R.id.tvCod);
		txtCod.setText(registro.getTipo());

		TextView txtMed = (TextView) v
				.findViewById(com.diabetescontrol.activities.R.id.tvMed);
		SimpleDateFormat sdhf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String formattedDateHour = sdhf.format(registro.getDataHora());
		txtMed.setText(formattedDateHour);

		TextView txtCat = (TextView) v
				.findViewById(com.diabetescontrol.activities.R.id.tvSmall);
		txtCat.setText(registro.getCategoria());

		return v;
	}

}