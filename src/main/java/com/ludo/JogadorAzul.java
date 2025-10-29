package com.ludo;

public class JogadorAzul extends Jogador {

    public JogadorAzul(boolean com) {
        super(com);
    }

    protected String definirCor() {
        return "azul";
    }

    protected String definirCorHexadecimal() {
        return "#0000FF";
    }

    protected Peca[] gerarPecas() {
        float[] x_bases = tabuleiro.getXCirculosPequenos(3), y_bases = tabuleiro.getYCirculosPequenos(3);
        Peca[] pecas = new Peca[4];

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaAzul(x_bases[i], y_bases[i], this);

        return pecas;
    }
}
