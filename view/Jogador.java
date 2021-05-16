package view;

import java.awt.*;
import java.util.function.Consumer;

class Jogador extends Frame {

    Jogador(model.Jogador jogador,
            Consumer<model.Jogador> onDouble,
            Consumer<model.Jogador> onSplit,
            Consumer<model.Jogador> onClear,
            Consumer<model.Jogador> onDeal) {
        setTitle(jogador.pegaNome());

        new DrawImagem(this, 0, 0, "imagens/blackjackBKG.png");
        new DrawImagem(this, 0, 561, "imagens/betBKG.png");
        new DrawImagem(this, 10, 571, "imagens/betBlackBar.png");
        new DrawImagem(this, 10, 621, "imagens/betBlackBar.png");
        new DrawImagem(this, 700, 475, "imagens/fichaMesa.png");
        new DrawString(this, 30, 594, "Bet 0.0", Color.white);
        new DrawString(this, 30, 644, "Balance 500.0", Color.white);

        getContentPane().add(new Botao(225, 600, 114, 40, "imagens/double.png", () -> {
            onDouble.accept(jogador);
            repaint();
        }));

        getContentPane().add(new Botao(344, 600, 114, 40, "imagens/split.png", () -> {
            onSplit.accept(jogador);
            repaint();
        }));

        getContentPane().add(new Botao(463, 600, 114, 40, "imagens/clear.png", () -> {
            onClear.accept(jogador);
            repaint();
        }));

        getContentPane().add(new Botao(582, 600, 114, 40, "imagens/deal.png", () -> {
            onDeal.accept(jogador);
            repaint();
        }));

        new Mesa(this, jogador.pegaHand());
    }

}
