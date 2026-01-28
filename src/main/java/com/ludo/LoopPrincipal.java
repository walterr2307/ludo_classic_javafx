package com.ludo;

import javafx.application.Platform;
import javafx.stage.Stage;

public class LoopPrincipal {
    private final Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private boolean com;
    private static int tempo_espera = 500;
    private final int qtd_jogs;
    private int indice;
    private final Stage stage;
    private Peca peca;
    private Jogador jog;
    private final Jogador[] jogs;

    public LoopPrincipal(Jogador[] jogs, Stage stage) {
        this.jogs = jogs;
        this.stage = stage;
        this.qtd_jogs = jogs.length;
        this.indice = qtd_jogs - 1;
        this.jog = jogs[indice];
        this.peca = jog.getPeca(indice);
    }

    public static void setTempoEspera(int tempo_espera) {
        LoopPrincipal.tempo_espera = tempo_espera;
    }

    public static void esperar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void comecarLoop(EncontroPecas encontro) {
        while (!jog.verificarGanhou()) {
            ajustarJogador(tabuleiro.getValorDado());
            esperarGirarDado();

            if (jog.verificarJogadasDisponiveis()) {
                verificarPecaUnica();
                jog.ajustarOrdemVisualizacao(-0.25f);
                ajustarPecaEscolhida();
                jog.ativarBotoes(false);
                esperar(tempo_espera);
                encontro.verificarAtaque(jog, peca);
                jog.ajustarOrdemVisualizacao(0f);
            } else {
                esperar(500);
            }
        }
        esperar(750);
        fecharTela();
    }

    private void verificarPecaUnica() {
        if (!com) {
            if (jog.verificarPecaUnica())
                jog.moverPecaUnica();
            else
                jog.ativarBotoes(true);
        }
    }

    private void ajustarJogador(int valor_dado) {
        if (valor_dado == 6 || peca.getJogarNovamente()) {
            peca.setJogarNovamente(false);
        } else {
            indice = (indice + 1) % qtd_jogs;
            jog = jogs[indice];
        }

        com = jog.getComputador();
    }

    private void esperarGirarDado() {
        tabuleiro.ativarBotaoDado(com, jog.getCorHexadecimal());
        jog.jogarAutomaticamente();

        while (tabuleiro.getBotaoAtivado()) {
            verificarFinalizarPrograma();
            esperar(125);
        }
    }

    private void ajustarPecaEscolhida() {
        while ((peca = jog.getPecaEscolhida()) == null) {
            verificarFinalizarPrograma();
            esperar(125);
        }
    }

    private void verificarFinalizarPrograma() {
        if (!stage.isShowing())
            System.exit(0);
    }

    private void fecharTela() {
        Platform.runLater(() -> {
            stage.close();
            Main.finalizarJogo(jog);
        });
    }
}
