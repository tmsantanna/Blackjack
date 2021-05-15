package view;

import model.Carta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;

class Mesa extends Componente {

    private List<Carta> cartas;

    private static final int LARGURA_CARTA = 73;
    private static final int ALTURA_CARTA = 97;
    private static final int ESPACO_ENTRE_CARTAS = 10;
    private static final int CARTAS_POR_LINHA = 6;
    private static final int Y_CARTAS = 350;

    private static final String NAIPES = "dshc";
    private static final String NUMEROS = "a23456789tjqk";

    private static final Map<String, BufferedImage> imagens = new HashMap<>();

    static {
        for (char naipe : NAIPES.toCharArray()) {
            for (char numero : NUMEROS.toCharArray()) {
                carregaImagem(numero + "" + naipe);
            }
        }
        carregaImagem("deck1");
        carregaImagem("deck2");
    }

    Mesa(Frame frame, List<Carta> cartas) {
        super(frame);
        this.cartas = cartas;
    }

    private static void carregaImagem(String imagem) {
        try {
            imagens.put(imagem, ImageIO.read(new File("imagens/" + imagem + ".gif")));
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: imagens/" + imagem + ".gif");
            System.exit(-1);
        }
    }

    private static BufferedImage pegaImagem(Carta c) {
        if (!c.pegaVisibilidade()) {
            return imagens.get("deck" + (c.pegaDeck() + 1));
        }

        char naipe = NAIPES.charAt(c.pegaNaipe() - 1);
        char numero = NUMEROS.charAt(c.pegaNum() - 1);

        return imagens.get(numero + "" + naipe);
    }

    private int larguraMesa(int nCartas) {
        return Math.min(nCartas, CARTAS_POR_LINHA) * (LARGURA_CARTA + ESPACO_ENTRE_CARTAS) - ESPACO_ENTRE_CARTAS;
    }

    @Override
    void paint(Graphics2D g) {
        int nCartas = cartas.size();

        int dx = LARGURA_CARTA + ESPACO_ENTRE_CARTAS, dy = ALTURA_CARTA + ESPACO_ENTRE_CARTAS;

        int x = (GUI.LARGURA - larguraMesa(nCartas)) / 2, y = Y_CARTAS;

        for (int i = 0; i < nCartas; i++) {
            if (i > 0 && i % CARTAS_POR_LINHA == 0) {
                x = (GUI.LARGURA - larguraMesa(nCartas - i)) / 2;
                y -= dy;
            }
            g.drawImage(pegaImagem(cartas.get(i)), x, y, null);
            x += dx;
        }
    }
}