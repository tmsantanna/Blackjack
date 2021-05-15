package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {

	public static final int LARGURA = 893;
	public static final int ALTURA = 700;

	private static Dealer dealer = new Dealer();
	private static TelaInicial telaInicial = new TelaInicial();
	private static List<Jogador> jogadores = new ArrayList<>();

	static {
		jogadores.add(new Jogador()); // apenas para teste
		jogadores.add(new Jogador()); // apenas para teste
	}

	private GUI() {}

	public static Dealer pegaDealer() {
		return dealer;
	}

	public static List<Jogador> pegaJogadores() {
		return jogadores;
	}

	public static TelaInicial pegaTelaInicial() {
		return telaInicial;
	}

}
