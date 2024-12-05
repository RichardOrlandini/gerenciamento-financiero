package com.agenda.financeiro.enums;

/**
 * Enum para representar as regras de cálculo de taxas.
 */
public enum RegraTaxa {
    DIA_0(0, 0, valor -> 3.00 + (valor * 0.025)),
    DIA_1_A_10(1, 10, valor -> 12.00),
    DIA_11_A_20(11, 20, valor -> valor * 0.082),
    DIA_21_A_30(21, 30, valor -> valor * 0.069),
    DIA_31_A_40(31, 40, valor -> valor * 0.047),
    DIA_41_A_50(41, 50, valor -> valor * 0.017);

    private final long inicio;
    private final long fim;
    private final CalculadorTaxa calculador;

    RegraTaxa(long inicio, long fim, CalculadorTaxa calculador) {
        this.inicio = inicio;
        this.fim = fim;
        this.calculador = calculador;
    }

    public boolean aplica(long dias) {
        return dias >= inicio && dias <= fim;
    }

    public double calcular(double valor) {
        return calculador.calcular(valor);
    }

    @FunctionalInterface
    private interface CalculadorTaxa {
        double calcular(double valor);
    }
}
