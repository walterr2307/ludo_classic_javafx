package com.ludo;

public class PecaVermelha extends Peca {

    public PecaVermelha(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected int definirPosicaoInicial() {
        return 2;
    }

    protected int definirPosicaoFinal() {
        return 0;
    }

    protected float[] definirXFinais() {
        return tabuleiro.getXQuadradosFinais(0);
    }

    protected float[] definirYFinais() {
        return tabuleiro.getYQuadradosFinais(0);
    }

    protected String definirCor() {
        return "vermelho";
    }
}
