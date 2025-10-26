package com.ludo;

import javafx.application.Platform;
import javafx.stage.Stage;

public class LoopPrincipal {
    private final Tabuleiro tabuleiro = Tabuleiro.instanciar();
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
                jog.ajustarOrdemVisualizacao(-0.25f);
                jog.ativarBotoes(true);
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

    private void ajustarJogador(int valor_dado) {
        if (valor_dado == 6 || peca.getJogarNovamente()) {
            peca.setJogarNovamente(false);
        } else {
            indice = (indice + 1) % qtd_jogs;
            jog = jogs[indice];
        }
    }

    private void esperarGirarDado() {
        tabuleiro.ativarBotaoDado(jog.getCorHexadecimal());

        while (tabuleiro.getBotaoAtivado()) {
            verificarFinalizarPrograma();
            esperar(25);
        }
    }

    private void ajustarPecaEscolhida() {
        while ((peca = jog.getPecaEscolhida()) == null) {
            verificarFinalizarPrograma();
            esperar(25);
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
