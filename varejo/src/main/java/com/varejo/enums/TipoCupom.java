package com.varejo.enums;

//Parte Indiviidual: Marina Mayumi

public enum TipoCupom {

		VALOR_DESCONTO("PC1412412522354"),
		PRIMEIRACOMPRA("PC1412412522354"),
		PORCENTAGEM("PC1412412522354");
	
	private final String nomePorExtenso;

	private TipoCupom(String nomePorExtenso) {
		this.nomePorExtenso = nomePorExtenso;
	}

	public String getNomePorExtenso() {
		return nomePorExtenso;
	}

	
	
	
	
}
