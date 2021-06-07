/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import model.Evento;
import model.Mestre;

class Dealer extends Frame {

    private final Botao novaRodada;

    Dealer(Mestre mestre, Runnable onSave, Runnable onNovaRodada) {
        setTitle("Dealer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");

        new Cartas(this, mestre);

        Botao terminarJogo = new Botao("TERMINAR JOGO", 50, 600, () -> GUI.mostraTelaInicial(null, null));
        Botao salvarJogo = new Botao("SALVAR JOGO", 694, 600, onSave);

        novaRodada = new Botao("NOVA RODADA", 363, 600, onNovaRodada);
        novaRodada.setVisible(false);

        getContentPane().add(terminarJogo);
        getContentPane().add(novaRodada);
        getContentPane().add(salvarJogo);

        mestre.addObserver(this, this::onFimDeRodada, Evento.Tipo.FIM_DE_RODADA);
        mestre.addObserver(this, this::onClearCartas, Evento.Tipo.CLEAR_CARTAS);
    }

    private void onFimDeRodada(Evento evento) {
        novaRodada.setVisible(true);
    }

    private void onClearCartas(Evento evento) {
        if (evento.jogador == -1) novaRodada.setVisible(false);
    }

}
