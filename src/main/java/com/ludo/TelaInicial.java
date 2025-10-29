package com.ludo;

import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class TelaInicial extends Application {

    private Jogador gerarJogador(boolean com, String cor) {
        return switch (cor) {
            case "Vermelho" -> new JogadorVermelho(com);
            case "Verde" -> new JogadorVerde(com);
            case "Amarelo" -> new JogadorAmarelo(com);
            default -> new JogadorAzul(com);
        };
    }

    private void mostrarTelaCores(Stage stage, int qtd_jogs) {
        Label lblTitulo = new Label("Selecione a cor inicial:");

        ComboBox<String> comboCores = new ComboBox<>();
        comboCores.getItems().addAll("Vermelho", "Verde", "Amarelo", "Azul");
        comboCores.setValue("Vermelho");

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(_ -> {
            mostrarTelaTipoJogadores(qtd_jogs, comboCores.getValue(), new Stage());
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

    private void mostrarTelaTipoJogadores(int qtd_jogs, String cor_inicial, Stage stage) {
        int indice;
        Label lblTitulo = new Label("Selecione o tipo de cada jogador:");

        VBox vboxJogadores = new VBox(10);
        vboxJogadores.setAlignment(Pos.CENTER);

        String[] cores = {"Vermelho", "Verde", "Amarelo", "Azul"};
        ArrayList<ComboBox<String>> combos = new ArrayList<>();

        for (int i = 0; i < qtd_jogs; i++)
            combos.add(new ComboBox<>());

        indice = IntStream.range(0, 3).filter(i -> cores[i].equals(cor_inicial)).findFirst().orElse(3);

        for (int i = 0; i < qtd_jogs; i++) {
            String cor = cores[(indice + i * 4 / qtd_jogs) % 4];
            Label lbl = new Label("Jogador " + cor + ":");
            ComboBox<String> combo = new ComboBox<>();
            combo.getItems().addAll("Humano", "Computador");
            combo.setValue("Humano");
            combos.set(i, combo);

            VBox linha = new VBox(5, lbl, combo);
            linha.setAlignment(Pos.CENTER);
            vboxJogadores.getChildren().add(linha);
        }

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(_ -> {
            Jogador[] jogs = new Jogador[qtd_jogs];
            for (int i = 0; i < qtd_jogs; i++) {
                boolean ehComputador = combos.get(i).getValue().equals("Computador");
                jogs[i] = gerarJogador(ehComputador, cores[(indice + i * 4 / qtd_jogs) % 4]);
            }
            Main.iniciarJogo(jogs);
            stage.close();
        });

        VBox layout = new VBox(20, lblTitulo, vboxJogadores, btnConfirmar);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 400);
        stage.setTitle("Tipo de Jogadores:");
        stage.setScene(scene);
        stage.setResizable(false);
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
