package com.android.diabetescontrol.business;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;

import com.android.diabetescontrol.database.RegistroDAO;
import com.android.diabetescontrol.model.Registro;

public class GlicoseMediaBusiness {
	public static final String TIPO_GLICOSE = "Glicose";
	private Double valorMedioHoje = 0d;
	private Double valorMedioOntem = 0d;
	private int contMedioHoje = 0;
	private int contMedioOntem = 0;

	public void getValorMedio(RegistroDAO regDAO) {
		regDAO.open();
		Cursor c = regDAO.consultarRegistrosPorTipo(TIPO_GLICOSE);
		c.moveToFirst();
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String dataOntem = new SimpleDateFormat("dd/MM/yyyy")
				.format(getDateOntem());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		while (!c.isAfterLast()) {
			Registro reg = regDAO.deCursorParaRegistro(c);
			String dataReg = sdf.format(reg.getDatahora());
			if (dataHoje.equals(dataReg)) {
				++contMedioHoje;
				valorMedioHoje += reg.getValor();
			} else if (dataOntem.equals(dataReg)) {
				++contMedioOntem;
				valorMedioOntem += reg.getValor();
			}
			c.moveToNext();
		}
		valorMedioOntem = valorMedioOntem / contMedioOntem;
		valorMedioHoje = valorMedioHoje / contMedioHoje;
		regDAO.close();
	}

	private Date getDateOntem() {
		Date ontem = new Date();
		ontem.setDate(new Date().getDay() - 1);
		return ontem;
	}

	public String getValorMedioHoje() {
		if (valorMedioHoje == null || valorMedioHoje.isNaN()) {
			return "--";
		}
		return valorMedioHoje.toString();
	}

	public int getContMedioHoje() {
		return contMedioHoje;
	}

	public int getContMedioOntem() {
		return contMedioOntem;
	}

	public String getValorMedioOntem() {
		if (valorMedioOntem == null || valorMedioOntem.isNaN()) {
			return "--";
		}
		return valorMedioOntem.toString();
	}
}