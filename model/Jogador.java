package model;
import java.util.*;

class Jogador {
		String nome;
		private List<Carta> hand = new ArrayList<Carta>();//Carta que o jogador tem é mão
		private List<Ficha> fichas = new ArrayList<Ficha>();//Fichas que o jogador tem
		private List<Ficha> aposta = new ArrayList<Ficha>();//Fichas que o jogador apostou

		public Jogador(String n) {
			nome = n;
			startFicha();
		}
		
		private void startFicha () {//Ficha para serem recebidas no inicio do jogo
			int i;

			for (i=0;i<2;i++) {//Duas fichas de $100
				fichas.add(new Ficha(100));
			}
			
			for (i=0;i<2;i++) { //Duas fichas de $50
				fichas.add(new Ficha(50));
			}
			
			for (i=0;i<5;i++){//Cinco fichas de $20
				fichas.add(new Ficha(20));
			}
			
			for (i=0;i<5;i++){//Cinco fichas de $10
				fichas.add(new Ficha(10));
			}
			
			for (i=0;i<8;i++){//Oito fichas de $5
				fichas.add(new Ficha(5));
			}
			
			for (i=0;i<10;i++){//Dez fichas de $1
				fichas.add(new Ficha(1));
			}
			return;
		} 
		
		public void dealCarta(Carta novaCarta){//Da uma carta para a mão do jogador
			hand.add(novaCarta);
			return;
		}
		
		public void clearHand() {//Remove todas as cartas da mão
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

		public int calcFichas() {
			int total = 0;

			for (Ficha ficha : fichas) {
				total += ficha.pegaValor();
			}

			return total;
		}

		boolean apostar(int valor) {
			Ficha ficha = new Ficha(valor);

			if (!fichas.contains(ficha)) {
				return false;
			}

			fichas.remove(ficha);
			aposta.add(ficha);

			return true;
		}

		boolean podeDobrarAposta() {
			int somaFichas = fichas.stream().mapToInt(Ficha::pegaValor).sum();
			int somaAposta = fichas.stream().mapToInt(Ficha::pegaValor).sum();

			return somaFichas >= somaAposta;
		}		


		private List<Ficha> converte(Ficha ficha,int valor) {
			int i;
			List<Ficha> conversao = new ArrayList<Ficha>();
			for (i=0; i < ficha.pegaValor()/valor;i++) {//Cria um numero de fichas depenendo do valor original e o valor novo
				conversao.add(new Ficha(valor));//Adiciona nova ficha
			}
			
			return conversao;
		}

}
