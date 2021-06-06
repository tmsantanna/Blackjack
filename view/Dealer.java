package view;

import model.Mestre;

class Dealer extends Frame {

    Dealer(Mestre mestre) {
        setTitle("Dealer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");

        new Cartas(this, mestre);
    }

}
