package com.diabetescontrol.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;

import com.diabetescontrol.database.RegistroDAO;
import com.diabetescontrol.model.Registro;
import com.diabetescontrol.util.Constante;
import com.diabetescontrol.util.DataUtil;

public class GlicoseMediaBusiness {
	private Double valorMedioHoje = 0d;
	private Double valorMedioOntem = 0d;
	private Double valorMedioSemana = 0d;
	private Double valorMedioSemanaPassada = 0d;
	private Double valorMedioMes = 0d;
	private Double valorMedioMesPassado = 0d;
	private Double valorMedioAnual = 0d;
	private Double valorMedioTotal = 0d;
	private int contMedioHoje = 0;
	private int contMedioOntem = 0;
	private int contMedioSemana = 0;
	private int contMedioSemanaPassada = 0;
	private int contMedioMes = 0;
	private int contMedioMesPassado = 0;
	private int contMedioAnual = 0;
	private int contMedioTotal = 0;

	public void getValorMedio(RegistroDAO regDAO) {
		regDAO.open();
		Cursor c = regDAO.consultarRegistrosPorTipo(Constante.TIPO_GLICOSE);
		c.moveToFirst();
		// Popula dados conforme necessário para realizar algumas comparações
		Date dataHojeDate = new Date();
		Date dataOntemDate = new DataUtil().getDateOntem();
		Date dataPrimeiroDiaSemanaDate = new DataUtil().getPrimeiroDiaSemana();
		Date dataPrimeiroDiaSemanaPassada = new DataUtil()
				.getPrimeiroDiaSemanaPassada();
		Date dataPrimeiroDiaMes = new DataUtil().getPrimeiroDiaMes();
		Date dataPrimeiroDiaMesPassado = new DataUtil()
				.getPrimeiroDiaMesPassado();
		Date dataPrimeiroDiaAno = new DataUtil().getPrimeiroDiaAno();
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataHojeDate);
		String dataOntem = new SimpleDateFormat("dd/MM/yyyy")
				.format(dataOntemDate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		while (!c.isAfterLast()) {
			Registro reg = regDAO.deCursorParaRegistro(c);
			String dataReg = sdf.format(reg.getDataHora());
			if (dataHoje.equals(dataReg)) {
				++contMedioHoje;
				valorMedioHoje += reg.getValor();
			} else if (dataOntem.equals(dataReg)) {
				++contMedioOntem;
				valorMedioOntem += reg.getValor();
			}
			if (reg.getDataHora().after(dataPrimeiroDiaSemanaDate)
					&& reg.getDataHora().before(dataHojeDate)) {
				++contMedioSemana;
				valorMedioSemana += reg.getValor();
			} else if (reg.getDataHora().after(dataPrimeiroDiaSemanaPassada)) {
				++contMedioSemanaPassada;
				valorMedioSemanaPassada += reg.getValor();
			}

			if (reg.getDataHora().after(dataPrimeiroDiaMes)) {
				++contMedioMes;
				valorMedioMes += reg.getValor();
			} else if (reg.getDataHora().after(dataPrimeiroDiaMesPassado)) {
				++contMedioMesPassado;
				valorMedioMesPassado += reg.getValor();
			}
			if (reg.getDataHora().after(dataPrimeiroDiaAno)) {
				++contMedioAnual;
				valorMedioAnual += reg.getValor();
			}
			++contMedioTotal;
			valorMedioTotal += reg.getValor();
			c.moveToNext();
		}
		valorMedioOntem = valorMedioOntem / contMedioOntem;
		valorMedioHoje = valorMedioHoje / contMedioHoje;
		valorMedioSemana = valorMedioSemana / contMedioSemana;
		valorMedioSemanaPassada = valorMedioSemanaPassada
				/ contMedioSemanaPassada;
		valorMedioMes = valorMedioMes / contMedioMes;
		valorMedioMesPassado = valorMedioMesPassado / contMedioMesPassado;
		valorMedioAnual = valorMedioAnual / contMedioAnual;
		valorMedioTotal = valorMedioTotal / contMedioTotal;

		regDAO.close();
	}

	public String getValorMedioHoje() {
		return returnValorMedio(valorMedioHoje);
	}

	public int getContMedioHoje() {
		return contMedioHoje;
	}

	public int getContMedioOntem() {
		return contMedioOntem;
	}

	public String getValorMedioSemana() {
		return returnValorMedio(valorMedioSemana);
	}

	public String getValorMedioSemanaPassada() {
		return returnValorMedio(valorMedioSemanaPassada);
	}

	public String getValorMedioMes() {
		return returnValorMedio(valorMedioMes);
	}

	public String getValorMedioMesPassado() {
		return returnValorMedio(valorMedioMesPassado);
	}

	public String getValorMedioAnual() {
		return returnValorMedio(valorMedioAnual);
	}

	public String getValorMedioTotal() {
		return returnValorMedio(valorMedioTotal);
	}

	public String getValorMedioOntem() {
		return returnValorMedio(valorMedioOntem);
	}

	public String returnValorMedio(Double valor) {
		if (valor == null || valor.isNaN()) {
			return "--";
		}
		DecimalFormat decimal = new DecimalFormat("0.00");
		return decimal.format(valor).toString();
	}

}