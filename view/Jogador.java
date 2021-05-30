package view;

import model.Mestre;
import model.Observable;
import model.Observer;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Jogador extends Frame implements Observer {

    private final Botao doubleB, splitB, clearB, dealB, standB, surrenderB, quitB;

    private final int jogador;

    private final DrawString betStr, balanceStr, segundaMao;

    Jogador(Mestre mestre,
            String nome,
            int jogador,
            Consumer<Integer> onDouble,
            Consumer<Integer> onSplit,
            Consumer<Integer> onClear,
            Consumer<Integer> onDeal,
            Consumer<Integer> onStand,
            Consumer<Integer> onSurrender,
            Consumer<Integer> onQuit,
            BiConsumer<Integer, Integer> apostar) {
        setTitle(nome);

        this.jogador = jogador;

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");
        new DrawImagem(this, 0, 561, "imagens/betBKG.png");
        new DrawImagem(this, 10, 571, "imagens/betBlackBar.png");
        new DrawImagem(this, 10, 621, "imagens/betBlackBar.png");
        new DrawImagem(this, 700, 475, "imagens/fichaMesa.png");
        betStr = new DrawString(this, 30, 594, "Bet 0", Color.white);
        balanceStr = new DrawString(this, 30, 644, "Balance 500", Color.white);
        segundaMao = new DrawString(this, 30, 544, "", Color.white);

        doubleB = new Botao(225, 600, 114, 40, "imagens/double.png", () -> {
            onDouble.accept(jogador);
            repaint();
        });

        splitB = new Botao(344, 600, 114, 40, "imagens/split.png", () -> {
            onSplit.accept(jogador);
            repaint();
        });

        clearB = new Botao(463, 600, 114, 40, "imagens/clear.png", () -> {
            onClear.accept(jogador);
            repaint();
        });

        dealB = new Botao(582, 600, 114, 40, "imagens/deal.png", () -> {
            onDeal.accept(jogador);
            repaint();
        });
        
        standB = new Botao(582, 540, 114, 40, "imagens/stand.png", () -> {
            onStand.accept(jogador);
            repaint();
        });
        
        surrenderB = new Botao(463, 540, 114, 40, "imagens/surrender.png", () -> {
            onStand.accept(jogador);
            repaint();
        });
        
        quitB = new Botao(660, 40, 114, 40, "imagens/quit.png", () -> {
            onStand.accept(jogador);
            repaint();
        });

        getContentPane().add(doubleB);
        getContentPane().add(splitB);
        getContentPane().add(clearB);
        getContentPane().add(dealB);
        getContentPane().add(standB);
        getContentPane().add(surrenderB);
        getContentPane().add(quitB);

        new Mesa(this, mestre, jogador);

        new Fichas(this, jogador, apostar);

        mestre.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Mestre m = (Mestre) arg;

        doubleB.setEnabled(m.podeDobrarAposta(jogador));
        splitB.setEnabled(m.podeSplit(jogador));
        clearB.setEnabled(m.podeClear(jogador));
        dealB.setEnabled(m.podeJogar(jogador));
        standB.setEnabled(m.podeJogar(jogador));
        surrenderB.setEnabled(m.podeSurrender(jogador));
        quitB.setEnabled(true);//Quit é sempre verdadeiro

        int aposta = m.pegaAposta(jogador);
        betStr.setTexto("Bet " + aposta);

        int fichas = m.pegaFichas(jogador);
        balanceStr.setTexto("Balance " + fichas);

        if (!m.temDuasMaos(jogador)) {
            segundaMao.setTexto("");
        } else {
            segundaMao.setTexto((m.pegaSegunda(jogador) ? "Segunda" : "Primeira") + " mão");
        }

        repaint();
    }
}
