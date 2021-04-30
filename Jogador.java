package model;
import java.util.*;

class Jogador {
		String nome;
		private List<Carta> hand = new ArrayList<Carta>();//Carta que o jogador tem é mão
		private List<Ficha> fichas = new ArrayList<Ficha>();//Fichas que o jogador tem
		
		
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
}
