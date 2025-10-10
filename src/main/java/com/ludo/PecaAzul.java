package com.ludo;

public class PecaAzul extends Peca {

    public PecaAzul(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected int definirPosicaoInicial() {
        return 41;
    }

    protected int definirPosicaoFinal() {
        return 39;
    }

    protected float[] definirXFinais() {
        return tabuleiro.getXQuadradosFinais(3);
    }

    protected float[] definirYFinais() {
        return tabuleiro.getYQuadradosFinais(3);
    }

    protected String definirCor() {
        return "azul";
    }
}
