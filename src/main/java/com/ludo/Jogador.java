package com.ludo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Jogador {
    @SuppressWarnings("unchecked")
    protected ArrayList<Peca>[] casas = new ArrayList[5];
    protected ArrayList<Peca> pecas_disponiveis = new ArrayList<>();
    protected Tabuleiro tabuleiro = Tabuleiro.instanciar();
    protected MovimentoInteligente mov_inteligente;
    protected boolean com;
    protected int indice_img = 0;
    protected int[] pos_especificas;
    protected float largura = Main.getLargura();
    protected String cor = definirCor(), cor_hexadecimal = definirCorHexadecimal();
    protected Pane root = Main.getRoot();
    protected Peca[] pecas = gerarPecas();
    protected ImageView[] imgs_chegada = gerarImagensChegada();
    protected Image[] imgs_encontro = gerarImagensEncontro();

    protected abstract String definirCor();

    protected abstract String definirCorHexadecimal();

    protected abstract Peca[] gerarPecas();

    public Jogador(boolean com) {
        this.com = com;
        this.pos_especificas = gerarPosicoesEspecificas();

        for (int i = 0; i < 5; i++)
            casas[i] = new ArrayList<>();
    }

    public void jogarAutomaticamente() {
        if (com)
            mov_inteligente.mover(pos_especificas, pecas);
    }

    protected int[] gerarPosicoesEspecificas() {
        if (com) {
            this.mov_inteligente = MovimentoInteligente.instanciar();
            int pos_inicial = pecas[0].getPosicaoInicial();
            int[] pos_especificas = new int[52];

            for (int i = pos_inicial; i < pos_inicial + 52; i++)
                pos_especificas[i % 52] = i - pos_inicial;

            return pos_especificas;
        } else {
            return null;
        }
    }

    protected Image[] gerarImagensEncontro() {
        Image[] imgs = new Image[4];

        for (int i = 0; i < 4; i++) {
            try {
                imgs[i] = new Image(Objects.requireNonNull(getClass().
                        getResource("/imagens/encontro_" + cor + (i + 1) + ".png")).toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("Erro: Imagem não encontrada no classpath.");
            } catch (Exception e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            }
        }

        return imgs;
    }

    protected ImageView[] gerarImagensChegada() {
        ImageView[] imgs = new ImageView[4];

        try {
            for (int i = 0; i < 4; i++) {
                String caminho = String.format("/imagens/chegada_%s%d.png", cor, i + 1);
                imgs[i] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(caminho)).toExternalForm()));
                imgs[i].setFitWidth(largura / 8f);
                imgs[i].setFitHeight(largura / 8f);
                imgs[i].setLayoutX(largura * 0.4375f);
                imgs[i].setLayoutY(largura * (13f / 48f));
                imgs[i].setVisible(false);
                root.getChildren().add(imgs[i]);
            }
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem 'chegada_xy.png' não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }

        return imgs;
    }

    public void mostrarImagemChegada() {
        imgs_chegada[indice_img].setVisible(true);
        ++indice_img;
    }

    public void ajustarOrdemVisualizacao(float ordem) {
        for (int i = 0; i < 4; i++) {
            pecas[i].getImagem().setViewOrder(ordem);
            pecas[i].getBotao().setViewOrder(ordem);
        }
    }

    public boolean verificarJogadasDisponiveis() {
        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogadaDisponivel())
                return true;
        }
        return false;
    }

    public boolean verificarGanhou() {
        for (int i = 0; i < 4; i++) {
            if (!pecas[i].getTipoPosicao().equals("linha_chegada"))
                return false;
        }
        return true;
    }

    public boolean verificarPecaUnica() {
        pecas_disponiveis.clear();

        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogadaDisponivel())
                pecas_disponiveis.add(pecas[i]);
        }

        for (int i = pecas_disponiveis.size() - 1; i > 0; i--) {
            Peca peca1 = pecas_disponiveis.getFirst(), peca2 = pecas_disponiveis.get(i);

            if (peca1.getPosicao() != peca2.getPosicao() || !peca1.getTipoPosicao().equals(peca2.getTipoPosicao()))
                return false;
        }

        return !pecas_disponiveis.isEmpty();
    }

    public void moverPecaUnica() {
        pecas_disponiveis.getFirst().mover();
    }

    public Peca getPecaEscolhida() {
        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogouAgora()) {
                pecas[i].setJogouAgora(false);
                return pecas[i];
            }
        }
        return null;
    }

    public void ativarBotoes(boolean ativar) {
        for (int i = 0; i < 4; i++)
            pecas[i].ativarBotao(ativar);
    }

    public boolean getComputador() {
        return com;
    }

    public String getCor() {
        return cor;
    }

    public String getCorHexadecimal() {
        return cor_hexadecimal;
    }

    public ArrayList<Peca> getCasa(int i) {
        return casas[i];
    }

    public Image getImgEncontro(int i) {
        return imgs_encontro[i];
    }

    public Peca getPeca(int i) {
        return pecas[i];
    }
}
