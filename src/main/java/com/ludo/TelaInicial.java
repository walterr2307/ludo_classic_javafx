package com.ludo;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;

public class TelaInicial extends Application {

    private Jogador gerarJogador(String cor) {
        return switch (cor) {
            case "Vermelho" -> new JogadorVermelho();
            case "Verde" -> new JogadorVerde();
            case "Amarelo" -> new JogadorAmarelo();
            default -> new JogadorAzul();
        };
    }

    private Jogador[] gerarJogadores(int qtd_jogs, String cor_inicial) {
        int indice = 3;
        Jogador[] jogs = new Jogador[qtd_jogs];
        String[] cores = {"Vermelho", "Verde", "Amarelo", "Azul"};

        for (int i = 0; i < 3; i++) {
            if (cor_inicial.equals(cores[i])) {
                indice = i;
                break;
            }
        }

        for (int i = 0; i < qtd_jogs; i++)
            jogs[i] = gerarJogador(cores[(indice + i * 4 / qtd_jogs) % 4]);

        return jogs;
    }

    private void mostrarTelaCores(Stage stage, int qtd_jogs) {
        Label lblTitulo = new Label("Selecione a cor inicial:");

        ComboBox<String> comboCores = new ComboBox<>();
        comboCores.getItems().addAll("Vermelho", "Verde", "Amarelo", "Azul");
        comboCores.setValue("Vermelho");

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(_ -> {
            Main.iniciarJogo(gerarJogadores(qtd_jogs, comboCores.getValue()));
            stage.close();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(lblTitulo, comboCores, btnConfirmar);

        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Cor inicial:");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void start(Stage stage) {
        Button btn2Jogadores = new Button("2 Jogadores"), btn4Jogadores = new Button("4 Jogadores");

        btn2Jogadores.setOnAction(_ -> {
            mostrarTelaCores(new Stage(), 2);
            stage.close();
        });

        btn4Jogadores.setOnAction(_ -> {
            mostrarTelaCores(new Stage(), 4);
            stage.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(btn2Jogadores, btn4Jogadores);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Número de jogadores:");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
