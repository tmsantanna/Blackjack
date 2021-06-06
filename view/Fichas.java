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

    private static final int TAMANHO_FICHA = 50;
    private static final Map<Integer, Point> FICHAS = new HashMap<>();
    private static final Map<Integer, BufferedImage> imagens = new HashMap<>();

    private Botao aumentar, diminuir;

    private boolean aumentarAposta = true;
    private boolean visible = true;

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

        aumentar = new Botao(700, 420, "imagens/aumentarAposta.png", this::toggleAposta);
        diminuir = new Botao(700, 420, "imagens/diminuirAposta.png", this::toggleAposta);
        diminuir.setVisible(false);

        frame.getContentPane().add(aumentar);
        frame.getContentPane().add(diminuir);

        frame.getContentPane().addMouseListener(new MouseAdapter() {

            private int fichaPressionada;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) return;

                fichaPressionada = pegaFicha(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    fichaPressionada = -1;
                    return;
                }

                int ficha = pegaFicha(e.getX(), e.getY());

                if (ficha == fichaPressionada && ficha > 0) {
                    apostar.accept(jogador(), aumentarAposta ? ficha : -ficha);
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

    private void toggleAposta() {
        aumentarAposta = !aumentarAposta;

        aumentar.setVisible(aumentarAposta);
        diminuir.setVisible(!aumentarAposta);
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

    public void setVisible(boolean visible) {
        aumentar.setVisible(visible && aumentarAposta);
        diminuir.setVisible(visible && !aumentarAposta);
        this.visible = visible;
        frame.repaint();
    }

    @Override
    void paint(Graphics2D g) {
        if (!visible) return;

        for (Map.Entry<Integer, Point> ficha : FICHAS.entrySet()) {
            g.drawImage(pegaImagem(ficha.getKey()), ficha.getValue().x, ficha.getValue().y, null);
        }
    }
}
