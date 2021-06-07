/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import java.util.ArrayList;
import java.util.List;

class Jogador {
	private final String nome;
	private final List<Carta> hand = new ArrayList<>();//Carta que o jogador tem é mão
	private final List<Carta> splitHand = new ArrayList<>();//Carta que o jogador tem é mão
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

	int caclHand() {//Calcula o melhor valor da mão considerando o valor do Às
		return caclHand(segunda);
	}

	int caclHand(boolean segunda) {//Calcula o melhor valor da mão considerando o valor do Às
		int resultado = 0, ases = 0;
		List<Carta> mao = segunda ? splitHand : hand;

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

	void split() {
		if (hand.get(0).pegaNum() == 1) {
			flagarSplitAs(true);
		}

		splitHand.add(hand.get(1));//Adiciona a carta para split Hand
		hand.remove(1);//Remove a carta da mão adicional

		aposta += aposta;
	}

	void trocaMao() { //Troca para usar a segunda mão
		segunda = !segunda;
	}

	boolean apostar(float valor) {
		if (valor < 0 || valor + aposta > fichas || apostado) return false;

		aposta += valor;

		return true;
	}

	void dobrarAposta() {
		if (2 * aposta > fichas || !apostado) return;

		aposta *= 2;
	}

	void diminuirAposta(float valor) {
		if (valor < 0 || valor > aposta || apostado) return;

		aposta -= valor;
	}

	void receber(float valor) {
		fichas += valor;
	}

	void zerarAposta() {
		aposta = 0;
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
		return !apostado && valor >= 0 && valor + aposta <= fichas && valor + aposta <= 100;
	}

	boolean podeDiminuirAposta(int valor) {
		return !apostado && valor >= 0 && valor <= aposta;
	}

	boolean podeDobrarAposta() {
		return aposta <= 50 && 2 * aposta <= fichas && hand.size() == 2 && !segunda;
	}

	boolean podeSplit() {
		if (splitHand.size() > 0) {//Se já deu split
			return false;
		}

		if (hand.size() != 2) {//Se a mão é maior que a inicial, retorna falso
			return false;
		}

		if (2 * aposta > fichas || aposta > 50) {//Verifica se a aposta pode ser feita
			return false;
		}

		//Verifica se as cartas da mao são iguais
		return hand.get(0).pegaValor() == hand.get(1).pegaValor();
	}

	boolean podeSurrender() {
		return hand.size() == 2 && !temDuasMaos();
	}

	List<Carta> pegaHand(){
		return hand;
	}

	void flagarSplitAs(boolean fato) {//Muda o flagSplitAs
		flagSplitAs = fato;
	}

	String pegaNome() {
		return nome;
	}

	float pegaFichas() {
		return fichas;
	}

	float pegaAposta() {
		return aposta;
	}

	boolean pegaSegunda() {
		return segunda;
	}

	boolean pegaFlagSplitAs() {
		return flagSplitAs;
	}

	boolean temDuasMaos() {
		return splitHand.size() > 0;
	}

}
