package br.com.fcoinvest.invest.enums;

import java.util.Random;

public enum TipoOperacao {
	COMPRA,
	VENDA;
	
	public static TipoOperacao random() {
		Random r = new Random();
		TipoOperacao t = TipoOperacao.values()[r.nextInt(2)];
		return t;
	}

}
