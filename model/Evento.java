/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

public class Evento {

    public final Mestre mestre;

    public final int jogador;

    public final Tipo tipo;

    public final Object[] args; // parâmetros adicionais

    Evento(Mestre mestre, int jogador, Tipo tipo, Object... args) {
        this.mestre = mestre;
        this.jogador = jogador;
        this.tipo = tipo;
        this.args = args;
    }

    public enum Tipo {

        JOGADOR_REMOVIDO,
        PROXIMO_JOGADOR,
        PASSOU_DE_21,
        NOVA_CARTA,
        MOSTRAR_CARTAS,
        MUDANCA_NA_APOSTA,
        SPLIT,
        SEGUNDA_MAO,
        CLEAR_CARTAS,
        BLACKJACK,
        FIM_DE_RODADA,
        REVALIDANDO,
        REVALIDADO
    }

}
