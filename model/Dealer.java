package model;
import java.util.*;

class Dealer {
	private List<Carta> mesa = new ArrayList<>();//Cartas na mesa

	Dealer() {
	}
	
	void dealCarta(Carta novaCarta, boolean visible) {//Adiciona cartas a mesa
		if (!visible) {
			novaCarta.flip();//Vira a carta se ela não for para ser vista
		}
		mesa.add(novaCarta);
	}
	
	public void clearMesa(){//clear a mesa de cartas
		mesa.clear();//Clear
		return;
	}
	
	public List<Carta> pegaMesa(){
		return mesa;
	}

	public int caclMesa() {//Calcula o melhor valor da mesa considerando o valor do Às
		int resultado = 0, ases = 0;

		for (Carta carta : mesa) {
			resultado += carta.pegaValor();//Adiciona o Valor

			if (carta.pegaValor() == 1) {
				ases++;//Conta Ases
			}
		}

		if (ases>0) {
			if (resultado + 10 <= 21) {
				resultado += 10;
			}
		}

		return resultado;
	}

}
