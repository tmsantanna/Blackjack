/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import model.Mestre;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GUI {

	public static final int LARGURA = 893;
	public static final int ALTURA = 700;

	private static Dealer dealer;
	private static TelaInicial telaInicial;
	private static Jogador[] jogadores;
	private static TelaLoad telaLoad;

	private GUI() {}

	public static void mostraDealer(Mestre mestre, Runnable onSave, Runnable onNovaRodada) {
		if (dealer != null) dealer.dispose();

		dealer = new Dealer(mestre, onSave, onNovaRodada);

		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (tela.width - GUI.LARGURA) / 2;

		dealer.setLocation(x, 0);
		dealer.setVisible(true);
	}

	public static void mostraJogadores(Mestre mestre,
									   Consumer<Integer> onDouble,
									   Consumer<Integer> onSplit,
									   Consumer<Integer> onClear,
									   Consumer<Integer> onDeal,
									   Consumer<Integer> onHit,
									   Consumer<Integer> onStand,
									   Consumer<Integer> onSurrender,
									   Consumer<Integer> onQuit,
									   BiConsumer<Integer, Integer> apostar) {
		if (jogadores != null) {
			for (Jogador jogador : jogadores) {
				if (jogador != null) {
					jogador.dispose();
				}
			}
		}

		jogadores = new Jogador[mestre.pegaNumTotalJogadores()];

		for (int i : mestre.pegaIndiceJogadores()) {
			jogadores[i] = new Jogador(mestre, i, onDouble, onSplit, onClear,
					onDeal, onHit, onStand, onSurrender, onQuit, apostar);
		}

		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

		int x = (tela.width - GUI.LARGURA) / 2;

		if (mestre.pegaNumJogadores() > 1) {
			x = (tela.width - GUI.LARGURA) / (mestre.pegaNumJogadores() - 1);
		}

		int y = 9 * (tela.height - GUI.ALTURA) / 10;

		for (int i : mestre.pegaIndiceJogadores()) {
			jogadores[i].setVisible(true);

			if (mestre.pegaNumJogadores() == 1) {
				jogadores[i].setLocation(x, y);
			} else {
				jogadores[i].setLocation(x * i, y);
			}
		}
	}

	public static void escondeJogador(int jogador) {
		jogadores[jogador].dispose();
	}

	public static void mostraTelaInicial(Consumer<List<String>> novoJogo, Consumer<Mestre> carregarJogo) {
		if (telaInicial == null) {
			telaInicial = new TelaInicial(novoJogo, carregarJogo);
		}

		telaInicial.setVisible(true);

		if (jogadores != null) {
			for (Jogador jogador : jogadores) {
				if (jogador != null) {
					jogador.setVisible(false);
				}
			}
		}

		if (dealer != null) {
			dealer.setVisible(false);
		}

		if (telaLoad != null) {
			telaLoad.setVisible(false);
		}
	}

	public static void escondeTelaInicial() {
		telaInicial.setVisible(false);
	}

	public static void escondeTelaLoad() {
		telaLoad.setVisible(false);
	}

	public static void mostraLoad(Consumer<Mestre> onLoad, Runnable onCancel) {
		if (telaLoad == null) {
			telaLoad = new TelaLoad(onLoad, onCancel);
		}

		telaLoad.setVisible(true);
		telaLoad.atualizaSaves();

		if (telaInicial != null) {
			telaInicial.setVisible(false);
		}
	}

	public static void focarJanela(int jogador) {
		if (jogador == -1) {
			dealer.requestFocus();
		} else if (jogador < jogadores.length && jogadores[jogador] != null) {
			jogadores[jogador].requestFocus();
		}
	}

}
