package com.ludo;

public class JogadorVerde extends Jogador {

    protected String definirCor() {
        return "verde";
    }

    protected String definirCorHexadecimal() {
        return "#00FF00";
    }

    protected Peca[] gerarPecas() {
        float[] x_bases = tabuleiro.getXCirculosPequenos(1), y_bases = tabuleiro.getYCirculosPequenos(1);
        Peca[] pecas = new Peca[4];

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaVerde(x_bases[i], y_bases[i], this);

        return pecas;
    }
}