package view;

import model.Evento;
import model.Evento.Tipo;
import model.Mestre;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Jogador extends Frame {

    private final Botao doubleB, splitB, clearB, dealB, standB, surrenderB, quitB, hitB;

    int jogador;

    private final DrawString betStr, balanceStr, indicacaoMao;

    private final Fichas fichas;

    Jogador(Mestre mestre,
            String nome,
            int jogador,
            Consumer<Integer> onDouble,
            Consumer<Integer> onSplit,
            Consumer<Integer> onClear,
            Consumer<Integer> onDeal,
            Consumer<Integer> onHit,
            Consumer<Integer> onStand,
            Consumer<Integer> onSurrender,
            Consumer<Integer> onQuit,
            BiConsumer<Integer, Integer> apostar) {
        setTitle(nome);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                onQuit.accept(Jogador.this.jogador);
            }

        });

        this.jogador = jogador;

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");
        new DrawImagem(this, 0, 561, "imagens/betBKG.png");
        new DrawImagem(this, 10, 571, "imagens/betBlackBar.png");
        new DrawImagem(this, 10, 621, "imagens/betBlackBar.png");
        new DrawImagem(this, 700, 475, "imagens/fichaMesa.png");
        betStr = new DrawString(this, 30, 594, "Bet 0.0", Color.white);
        balanceStr = new DrawString(this, 30, 644, "Balance 500.0", Color.white);
        indicacaoMao = new DrawString(this, 30, 544, "", Color.white);

        doubleB = new Botao(225, 600, "imagens/double.png", () -> {
            onDouble.accept(this.jogador);
            repaint();
        });

        splitB = new Botao(344, 600, "imagens/split.png", () -> {
            onSplit.accept(this.jogador);
            repaint();
        });

        clearB = new Botao(582, 600, "imagens/clear.png", () -> {
            onClear.accept(this.jogador);
            repaint();
        });

        dealB = new Botao(582, 600, "imagens/deal.png", () -> {
            onDeal.accept(this.jogador);
            repaint();
        });

        hitB = new Botao(582, 600, "imagens/hit.png", () -> {
            onHit.accept(this.jogador);
            repaint();
        });

        standB = new Botao(463, 600, "imagens/stand.png", () -> {
            onStand.accept(this.jogador);
            repaint();
        });

        surrenderB = new Botao(582, 540, "imagens/surrender.png", () -> {
            onSurrender.accept(this.jogador);
            repaint();
        });

        quitB = new Botao(660, 40, "imagens/quit.png", () -> {
            onQuit.accept(this.jogador);
            repaint();
        });

        getContentPane().add(doubleB);
        getContentPane().add(splitB);
        getContentPane().add(clearB);
        getContentPane().add(dealB);
        getContentPane().add(hitB);
        getContentPane().add(standB);
        getContentPane().add(surrenderB);
        getContentPane().add(quitB);

        new Cartas(this, mestre);

        fichas = new Fichas(this, apostar);

        mestre.addObserver(this, this::onJogadorRemovido, Tipo.JOGADOR_REMOVIDO);
        mestre.addObserver(this, this::onMudancaNaAposta, Tipo.MUDANCA_NA_APOSTA);
        mestre.addObserver(this, this::onSplit, Tipo.SPLIT);
        mestre.addObserver(this, this::onSegundaMao, Tipo.SEGUNDA_MAO);
        mestre.addObserver(this, this::onJogada, Tipo.PASSOU_DE_21, Tipo.NOVA_CARTA, Tipo.PROXIMO_JOGADOR);
    }

    private void onJogadorRemovido(Evento evento) {
        if (evento.jogador < jogador) {
            jogador--;  //atualiza o indice do jogador quando um é removido
            repaint();
        } else if (evento.jogador == jogador) {
            GUI.escondeJogador(jogador);
            evento.mestre.removeObserver(this);
        }
    }

    private void onMudancaNaAposta(Evento evento) {
        if (jogador != evento.jogador) return;

        float aposta = (float) evento.args[0];
        betStr.setTexto("Bet " + aposta);

        float fichas = (float) evento.args[1];
        balanceStr.setTexto("Balance " + fichas);

        dealB.setEnabled(evento.mestre.podeDeal(jogador));
        repaint();
    }

    private void onSplit(Evento evento) {
        if (jogador != evento.jogador) return;

        indicacaoMao.setTexto("Primeira mão");
        repaint();
    }

    private void onSegundaMao(Evento evento) {
        if (jogador != evento.jogador) return;

        indicacaoMao.setTexto("Segunda mão");
        repaint();
    }

    private void onJogada(Evento evento) {
        Mestre m = evento.mestre;

        doubleB.setVisible(m.podeDobrarAposta(jogador));
        splitB.setVisible(m.podeSplit(jogador));
        clearB.setVisible(m.podeClear());
        dealB.setEnabled(m.podeDeal(jogador));
        dealB.setVisible(m.podeApostar(jogador));
        hitB.setVisible(m.podeHit(jogador));
        standB.setVisible(m.podeStand(jogador));
        surrenderB.setVisible(m.podeSurrender(jogador));
        quitB.setEnabled(true);//Quit é sempre verdadeiro

        fichas.setVisible(m.podeApostar(jogador));

        repaint();
    }
}
