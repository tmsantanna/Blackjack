/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import model.*;
import model.Evento.Tipo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Cartas extends Componente {

    private final List<Integer[]> primeiraMao = new ArrayList<>();
    private final List<Integer[]> segundaMao = new ArrayList<>();

    private int valorPrimeiraMao, valorSegundaMao;

    private boolean esconderPrimeiraCarta;
    private boolean segunda = false;

    private final DrawString valorDaMao = new DrawString(frame, 370, 480, "Valor da Mão: 0", Color.WHITE);
    private final DrawString indicacaoMao = new DrawString(frame, 30, 544, "", Color.white);
    private final Botao primeiraMaoB;
    private final Botao segundaMaoB;

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

    Cartas(Frame frame, Mestre mestre) {
        super(frame);

        mestre.addObserver(this, this::onNovaCarta, Tipo.NOVA_CARTA);
        mestre.addObserver(this, this::onSegundaMao, Tipo.SEGUNDA_MAO);
        mestre.addObserver(this, this::onMostrarCartas, Tipo.MOSTRAR_CARTAS);
        mestre.addObserver(this, this::onClearCartas, Tipo.CLEAR_CARTAS);
        mestre.addObserver(this, this::onSplit, Tipo.SPLIT);
        mestre.addObserver(this, this::onJogadorRemovido, Tipo.JOGADOR_REMOVIDO);
        mestre.addObserver(this, this::onProximoJogador, Tipo.PROXIMO_JOGADOR);

        valorDaMao.setVisible(false);

        primeiraMaoB = new Botao("PRIMEIRA MÃO", 30, 514, 18, () -> mostrarMao(true));
        segundaMaoB = new Botao("SEGUNDA MÃO", 30, 514, 18, () -> mostrarMao(false));

        primeiraMaoB.setVisible(false);
        segundaMaoB.setVisible(false);
        frame.getContentPane().add(primeiraMaoB);
        frame.getContentPane().add(segundaMaoB);

        esconderPrimeiraCarta = cartasDoDealer();
    }

    private static void carregaImagem(String imagem) {
        try {
            imagens.put(imagem, ImageIO.read(new File("imagens/" + imagem + ".gif")));
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: imagens/" + imagem + ".gif");
            System.exit(-1);
        }
    }

    private BufferedImage pegaImagem(int index) {
        Integer[] carta = cartas().get(index);

        int numero = carta[0];
        int naipe = carta[1];
        int deck = carta[2];

        if (index == 0 && esconderPrimeiraCarta) {
            return imagens.get("deck" + (deck + 1)); //se a carta nao for visivel, mostra a parte de tras
        }

        return imagens.get(NUMEROS.charAt(numero - 1) + "" + NAIPES.charAt(naipe - 1));
    }

    private int larguraMesa(int nCartas) {
        return Math.min(nCartas, CARTAS_POR_LINHA) * (LARGURA_CARTA + ESPACO_ENTRE_CARTAS) - ESPACO_ENTRE_CARTAS;
    }

    private boolean cartasDoDealer() {
        return frame instanceof Dealer;
    }

    private int jogador() {
        return cartasDoDealer() ? -1 : ((Jogador) frame).jogador;
    }

    private List<Integer[]> cartas() {
        return segunda ? segundaMao : primeiraMao;
    }

    private void mostrarMao(boolean segunda) {
        primeiraMaoB.setVisible(!segunda);
        segundaMaoB.setVisible(segunda);

        this.segunda = segunda;
        valorDaMao.setTexto("Valor da Mão: " + (segunda ? valorSegundaMao : valorPrimeiraMao));
        frame.repaint();
    }

    @Override
    void paint(Graphics2D g) {
        int nCartas = cartas().size();

        int dx = LARGURA_CARTA + ESPACO_ENTRE_CARTAS, dy = ALTURA_CARTA + ESPACO_ENTRE_CARTAS;

        int x = (GUI.LARGURA - larguraMesa(nCartas)) / 2, y = Y_CARTAS;

        for (int i = 0; i < nCartas; i++) {
            if (i > 0 && i % CARTAS_POR_LINHA == 0) {
                x = (GUI.LARGURA - larguraMesa(nCartas - i)) / 2;
                y -= dy;
            }
            g.drawImage(pegaImagem(i), x, y, null);
            x += dx;
        }
    }

    private void onNovaCarta(Evento evento) {
        if (evento.jogador != jogador()) return;


        if (!cartasDoDealer() || !esconderPrimeiraCarta) {
            int valorMao = evento.mestre.caclHand(jogador(), segunda);

            valorDaMao.setVisible(true);
            valorDaMao.setTexto("Valor da Mão: " + valorMao);

            if (segunda) {
                valorSegundaMao = valorMao;
            } else {
                valorPrimeiraMao = valorMao;
            }
        }


        boolean segunda = (boolean) evento.args[0];
        Integer[] carta = (Integer[]) evento.args[1];

        (segunda ? segundaMao : primeiraMao).add(carta);

        frame.repaint();
    }

    private void onSegundaMao(Evento evento) {
        if (evento.jogador != jogador()) return;

        segunda = true;
        indicacaoMao.setTexto("Segunda mão");

        valorSegundaMao = evento.mestre.caclHand(jogador(), segunda);
        valorDaMao.setTexto("Valor da Mão: " + valorSegundaMao);

        frame.repaint();
    }

    private void onMostrarCartas(Evento evento) {
        if (evento.jogador != jogador()) return;

        esconderPrimeiraCarta = false;
        frame.repaint();
    }

    private void onClearCartas(Evento evento) {
        if (evento.jogador != jogador()) return;

        primeiraMao.clear();
        segundaMao.clear();
        frame.repaint();
        segunda = false;
        esconderPrimeiraCarta = cartasDoDealer();
        valorDaMao.setTexto("");
        primeiraMaoB.setVisible(false);
        segundaMaoB.setVisible(false);
    }

    private void onSplit(Evento evento) {
        if (evento.jogador != jogador()) return;

        segundaMao.add(0, primeiraMao.remove(1));
        indicacaoMao.setTexto("Primeira mão");

        frame.repaint();
    }

    private void onJogadorRemovido(Evento evento) {
        if (evento.jogador == jogador()) {
            evento.mestre.removeObserver(this);
        }
    }

    private void onProximoJogador(Evento evento) {
        if (evento.jogador != -1 && evento.jogador < jogador() || segundaMao.size() == 0) return;

        segundaMaoB.setVisible(true);
        indicacaoMao.setVisible(false);
    }

}
