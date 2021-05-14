package view;

import java.awt.*;

public class Jogador extends Frame {

    private Botao doubleB = new Botao(225, 600, 114, 40, "imagens/double.png");
    private Botao splitB = new Botao(344, 600, 114, 40, "imagens/split.png");
    private Botao clearB = new Botao(463, 600, 114, 40, "imagens/clear.png");
    private Botao dealB = new Botao(582, 600, 114, 40, "imagens/deal.png");

    private DrawString betString = new DrawString(this, 30, 594, "Bet 0.0", Color.white);
    private DrawString balanceString = new DrawString(this, 30, 644, "Balance 500.0", Color.white);

    Jogador() {
        setTitle("Jogador");

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png").add();
        new DrawImagem(this, 0, 561, "imagens/betBKG.png").add();
        new DrawImagem(this, 10, 571, "imagens/betBlackBar.png").add();
        new DrawImagem(this, 10, 621, "imagens/betBlackBar.png").add();
        new DrawImagem(this, 700, 475, "imagens/fichaMesa.png").add();
        betString.add();
        balanceString.add();

        getContentPane().add(doubleB);
        getContentPane().add(splitB);
        getContentPane().add(clearB);
        getContentPane().add(dealB);
    }

}
