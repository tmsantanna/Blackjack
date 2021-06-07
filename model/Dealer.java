/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import java.util.*;

class Dealer {
	private final List<Carta> mesa = new ArrayList<>();//Cartas na mesa
	private final Mestre mestre;

	Dealer(Mestre mestre) {
		this.mestre = mestre;
	}
	
	void dealCarta(Carta novaCarta, boolean visible) {//Adiciona cartas a mesa
		if (!visible) {
			novaCarta.flip();//Vira a carta se ela não for para ser vista
		}
		mesa.add(novaCarta);
	}
	
	void clearMesa(){//clear a mesa de cartas
		mesa.clear();//Clear
	}
	
	List<Carta> pegaMesa(){
		return mesa;
	}

	int caclMesa() {//Calcula o melhor valor da mesa considerando o valor do Às
		int resultado = 0;
		boolean temAs = false;

		for (Carta carta : mesa) {
			resultado += carta.pegaValor();//Adiciona o Valor

			if (carta.pegaValor() == 1) {
				temAs = true;
			}
		}

		//Caso tenha um Às e ele pode valer 11
		if (temAs && resultado + 10 <= 21) {
			resultado += 10;
		}

		return resultado;
	}

	void jogar() {
		while (continuarJogando()) {
			mestre.dealMesaCarta();
		}
	}

	private boolean continuarJogando() {
		if (caclMesa() < 17) return true;
		else if (caclMesa() >= 21) return false;

		float lucroTotal = 0;

		for (int i = 0; i < mestre.pegaNumJogadores(); i++) {
			lucroTotal += lucroJogador(i);
		}

		// Para de jogar caso esteja no lucro ou dependendo da chance de quebrar
		return lucroTotal < 0 && Math.random() >= chanceDeQuebrar();
	}

	private float lucroJogador(int jogador) {
		//Lucro do dealer em cima do jogador

		float multiplicador = -mestre.multiplicadorAposta(false, jogador, false);

		if (mestre.temDuasMaos(jogador)) {
			multiplicador -= mestre.multiplicadorAposta(false, jogador, true);
		}

		return multiplicador * mestre.pegaJogadores().get(jogador).pegaAposta();
	}

	private double chanceDeQuebrar() {
		int faltaPara21 = 21;

		for (Carta carta : mesa) {
			faltaPara21 -= carta.pegaValor();
		}

		if (faltaPara21 < 10) {
			return (double) (13 - faltaPara21) / 13;
		}

		return 0; //Caso falte mais de 10, não tem chance de quebrar
	}

}
