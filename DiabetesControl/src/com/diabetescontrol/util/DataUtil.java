package com.diabetescontrol.util;

import java.util.Calendar;
import java.util.Date;

public class DataUtil {
	public static final long millisPorDia = 1000 * 60 * 60 * 24;

	public final Date getDateOntem() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() - millisPorDia);
		return cal.getTime();
	}

	public final Date getPrimeiroDiaSemana() {
		Date primeiroDiaSemana = new Date();
		Calendar cal = Calendar.getInstance();
		int diaDaSemana = cal.get(Calendar.DAY_OF_WEEK);
		long time = cal.getTimeInMillis();
		long primeiroDiaDaSemana = time
				- ((diaDaSemana - Calendar.SUNDAY) * millisPorDia);

		primeiroDiaSemana.setTime(primeiroDiaDaSemana);
		primeiroDiaSemana.setMinutes(0);
		primeiroDiaSemana.setHours(0);
		primeiroDiaSemana.setSeconds(0);

		return primeiroDiaSemana;
	}

	public final Date getPrimeiroDiaSemanaPassada() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getUltimoDiaSemanaPassada());
		long time = cal.getTimeInMillis();
		long primeiroDiaDaSemanaPassada = time - (6 * millisPorDia);
		cal.setTimeInMillis(primeiroDiaDaSemanaPassada);
		return cal.getTime();

	}

	public final Date getUltimoDiaSemanaPassada() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getPrimeiroDiaSemana());
		cal.setTimeInMillis(cal.getTimeInMillis() - millisPorDia);
		return cal.getTime();
	}

	public final Date getPrimeiroDiaMes() {
		Date date = new Date();
		date.setDate(1);
		date.setMinutes(0);
		date.setHours(0);
		date.setSeconds(0);
		return date;
	}

	public final Date getPrimeiroDiaMesPassado() {
		Date date = new Date();
		date.setDate(1);
		if (date.getMonth() == 12) {
			date.setMonth(1);
			date.setYear(date.getYear() - 1);
		} else {
			date.setMonth(date.getMonth() - 1);
		}
		date.setMinutes(0);
		date.setHours(0);
		date.setSeconds(0);
		return date;
	}

	public final Date getPrimeiroDiaAno() {
		Date date = new Date();
		date.setDate(1);
		date.setMonth(Calendar.JANUARY);
		date.setMinutes(0);
		date.setHours(0);
		date.setSeconds(0);
		return date;
	}

	public final Date getUltimoDiaMesPassado() {
		Date date = getPrimeiroDiaMesPassado();
		switch (date.getMonth()) {
		case Calendar.JANUARY:
			date.setDate(31);
			break;
		case Calendar.FEBRUARY:
			date.setDate(28);
			break;
		case Calendar.MARCH:
			date.setDate(31);
			break;
		case Calendar.APRIL:
			date.setDate(30);
			break;
		case Calendar.MAY:
			date.setDate(31);
			break;
		case Calendar.JUNE:
			date.setDate(30);
			break;
		case Calendar.JULY:
			date.setDate(31);
			break;
		case Calendar.AUGUST:
			date.setDate(31);
			break;
		case Calendar.SEPTEMBER:
			date.setDate(30);
			break;
		case Calendar.OCTOBER:
			date.setDate(31);
			break;
		case Calendar.NOVEMBER:
			date.setDate(30);
			break;
		case Calendar.DECEMBER:
			date.setDate(31);
			break;
		}
		return date;
	}
}
