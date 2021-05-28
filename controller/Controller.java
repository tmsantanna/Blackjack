package controller;

import model.Jogador;
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
        mestre.dealStart();

        GUI.mostraDealer();
        GUI.mostraJogadores(mestre, Controller::onDouble, Controller::onSplit, Controller::onClear, Controller::onDeal, Controller::onStand);
    }

    private static void carregarJogo() {

    }

    private static void onDouble(Jogador j) {
    }

    private static void onSplit(Jogador j) {
    }

    private static void onClear(Jogador j) {
    }

    private static void onDeal(Jogador j) {
    }
    private static void onStand(Jogador j) {
    	
    }
}
