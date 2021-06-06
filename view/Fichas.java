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
    private static final Map<String, BufferedImage> imagens = new HashMap<>();

    private final Botao aumentar, diminuir;

    private int fichaPressionada = -1;
    private int fichaHover = -1;

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

        aumentar = new Botao("AUMENTAR APOSTA", 710, 420, 18, this::toggleAposta);
        diminuir = new Botao("DIMINUIR APOSTA", 724, 420, 18, this::toggleAposta);
        diminuir.setVisible(false);

        frame.getContentPane().add(aumentar);
        frame.getContentPane().add(diminuir);

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) return;

                int ficha = pegaFicha(e.getX(), e.getY());
                if (ficha != fichaPressionada) {
                    fichaPressionada = ficha;
                    frame.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) return;

                int ficha = pegaFicha(e.getX(), e.getY());

                if (ficha == fichaPressionada && ficha > 0) {
                    apostar.accept(jogador(), aumentarAposta ? ficha : -ficha);
                }


                if (fichaPressionada != -1) {
                    fichaPressionada = -1;
                    frame.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int ficha = pegaFicha(e.getX(), e.getY());

                if (ficha != fichaHover) {
                    fichaHover = ficha;
                    frame.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int ficha = pegaFicha(e.getX(), e.getY());

                if (ficha != fichaHover) {
                    fichaHover = ficha;
                    frame.repaint();
                }
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

        };

        frame.getContentPane().addMouseListener(adapter);
        frame.getContentPane().addMouseMotionListener(adapter);
    }

    private void toggleAposta() {
        aumentarAposta = !aumentarAposta;

        aumentar.setVisible(aumentarAposta);
        diminuir.setVisible(!aumentarAposta);
    }

    private static void carregaImagem(int valor) {
        try {
            BufferedImage imagem = ImageIO.read(new File("imagens/ficha " + valor + "$.png"));
            imagens.put(valor + "", imagem);
            imagens.put("g" + valor, grayScale(imagem));
            imagem = darker(imagem);
            imagens.put("d1" + valor, imagem);
            imagens.put("d2" + valor, darker(imagem));
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: imagens/ficha" + valor + "$.png");
            System.exit(-1);
        }
    }

    private static BufferedImage grayScale(BufferedImage img) {
        BufferedImage grayScale = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        grayScale.getGraphics().drawImage(img, 0, 0, null);
        return grayScale;
    }

    private static BufferedImage darker(BufferedImage img) {
        BufferedImage darker = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int argb = img.getRGB(x, y);
                int a = argb & 0xFF000000, r = argb >> 16 & 0xFF, g = argb >> 8 & 0xFF, b = argb & 0xFF;
                r = (int) (r * 0.7) << 16;
                g = (int) (g * 0.7) << 8;
                b = (int) (b * 0.7);
                darker.setRGB(x, y, a | r | g | b);
            }
        }

        return darker;
    }

    private static BufferedImage pegaImagem(int valor, boolean visible, int darker) {
        if (!visible) return imagens.get("g" + valor);
        else if (darker == 0) return imagens.get(valor + "");

        return imagens.get(String.format("d%d%d", darker, valor));
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
        for (Map.Entry<Integer, Point> ficha : FICHAS.entrySet()) {
            int valor = ficha.getKey();
            int darker = 0;

            if (fichaPressionada == valor) darker = 2;
            else if (fichaHover == valor && fichaPressionada == -1) darker = 1;

            g.drawImage(pegaImagem(valor, visible, darker), ficha.getValue().x, ficha.getValue().y, null);
        }
    }
}
