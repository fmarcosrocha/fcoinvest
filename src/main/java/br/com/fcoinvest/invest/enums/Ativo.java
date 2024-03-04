package br.com.fcoinvest.invest.enums;

import java.util.Random;

public enum Ativo {
	BOVA11,
	BPAN4F,
	ITUB4F,
	MGLU3F,
	VVAR3F;
	
	public static Ativo random() {
		Random r = new Random();
		Ativo a = Ativo.values()[r.nextInt(5)];
		return a;
	}
}
