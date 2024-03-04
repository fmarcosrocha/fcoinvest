package br.com.fcoinvest.invest.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
	
	public static Date addDias(Date data, int d) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(Calendar.DATE, d);
		return c.getTime();
	}

	public static String toRealMask(Double valor) {
		Locale brazil = new Locale("pt","BR");
		DecimalFormatSymbols real = new DecimalFormatSymbols(brazil);
	    DecimalFormat dinheiro = new DecimalFormat("¤ ###,###,##0.00",real);
	    
		return dinheiro.format(valor);
	}
	
	public static String toPorcentMask(Double valor) {
		return NumberFormat.getPercentInstance().format(valor);
	}
	
}
