package com.android.diabetescontrol.adapters;

import java.util.List;

import com.android.diabetescontrol.model.Paciente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PacienteAdapter extends BaseAdapter{
	
	private Context ctx;
	private List<Paciente> lista;
	
	public PacienteAdapter(Context ctx, List<Paciente> lista){
		this.ctx = ctx;
		this.lista= lista;
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
		Paciente paciente = lista.get(position);
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(com.android.diabetescontrol.activities.R.layout.listapaciente, null);
		
		TextView txtID = (TextView)v.findViewById(com.android.diabetescontrol.activities.R.id.tvCod);
		txtID.setText(paciente.getId().toString());
		
		TextView txtNome = (TextView)v.findViewById(com.android.diabetescontrol.activities.R.id.tvPrinc);
		txtNome.setText(paciente.getNome());
		
		TextView txtEmail = (TextView)v.findViewById(com.android.diabetescontrol.activities.R.id.tvSec);
		txtEmail.setText(paciente.getEmail());
		
		return v;
	}
	

}
