/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import java.util.*;

class Dealer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7926001715215555885L;
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
	void jogar(List<Jogador> jogadores) {
		while (caclMesa() < 17) {
			mestre.dealMesaCarta();
		}
		
		//Setup da IA
		int mao;
		int flag_jogadoresPerdem = 0;
		int flag_jogadoresVencem = 0;
		int flag_jogadoresExplodem = 0;
		
		if (caclMesa()<21) {//IA
			for(Jogador jogador: jogadores) {
				mao = jogador.caclHand();
				
				if(mao>21 || mao<caclMesa()){
					flag_jogadoresPerdem++;
					if (mao>21) {
						flag_jogadoresExplodem++;
					}
				}
				else {
					
					flag_jogadoresVencem++;
				}
			}
		}
		
		if (flag_jogadoresPerdem == flag_jogadoresExplodem && caclMesa()!=21 && flag_jogadoresVencem > 0) {//Se todos os jogadores que perderam explodiram, portanto existe pouco risco em tentar vencer os que restaram
			mestre.dealMesaCarta();
		}
		
		else if(flag_jogadoresVencem>flag_jogadoresPerdem && caclMesa()<19) {//Se mais jogadores estão vencendo que perdendo, e ainda tem alguma chance de puxar uma carta baixa, faça
			mestre.dealMesaCarta();
		}
		
		
	}

}
