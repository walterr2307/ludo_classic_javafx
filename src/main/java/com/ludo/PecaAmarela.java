package com.ludo;

public class PecaAmarela extends Peca {

    public PecaAmarela(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected int definirPosicaoInicial() {
        return 28;
    }

    protected int definirPosicaoFinal() {
        return 26;
    }

    protected float[] definirXFinais() {
        return tabuleiro.getXQuadradosFinais(2);
    }

    protected float[] definirYFinais() {
        return tabuleiro.getYQuadradosFinais(2);
    }

    protected String definirCor() {
        return "amarelo";
    }
}
