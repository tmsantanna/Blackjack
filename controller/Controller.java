/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package controller;

import model.Evento;
import model.Mestre;
import view.GUI;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Controller {

    private static final Controller controller = new Controller();

    private Mestre mestre;

    private boolean revalidando = false;

    private Controller() {
    }

    public static void comecarJogo() {
        GUI.mostraTelaInicial(controller::novoJogo, controller::onLoad);
    }

    private void novoJogo(List<String> nomes) {
        if (nomes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira pelo menos um nome!");
            return;
        }

        GUI.escondeTelaInicial();

        mestre = new Mestre(nomes);
        carregarPartida(mestre);
        mestre.comecarRodada();
    }

    private void onLoad(Mestre mestre) {
        carregarPartida(mestre);
        mestre.revalidar();
    }

    private void carregarPartida(Mestre mestre) {
        this.mestre = mestre;
        mestre.removeObservers();

        mestre.addObserver(null, this::onPassouDe21, Evento.Tipo.PASSOU_DE_21);
        mestre.addObserver(null, this::onProximoJogador, Evento.Tipo.PROXIMO_JOGADOR);
        mestre.addObserver(null, this::onBlackjack, Evento.Tipo.BLACKJACK);
        mestre.addObserver(null, this::onRevalidate, Evento.Tipo.REVALIDADO, Evento.Tipo.REVALIDANDO);

        GUI.mostraDealer(mestre, this::onSave, this::onNovaRodada);
        GUI.mostraJogadores(mestre, this::onDouble, this::onSplit, this::onClear, this::onDeal,
                this::onHit, this::onStand, this::onSurrender, this::onQuit, this::apostar);
    }

    private void onDouble(int jogador) {
        if (mestre.podeDobrarAposta(jogador)) {
            mestre.doubleAposta();
        }
    }

    private void onSplit(int jogador) {
        if (mestre.podeSplit(jogador)) {
            mestre.split();
        }
    }

    private void onClear(int jogador) {
        if (mestre.podeClear()) {
            mestre.clear(jogador);
        }
    }

    private void onDeal(int jogador) {
        if (mestre.podeDeal(jogador)) {
            mestre.deal();
        }
    }

    private void onHit(int jogador) {
        if (mestre.podeHit(jogador)) {
            mestre.hit();
        }
    }

    private void onStand(int jogador) {
        if (mestre.podeJogar(jogador)) {
            mestre.stand();
        }
    }

    private void onSurrender(int jogador) {
        if (mestre.podeSurrender(jogador)) {
            mestre.surrender();
        }
    }

    private void onQuit(int jogador) {
        mestre.quit(jogador);

        if (mestre.pegaNumJogadores() == 0) {
            System.exit(0);
        }

    }

    private void apostar(int jogador, int aposta) {
        if (mestre.podeDiminuirAposta(jogador, -aposta)) {
            mestre.diminuirAposta(-aposta);
        } else if (mestre.podeApostar(jogador, aposta)) {
            mestre.apostar(aposta);
        }
    }

    private void onPassouDe21(Evento evento) {
        if (mestre.pegaNumJogadores() == 0) return;

        int jogador = mestre.pegaVez();

        if (jogador == -1) {
            mostrarMensagem("Dealer passou de 21!");
            return;
        }

        String mensagem = "Jogador " + mestre.pegaNome(jogador) + " passou de 21";

        if (!mestre.temDuasMaos(jogador)) {
            mostrarMensagem(mensagem + "!");
        } else if (mestre.pegaSegunda(jogador)) {
            mostrarMensagem(mensagem + " na segunda mão!");
        } else {
            mostrarMensagem(mensagem + " na primeira mão!");
        }
    }

    private void onProximoJogador(Evento evento) {
        if (evento.args.length < 1 || mestre.pegaNumJogadores() == 0) return;

        int jogador = (int) evento.args[0];

        if (mestre.pegaNumJogadores() > 1 || jogador == -1) {
            mostrarMensagem("Vez de " + mestre.pegaNome(jogador));
        }

        GUI.focarJanela(jogador);
    }

    private void onBlackjack(Evento evento) {
        String nome = mestre.pegaNome(evento.jogador);

        mostrarMensagem(nome + " tem um blackjack!");
    }

    private void onNovaRodada() {
        if (!mestre.comecarRodada()) {
            mostrarMensagem("Todos os jogadores devem dar CLEAR ou QUIT!");
        }
    }

    private void onSave() {
        String nome = escolheNomeArquivo();

        if (nome == null) return;

        File root = new File(Mestre.SAVE_PATH);
        root.mkdirs();

        String arquivo = new File(root, nome + ".bjk").toString();

        //TODO salvar arquivo

        mostrarMensagem("Jogo salvo!");
    }

    private void onRevalidate(Evento evento) {
        revalidando = evento.tipo == Evento.Tipo.REVALIDANDO;
    }

    private String escolheNomeArquivo() {
        String nome = null;

        do {
            if (nome != null && !nome.trim().isEmpty() ) {
                int result = JOptionPane.showConfirmDialog(null, "Já existe um jogo com esse nome!\nDeseja substituí-lo?");

                switch (result) {
                    case JOptionPane.YES_OPTION:
                        return nome;
                    case JOptionPane.CANCEL_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                        return null;
                }
            }

            nome = JOptionPane.showInputDialog(null, "Dê um nome a esse jogo.");

        } while (new File(Mestre.SAVE_PATH, nome + ".bj").exists() || nome.trim().isEmpty());

        return nome;
    }

    private void mostrarMensagem(String mensagem) {
        if (revalidando) return;

        JOptionPane.showMessageDialog(null, mensagem);
    }

}
