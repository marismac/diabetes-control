package com.android.diabetescontrol.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.diabetescontrol.model.RegistroMedico;

public class RegistroMedicoAdapter extends BaseAdapter {

	private Context ctx;
	private List<RegistroMedico> lista;

	public RegistroMedicoAdapter(Context ctx, List<RegistroMedico> lista) {
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
		RegistroMedico regMed = lista.get(position);
		LayoutInflater layout = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(
				com.android.diabetescontrol.activities.R.layout.listaregistromedico,
				null);
		
		TextView txtID = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvCod);
		txtID.setText(regMed.getId().toString());

		TextView txtNome = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvPrinc);
		txtNome.setText(regMed.getCategoria());

		TextView txtEmail = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvSmall);
		txtEmail.setText(regMed.getValor().toString());

		return v;
	}

}