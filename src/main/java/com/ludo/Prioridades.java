package com.ludo;

public class Prioridades {
    private EncontroPecas encontro;
    private static Prioridades instancia;
    private final Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private final int[] casas_seguras = {2, 10, 15, 23, 28, 36, 41, 49};

    private Prioridades() {
    }

    public void setEncontroPecas(EncontroPecas encontro) {
        this.encontro = encontro;
    }

    public static Prioridades instanciar() {
        if (instancia == null)
            instancia = new Prioridades();

        return instancia;
    }

    public int definirPrioridade(int indice, Peca peca) {
        int pos = peca.getPosicao(), pos_final = peca.getPosicaoFinal(), valor_dado = tabuleiro.getValorDado();
        String tipo_pos = peca.getTipoPosicao(), cor = peca.getCor();

        boolean base = tipo_pos.equals("base"),
                quad_principal = tipo_pos.equals("quad_principal"),
                quad_final = tipo_pos.equals("quad_final"),
                sair_casa_segura = verificarSairLugarSeguro(pos),
                entrar_casa_segura = verificarCasaSegura(base, quad_final, pos, pos_final, valor_dado);

        if (verifcarPodeAtacar(entrar_casa_segura, pos, valor_dado, cor))
            return 15;
        else if (base)
            return 14;
        else if (quad_principal && valor_dado == 6 && pos == pos_final)
            return 13;
        else if (quad_final && pos + valor_dado == 5)
            return 12;
        else if (quad_principal && entrar_casa_segura)
            return 11;
        else if (!verificarZonaPerigosa(sair_casa_segura, pos, valor_dado, cor) && quad_principal)
            return 10;
        else if (quad_final)
            return 9;
        else if (!sair_casa_segura)
            return 5 + indice;
        else
            return 1 + indice;
    }

    private boolean verificarCasaSegura(boolean base, boolean quad_final, int pos, int pos_final, int valor_dado) {
        if (base || quad_final)
            return true;

        for (int i = 0; i < valor_dado; i++) {
            if ((pos + i) % 52 == pos_final)
                return true;
        }

        for (int i = 0; i < 8; i++) {
            if ((pos + valor_dado) % 52 == casas_seguras[i])
                return true;
        }

        return false;
    }

    private boolean verificarZonaPerigosa(boolean casa_segura, int pos, int valor_dado, String cor) {
        int inicio = casa_segura ? valor_dado - 5 : 0;

        for (int i = inicio; i < valor_dado; i++) {
            if (i < 0)
                encontro.definirCasa((((pos + i) % 52) + 52) % 52);
            else
                encontro.definirCasa((pos + i) % 52);

            if (encontro.verificarInimigos(cor))
                return true;
        }

        return false;
    }

    private boolean verificarSairLugarSeguro(int pos) {
        for (int i = 0; i < 8; i++) {
            if (pos == casas_seguras[i])
                return true;
        }
        return false;
    }

    private boolean verifcarPodeAtacar(boolean entrar_casa_segura, int pos, int valor_dado, String cor) {
        if (!entrar_casa_segura) {
            encontro.definirCasa((pos + valor_dado) % 52);
            return encontro.verificarPodeAtacar(cor);
        }
        return false;
    }
}
