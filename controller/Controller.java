package controller;

import model.Mestre;
import view.GUI;

import javax.swing.*;
import java.util.List;

public class Controller {

    private static Mestre mestre;

    public static void main(String[] args) {
        GUI.mostraTelaInicial(Controller::novoJogo, Controller::carregarJogo);
    }

    private static void novoJogo(List<String> nomes) {
        if (nomes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira pelo menos um nome!");
            return;
        }

        GUI.escondeTelaInicial();

        mestre = new Mestre(nomes);

        GUI.mostraDealer();
        GUI.mostraJogadores(mestre, nomes,
                Controller::onDouble, Controller::onSplit,
                Controller::onClear, Controller::onDeal,
                Controller::onStand, Controller::apostar);

        mestre.dealStart();
    }

    private static void carregarJogo() {

    }

    private static void onDouble(int jogador) {
        if (mestre.podeJogar(jogador)) {
            mestre.doubleAposta();
        }
    }

    private static void onSplit(int jogador) {
        if (mestre.podeJogar(jogador)) {
            mestre.split();
        }
    }

    private static void onClear(int jogador) {
        if (mestre.podeClear(jogador)) {
            mestre.clear(jogador);
        }
    }

    private static void onDeal(int jogador) {
        boolean segundaMao = mestre.pegaSegunda(jogador);

        if (mestre.podeJogar(jogador)) {
            mestre.hit();
        }

        String nome = mestre.pegaNome(jogador);

        if (!mestre.podeJogar(jogador)) {
            if (segundaMao) {
                JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21 na segunda mão!");
            } else {
                JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21!");
            }
        } else if (mestre.pegaSegunda(jogador) && !segundaMao) {
            JOptionPane.showMessageDialog(null, "Jogador " + nome + " passou de 21 na primeira mão!");
        }
    }

    private static void onStand(int jogador) {
        if (mestre.podeJogar(jogador)) {
            mestre.stand();
        }
    }

    private static void apostar(int jogador, int aposta) {
        if (mestre.podeApostar(jogador, aposta)) {
            mestre.apostar(aposta);
        }
    }

}
