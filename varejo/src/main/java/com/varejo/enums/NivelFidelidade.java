package com.varejo.enums;

public enum NivelFidelidade {

    BRONZE(1),
    PRATA(2),
    OURO(3);

    private final int valor;

    NivelFidelidade(int valor) {
        this.valor = valor;
    }

    public static NivelFidelidade fromValor(int valor) {
        for (NivelFidelidade nivel : NivelFidelidade.values()) {
            if (nivel.valor == valor) {
                return nivel;
            }
        }
        return BRONZE; // Retorna BRONZE se o valor não for encontrado
    }
}

