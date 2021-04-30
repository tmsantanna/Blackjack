package model;
import java.util.*;

class Mestre {// A fun��o dessa classe � manter a no��o do jogo, do que est� acontencendo, jogadores e tudo mais.
	private List<Carta> baralho = new ArrayList<Carta>();//Cartas no baralho 
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private Dealer dealer = new Dealer();
	int vez;
	int numJogadores;
	

	public Mestre(String nome) {//Vers�o com um nome
		jogadores.add(new Jogador(nome));
		shuffleBaralho();
		return;
	}
	
	private void createBaralho() {//Cria os 4 baralhos
		int i,j;
		int num,naipe;
		
		baralho.clear();//Renicia o baralho
		
		for(i=0;i<4;i++) {
			//Inicia os contadores de baralho
			num = 1;
			naipe = 1;
			for(j=0;j<54;j++) {
				Carta novaCarta = new Carta(num,naipe);//Cria uma nova Carta
				baralho.add(novaCarta);//Coloca a nova carta no baralho
				
				num++;//Aumenta a carta
				if (num == 14) //Se completou o naipe, passa par o proximo
				{
					num = 1;//Reinicia o valor das Cartas
					naipe++;
				}
				
			}
		}
	}

	private void shuffleBaralho() {//Embaralha o baralho
		createBaralho();//Cria o baralho
		Collections.shuffle(baralho);
	}

	private void addJogador(String nome) {//Adiciona jogador
		jogadores.add(new Jogador(nome));
		return;
	}
	
	private int calculaNumJog() {//Calcula o numero de jogadores
		return numJogadores = jogadores.size();
	}
	
	private void clearCartas() {//Tira cartas de jogo da m�o dos jogadores e da mesa
		int i;
		
		for(i = 0; i < numJogadores;i++) {//Para cada jogador
			jogadores.get(i).clearHand();//Clear na m�o
		}
		dealer.clearMesa();//Clear na mesa
	}
	
	public void dealCartas() {
		for (Jogador jogador : jogadores) {
			jogador.dealCarta(baralho.remove(0));
		}
		dealer.dealCarta(baralho.remove(0), false);

		for (Jogador jogador : jogadores) {
			jogador.dealCarta(baralho.remove(0));
		}
		dealer.dealCarta(baralho.remove(0), true);
	}

}
