package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

class Fichas extends Componente {

    private final BiConsumer<Integer, Integer> apostar;

    private static final int TAMANHO_FICHA = 50;
    private static final Map<Integer, Point> FICHAS = new HashMap<>();
    private static final Map<Integer, BufferedImage> imagens = new HashMap<>();

    static {
        carregaImagem(1);
        carregaImagem(5);
        carregaImagem(10);
        carregaImagem(20);
        carregaImagem(50);
        carregaImagem(100);

        FICHAS.put(1, new Point(750, 480));
        FICHAS.put(5, new Point(815, 480));
        FICHAS.put(10, new Point(750, 540));
        FICHAS.put(20, new Point(815, 540));
        FICHAS.put(50, new Point(750, 600));
        FICHAS.put(100, new Point(815, 600));
    }

    Fichas(Jogador frame, BiConsumer<Integer, Integer> apostar) {
        super(frame);

        this.apostar = apostar;

        frame.getContentPane().addMouseListener(new MouseAdapter() {

            private int fichaPressionada;

            @Override
            public void mousePressed(MouseEvent e) {
                fichaPressionada = pegaFicha(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int ficha = pegaFicha(e.getX(), e.getY());

                if (ficha == fichaPressionada && ficha > 0) {
                    apostar.accept(jogador(), ficha);
                }

                fichaPressionada = -1;
            }

            private int pegaFicha(int x, int y) {
                for (Map.Entry<Integer, Point> ficha : FICHAS.entrySet()) {
                    int fx = ficha.getValue().x, fy = ficha.getValue().y;

                    if (x >= fx && x < fx + TAMANHO_FICHA && y >= fy && y < fy + TAMANHO_FICHA) {
                        return ficha.getKey();
                    }
                }

                return -1;
            }

        });
    }

    private static void carregaImagem(int valor) {
        try {
            imagens.put(valor, ImageIO.read(new File("imagens/ficha " + valor + "$.png")));
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: imagens/ficha" + valor + "$.png");
            System.exit(-1);
        }
    }

    private static BufferedImage pegaImagem(int valor) {
        return imagens.get(valor);
    }

    private int jogador() {
        return frame instanceof Jogador ? ((Jogador) frame).jogador : -1;
    }

    @Override
    void paint(Graphics2D g) {
        for (Map.Entry<Integer, Point> ficha : FICHAS.entrySet()) {
            g.drawImage(pegaImagem(ficha.getKey()), ficha.getValue().x, ficha.getValue().y, null);
        }
    }
}
