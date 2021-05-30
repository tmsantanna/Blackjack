package view;

import model.Mestre;

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

	public static void mostraDealer() {
		if (dealer == null) {
			dealer = new Dealer();
		}
		dealer.setVisible(true);
	}

	public static void escondeDealer() {
		dealer.dispose();
	}

	public static void mostraJogadores(Mestre mestre,
									   List<String> nomes,
									   Consumer<Integer> onDouble,
									   Consumer<Integer> onSplit,
									   Consumer<Integer> onClear,
									   Consumer<Integer> onDeal,
									   Consumer<Integer> onStand,
									   BiConsumer<Integer, Integer> apostar){
		if (jogadores == null) {
			jogadores = new ArrayList<>();
			for (int i = 0; i < nomes.size(); i++) {
				jogadores.add(new Jogador(mestre, nomes.get(i), i, onDouble, onSplit, onClear, onDeal, onStand, apostar));
			}
		}

		for (Jogador jogador : jogadores) {
			jogador.setVisible(true);
		}
	}

	public static void escondeJogadores() {
		for (Jogador jogador : jogadores) {
			jogador.dispose();
		}
	}

	public static void escondeJogador(int jogador) {
		jogadores.get(jogador).dispose();
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

}
