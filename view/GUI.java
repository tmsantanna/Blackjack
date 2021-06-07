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
	private static List<Jogador> jogadores;

	private GUI() {}

	public static void mostraDealer(Mestre mestre, Runnable onNovaRodada) {
		if (dealer == null) {
			dealer = new Dealer(mestre, onNovaRodada);

			Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (tela.width - GUI.LARGURA) / 2;

			dealer.setLocation(x, 0);
		}
		dealer.setVisible(true);
	}

	public static void mostraJogadores(Mestre mestre,
									   List<String> nomes,
									   Consumer<Integer> onDouble,
									   Consumer<Integer> onSplit,
									   Consumer<Integer> onClear,
									   Consumer<Integer> onDeal,
									   Consumer<Integer> onHit,
									   Consumer<Integer> onStand,
									   Consumer<Integer> onSurrender,
									   Consumer<Integer> onQuit,
									   BiConsumer<Integer, Integer> apostar){
		if (jogadores == null) {
			jogadores = new ArrayList<>();
			for (int i = 0; i < nomes.size(); i++) {
				jogadores.add(new Jogador(mestre, nomes.get(i), i, onDouble, onSplit, onClear,
						onDeal, onHit, onStand, onSurrender, onQuit, apostar));
			}
		}

		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

		int x = (tela.width - GUI.LARGURA) / 2;

		if (jogadores.size() > 1) {
			x = (tela.width - GUI.LARGURA) / (jogadores.size() - 1);
		}

		int y = 9 * (tela.height - GUI.ALTURA) / 10;

		for (int i = jogadores.size() - 1; i >= 0; i--) {
			jogadores.get(i).setVisible(true);

			if (jogadores.size() == 1) {
				jogadores.get(i).setLocation(x, y);
			} else {
				jogadores.get(i).setLocation(x * i, y);
			}
		}
	}

	public static void escondeJogador(int jogador) {
		jogadores.remove(jogador).dispose();
	}

	public static void mostraTelaInicial(Consumer<List<String>> novoJogo, Runnable carregarJogo) {
		if (telaInicial == null) {
			telaInicial = new TelaInicial(novoJogo, carregarJogo);
		}
		telaInicial.setVisible(true);
	}

	public static void escondeTelaInicial() {
		telaInicial.dispose();
	}

	public static void focarJanela(int jogador) {
		if (jogador == -1) {
			dealer.requestFocus();
		} else {
			jogadores.get(jogador).requestFocus();
		}
	}

}
