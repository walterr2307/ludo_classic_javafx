package com.ludo;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main {
    private static final float largura = 800f, altura = largura / 1.5f;
    private static final Pane root = new Pane();

    public static float getLargura() {
        return largura;
    }

    public static float getAltura() {
        return altura;
    }

    public static Pane getRoot() {
        return root;
    }

    public static void iniciarJogo(Jogador[] jogs) {
        Platform.runLater(() -> {
            EncontroPecas encontro = new EncontroPecas();
            Scene scene = new Scene(root, largura, altura);
            Stage stage = new Stage();
            LoopPrincipal loop = new LoopPrincipal(jogs, stage);

            Movimento.instanciar().setEncontroPecas(encontro);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            new Thread(() -> loop.comecarLoop(encontro)).start();
        });
    }

    public static void finalizarJogo(Jogador jog) {
        Stage stage = new Stage();
        FimDeJogo fim_de_jogo = new FimDeJogo(jog.getCor().toUpperCase(), jog.getCorHexadecimal());

        fim_de_jogo.start(stage);
    }

    public static void main(String[] args) {
        TelaInicial.main(args);
    }
}