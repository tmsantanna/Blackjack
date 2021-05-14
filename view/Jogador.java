package view;

import java.awt.*;

public class Jogador extends Frame {

    private Botao doubleB = new Botao(this, 225, 600, 114, 40, "imagens/double.png");
    private Botao splitB = new Botao(this, 344, 600, 114, 40, "imagens/split.png");
    private Botao clearB = new Botao(this, 463, 600, 114, 40, "imagens/clear.png");
    private Botao dealB = new Botao(this, 582, 600, 114, 40, "imagens/deal.png");

    Jogador() {
        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png").add();
        new DrawImagem(this, 0, 561, "imagens/betBKG.png").add();
        new DrawImagem(this, 10, 571, "imagens/betBlackBar.png").add();
        new DrawImagem(this, 10, 621, "imagens/betBlackBar.png").add();
        new DrawImagem(this, 700, 475, "imagens/fichaMesa.png").add();
        new DrawString(this, 30, 594, "Bet 1000.0", Color.white).add();
        new DrawString(this, 30, 644, "Balance 1000.0", Color.white).add();

        doubleB.add();
        splitB.add();
        clearB.add();
        dealB.add();
    }

}
