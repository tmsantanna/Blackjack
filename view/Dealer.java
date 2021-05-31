package view;

import model.Mestre;

class Dealer extends Frame {

    Dealer(Mestre mestre) {
        setTitle("Dealer");

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");

        new Mesa(this, mestre);
    }

}
