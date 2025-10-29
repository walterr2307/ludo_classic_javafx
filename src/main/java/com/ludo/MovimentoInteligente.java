package com.ludo;

import java.util.ArrayList;

public class MovimentoInteligente {
    private static MovimentoInteligente instancia;
    private final ArrayList<Peca> pecas_disponiveis = new ArrayList<>();
    private final Prioridades prioridades = Prioridades.instanciar();
    private final Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private int[] pos_especificas;
    private Peca[] pecas;

    private MovimentoInteligente() {
    }

    public static MovimentoInteligente instanciar() {
        if (instancia == null)
            instancia = new MovimentoInteligente();

        return instancia;
    }

    public void mover(int[] pos_especificas, Peca[] pecas) {
        int maior_prioridade = -1;

        this.pos_especificas = pos_especificas;
        this.pecas = pecas;

        tabuleiro.girarDado(true);
        reajustarPecas();

        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogadaDisponivel())
                maior_prioridade = definirPrioridade(i, maior_prioridade, pecas[i]);
            else
                pecas[i].setPrioridade(-1);
        }

        moverPeca(maior_prioridade);
    }

    private void reajustarPecas() {
        boolean troca;
        int[] posicoes = new int[4];

        for (int i = 0; i < 4; i++)
            posicoes[i] = pos_especificas[pecas[i].getPosicao()];

        do {
            troca = false;

            for (int i = 3; i > 0; i--) {
                if (posicoes[i] > posicoes[i - 1]) {
                    int copia_pos = posicoes[i];
                    posicoes[i] = posicoes[i - 1];
                    posicoes[i - 1] = copia_pos;

                    Peca copia_peca = pecas[i];
                    pecas[i] = pecas[i - 1];
                    pecas[i - 1] = copia_peca;

                    troca = true;
                }
            }
        } while (troca);
    }

    private int definirPrioridade(int indice, int maior_prioridade, Peca peca) {
        int prioridade = prioridades.definirPrioridade(indice, peca);

        peca.setPrioridade(prioridade);
        pecas_disponiveis.add(peca);

        if (prioridade > maior_prioridade)
            maior_prioridade = prioridade;

        return maior_prioridade;
    }

    protected void moverPeca(int maior_prioridade) {
        for (Peca peca : pecas_disponiveis) {
            if (peca.getPrioridade() == maior_prioridade) {
                peca.mover();
                break;
            }
        }
        pecas_disponiveis.clear();
    }
}
