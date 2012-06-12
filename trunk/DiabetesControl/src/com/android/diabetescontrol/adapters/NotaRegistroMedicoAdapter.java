package com.android.diabetescontrol.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.diabetescontrol.model.NotaRegistroMedico;

public class NotaRegistroMedicoAdapter extends BaseAdapter {

	private Context ctx;
	private List<NotaRegistroMedico> lista;

	public NotaRegistroMedicoAdapter(Context ctx, List<NotaRegistroMedico> lista) {
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
		NotaRegistroMedico nrm = lista.get(position);
		LayoutInflater layout = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout
				.inflate(
						com.android.diabetescontrol.activities.R.layout.lista_notamedica,
						null);

		ImageView btEdit = (ImageView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.btEdit);
		if (position > 0) {
			btEdit.setVisibility(View.GONE);
			TextView tituloEdit = (TextView) v
					.findViewById(com.android.diabetescontrol.activities.R.id.textView1);
			tituloEdit.setVisibility(View.GONE);
		}

		TextView txtNome = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvId);
		txtNome.setText(nrm.getCodPaciente());

		TextView txtMed = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvMed);
		txtMed.setText(nrm.getInfoRegistro());

		TextView txtID = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvPrinc);
		txtID.setText(nrm.getDescricao());

		TextView txtCod = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvCod);
		txtCod.setText(nrm.getId().toString());
		txtCod.setVisibility(View.INVISIBLE);

		TextView txtCat = (TextView) v
				.findViewById(com.android.diabetescontrol.activities.R.id.tvSmall);
		txtCat.setText(nrm.getIdRegistro().toString());
		txtCat.setVisibility(View.INVISIBLE);

		return v;
	}

}