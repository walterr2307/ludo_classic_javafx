package com.ludo;

public class JogadorVermelho extends Jogador {

    protected String definirCor() {
        return "vermelho";
    }

    protected String definirCorHexadecimal() {
        return "#FF0000";
    }

    protected Peca[] gerarPecas() {
        float[] x_bases = tabuleiro.getXCirculosPequenos(0), y_bases = tabuleiro.getYCirculosPequenos(0);
        Peca[] pecas = new Peca[4];

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaVermelha(x_bases[i], y_bases[i], this);

        return pecas;
    }
}
