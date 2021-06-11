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
		while (caclMesa() < 17) {
			mestre.dealMesaCarta();
		}
	}

}
