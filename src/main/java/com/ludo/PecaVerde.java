package com.ludo;

public class PecaVerde extends Peca {

    public PecaVerde(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected int definirPosicaoInicial() {
        return 15;
    }

    protected int definirPosicaoFinal() {
        return 13;
    }

    protected float[] definirXFinais() {
        return tabuleiro.getXQuadradosFinais(1);
    }

    protected float[] definirYFinais() {
        return tabuleiro.getYQuadradosFinais(1);
    }

    protected String definirCor() {
        return "verde";
    }
}
