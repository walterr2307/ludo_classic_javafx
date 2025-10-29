package com.ludo;

import java.util.ArrayList;

public class EncontroPecas {
    @SuppressWarnings("unchecked")
    private final ArrayList<Peca>[] casas_principais = new ArrayList[52];
    private ArrayList<Peca> casa;
    private final Movimento mov = Movimento.instanciar();

    public EncontroPecas() {
        for (int i = 0; i < 52; i++)
            casas_principais[i] = new ArrayList<>();
    }

    private void adicionarPeca(Peca peca) {
        casa.add(peca);
        ajustarImagem();
    }

    public void removerPeca(Jogador jog, Peca peca) {
        definirCasa(jog, peca);
        peca.trocarImagem(true, 0);
        casa.remove(peca);
        ajustarImagem();
    }

    private void definirCasa(Jogador jog, Peca peca) {
        if (peca.getTipoPosicao().equals("quad_final"))
            casa = jog.getCasa(peca.getPosicao());
        else
            casa = casas_principais[peca.getPosicao()];
    }

    public void definirCasa(int pos) {
        casa = casas_principais[pos];
    }

    public void verificarAtaque(Jogador jog, Peca peca) {
        if (!peca.getTipoPosicao().equals("linha_chegada")) {
            definirCasa(jog, peca);

            if (verificarZonaAtaque(peca.getPosicao(), peca.getTipoPosicao(), peca.getCor()))
                comecarAtaque(peca, casa.getFirst());
            else
                adicionarPeca(peca);
        }
    }

    private boolean verificarZonaAtaque(int pos, String tipo_pos, String cor) {
        int[] casas_seguras = {2, 10, 15, 23, 28, 36, 41, 49};

        if (tipo_pos.equals("quad_final"))
            return false;

        for (int i = 0; i < 8; i++) {
            if (pos == casas_seguras[i])
                return false;
        }

        return verificarPodeAtacar(cor);
    }

    public boolean verificarPodeAtacar(String cor) {
        int qtd_pecas_amigas = 0, qtd_pecas_inimigas = 0;

        for (Peca peca : casa) {
            if (peca.getCor().equals(cor))
                ++qtd_pecas_amigas;
            else
                ++qtd_pecas_inimigas;
        }

        return qtd_pecas_amigas == 0 && qtd_pecas_inimigas == 1;
    }

    public boolean verificarInimigos(String cor) {
        for (Peca peca : casa) {
            if (!peca.getCor().equals(cor))
                return true;
        }
        return false;
    }

    private void ajustarImagem() {
        int[] num_pecas = {0, 0, 0, 0};

        if (casa.size() == 1) {
            casa.getFirst().trocarImagem(true, 0);
        } else {
            for (Peca peca : casa) {
                switch (peca.getCor()) {
                    case "vermelho":
                        peca.trocarImagem(false, num_pecas[0]);
                        ++num_pecas[0];
                        break;
                    case "verde":
                        peca.trocarImagem(false, num_pecas[1]);
                        ++num_pecas[1];
                        break;
                    case "amarelo":
                        peca.trocarImagem(false, num_pecas[2]);
                        ++num_pecas[2];
                        break;
                    default:
                        peca.trocarImagem(false, num_pecas[3]);
                        ++num_pecas[3];
                }
            }
        }
    }

    private void comecarAtaque(Peca peca, Peca peca_atacada) {
        peca.setJogarNovamente(true);
        casa.remove(peca_atacada);
        adicionarPeca(peca);
        mov.moverSemPulo(peca_atacada);
    }
}
