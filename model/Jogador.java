package model;

import java.util.ArrayList;
import java.util.List;

class Jogador {
	String nome;
	private List<Carta> hand = new ArrayList<>();//Carta que o jogador tem é mão
	private List<Carta> splitHand = new ArrayList<>();//Carta que o jogador tem é mão
	private boolean segunda = false;
	private boolean flagSplitAs = false;
	private boolean apostado = false;
	private float fichas = 500;
	private float aposta = 0;

	Jogador(String n) {
		nome = n;
	}

	void dealCarta(Carta novaCarta) {//Da uma carta para a mão do jogador
		if (segunda) {
			splitHand.add(novaCarta);
		} else {
			hand.add(novaCarta);
		}
		return;
	}

	void dealCartaMao(Carta novaCarta, boolean segunda) {
		(segunda ? splitHand : hand).add(novaCarta);
	}

	void clearHand() {//Remove todas as cartas da mão
		hand.clear();//Clear
		splitHand.clear();//Clear
		flagSplitAs = false;
		segunda = false;
		apostado = false;
		aposta = 0;
	}

	public int caclHand() {//Calcula o melhor valor da mão considerando o valor do Às
		int resultado = 0, ases = 0;
		List<Carta> mao;
		if (segunda) {
			mao = splitHand;
		} else {
			mao = hand;
		}

		for (Carta carta : mao) {
			resultado += carta.pegaValor();//Adiciona o Valor

			if (carta.pegaValor() == 1) {
				ases++;//Conta Ases
			}
		}

		if (ases > 0) {
			if (resultado + 10 <= 21) {
				resultado += 10;
			}
		}
		return resultado;
	}

	public void split() {
		if (hand.get(0).pegaNum() == 1) {
			flagarSplitAs(true);
		}

		splitHand.add(hand.get(1));//Adiciona a carta para split Hand
		hand.remove(1);//Remove a carta da mão adicional

		fichas -= aposta;
		aposta += aposta;

		return;
	}

	public void trocaMao() {//Troca para usar a segunda mão
		if (segunda) {
			segunda = false;
		} else {
			segunda = true;
		}
		return;
	}

	boolean apostar(float valor) {
		if (valor < 0 || valor > fichas || apostado) return false;

		aposta += valor;
		fichas -= valor;

		return true;
	}

	boolean dobrarAposta() {
		if (aposta > fichas || !apostado) return false;

		fichas -= aposta;
		aposta += aposta;

		return true;
	}

	boolean diminuirAposta(float valor) {
		if (valor < 0 || valor > aposta || apostado) return false;

		aposta -= valor;
		fichas += valor;

		return true;
	}

	void receber(float valor) {
		fichas += valor;
		return;
	}

	void deal() {
		apostado = true;
	}

	boolean podeDeal() {
		return !apostado && aposta >= 20 && aposta <= 100;
	}

	boolean podeHit() {
		return apostado;
	}

	boolean podeStand() {
		return apostado;
	}

	boolean podeApostar() {
		return !apostado;
	}

	boolean podeApostar(float valor) {
		return !apostado && valor >= 0 && valor <= fichas && valor + aposta <= 100;
	}

	boolean podeDiminuirAposta(int valor) {
		return !apostado && valor >= 0 && valor <= aposta;
	}

	boolean podeDobrarAposta() {
		return aposta <= 50 && aposta <= fichas && hand.size() == 2 && !segunda;
	}

	boolean podeSplit() {
		if (splitHand.size() > 0) {//Se já deu split
			return false;
		}

		if (hand.size() != 2) {//Se a mão é maior que a inicial, retorna falso
			return false;
		}

		if (aposta > fichas || aposta > 50) {//Verifica se a aposta pode ser feita
			return false;
		}

		if (hand.get(0).pegaValor() != hand.get(1).pegaValor()) {//Verifica se as cartas da mao são iguais
			return false;
		}

		return true;
	}

	boolean podeSurrender() {
		return hand.size() == 2 && !temDuasMaos();
	}

	public List<Carta> pegaHand(){
		return hand;
	}

	public void flagarSplitAs(boolean fato) {//Muda o flagSplitAs
		flagSplitAs = fato;
		return;
	}

	public String pegaNome() {
		return nome;
	}

	public float pegaFichas() {
		return fichas;
	}

	public float pegaAposta() {
		return aposta;
	}

	public boolean pegaSegunda() {
		return segunda;
	}

	public boolean pegaFlagSplitAs() {
		return flagSplitAs;
	}

	public boolean temDuasMaos() {
		return splitHand.size() > 0;
	}

}
