package model;
import java.util.*;

public class Jogador {
		String nome;
		private List<Carta> hand = new ArrayList<Carta>();//Carta que o jogador tem é mão
		private int fichas = 500;
		private int aposta = 0;

		Jogador(String n) {
			nome = n;
		}
		
		void dealCarta(Carta novaCarta){//Da uma carta para a mão do jogador
			hand.add(novaCarta);
			return;
		}
		
		void clearHand() {//Remove todas as cartas da mão
			hand.clear();//Clear
			return;
		}
		
		public int caclHand() {//Calcula o melhor valor da mão considerando o valor do Às
			int resultado = 0, ases = 0;

			for (Carta carta : hand) {
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

		boolean apostar(int valor) {
			if (valor < 0 || valor > fichas) return false;

			aposta += valor;
			fichas -= valor;

			return true;
		}

		boolean podeApostar(int valor) {
			return valor >= 0 && valor <= fichas;
		}

		boolean podeDobrarAposta() {
			return fichas >= 2 * aposta;
		}
		
		public List<Carta> pegaHand(){
			return hand;
		}
		
		public String pegaNome() {
			return nome;
		}

		public int pegaFichas(){
			return fichas;
		}

		public int pegaAposta(){
			return aposta;
		}
}
