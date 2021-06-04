package controller;

import model.JogadorPassouDe21;
import model.Mestre;
import model.Observable;
import model.Observer;
import view.GUI;

import javax.swing.*;
import java.util.List;

public class Controller implements Observer {

    private Mestre mestre;

    public Controller() {
        GUI.mostraTelaInicial(this::novoJogo, this::carregarJogo);
    }

    private void novoJogo(List<String> nomes) {
        if (nomes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira pelo menos um nome!");
            return;
        }

        GUI.escondeTelaInicial();

        mestre = new Mestre(nomes);
        mestre.addObserver(this);

        GUI.mostraDealer(mestre);
        GUI.mostraJogadores(mestre, nomes, this::onDouble, this::onSplit, this::onClear, this::onDeal,
                this::onHit, this::onStand, this::onSurrender, this::onQuit, this::apostar);

        mestre.dealStart();
    }

    private void carregarJogo() {

    }

    private void onDouble(int jogador) {
        if (mestre.podeDobrarAposta(jogador)) {
            mestre.doubleAposta();
        }
    }

    private void onSplit(int jogador) {
        if (mestre.podeSplit(jogador)) {
            mestre.split();
        }
    }

    private void onClear(int jogador) {
        if (mestre.podeClear()) {
            mestre.clear(jogador);
        }
    }

    private void onDeal(int jogador) {
        if (mestre.podeDeal(jogador)) {
            mestre.deal();
        }
    }

    private void onHit(int jogador) {
        if (mestre.podeHit(jogador)) {
            mestre.hit();
        }
    }

    private void onStand(int jogador) {
        if (mestre.podeJogar(jogador)) {
            mestre.stand();
        }
    }

    private void onSurrender(int jogador) {
        if (mestre.podeSurrender(jogador)) {
            mestre.surrender();
        }
    }

    private void onQuit(int jogador) {
        mestre.quit(jogador);

        if (mestre.pegaNumJogadores() == 0) {
            System.exit(0);
        }

    }

    private void apostar(int jogador, int aposta) {
        if (mestre.podeDiminuirAposta(jogador, -aposta)) {
            mestre.diminuirAposta(-aposta);
        } else if (mestre.podeApostar(jogador, aposta)) {
            mestre.apostar(aposta);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof JogadorPassouDe21) {
            JogadorPassouDe21 obj = (JogadorPassouDe21) arg;
            String nome = mestre.pegaNome(obj.jogador);

            if (!obj.split) {
                JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21!");
            } else if (obj.segundaMao) {
                JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21 na segunda mão!");
            } else {
                JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21 na primeira mão!");
            }
        }
    }
}
