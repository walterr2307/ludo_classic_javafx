package com.ludo;


public class JogadorAmarelo extends Jogador {

    protected String definirCor() {
        return "amarelo";
    }

    protected String definirCorHexadecimal() {
        return "#FFFF00";
    }

    protected Peca[] gerarPecas() {
        float[] x_bases = tabuleiro.getXCirculosPequenos(2), y_bases = tabuleiro.getYCirculosPequenos(2);
        Peca[] pecas = new Peca[4];

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaAmarela(x_bases[i], y_bases[i], this);

        return pecas;
    }
}
