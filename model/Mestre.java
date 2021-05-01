package model;
import java.util.*;

class Mestre {// A fun��o dessa classe � manter a no��o do jogo, do que est� acontencendo, jogadores e tudo mais.
	private List<Carta> baralho = new ArrayList<Carta>();//Cartas no baralho 
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private Dealer dealer = new Dealer();
	int vez = 0;
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
	
	
	
	
	public void dealStart() {//Cartas no inicio da rodada
		for (Jogador jogador : jogadores) {
			jogador.dealCarta(baralho.remove(0));
		}
		dealer.dealCarta(baralho.remove(0), false);

		for (Jogador jogador : jogadores) {
			jogador.dealCarta(baralho.remove(0));
		}
		dealer.dealCarta(baralho.remove(0), true);
	}

	public void dealCarta() {//Da carta para o jogador da vez
		jogadores.get(vez).dealCarta(baralho.remove(0));//Da a primeira carta do baralho para o jogador da vez
	}
	
	public void dealCarta(Jogador jog) {//Da carta para um Jogador
		jog.dealCarta(baralho.remove(0));
	}
	public void dealMesaCarta() {//Da Carta para a mesa sem Boolean
		dealer.dealCarta(baralho.remove(0), true);
	}
	
	public void dealMesaCarta(boolean visible) {//Da Carta para a mesa com Boolean
		dealer.dealCarta(baralho.remove(0), visible);
	}
	
	public void stand() {//Stand, confirma a mão atual e passa a vez
		vez++;//Passa a vez
		vez = vez%4;//Verifica se deu volta
		return;
	}
	
	public void hit() {//Pede mais uma Carta
		dealCarta();
		
		if (jogadores.get(vez).caclHand() < 21) {//Se o Jogador quebrou
			vez++;//Passa a vez
			vez = vez%4;//Verifica se deu volta
		}
		return;
	}
	
	private void dealerTurn() {
		int valor;
		
		valor = dealer.caclMesa();
		
		while (valor<17) {
			dealMesaCarta();
			valor = dealer.caclMesa();
		}
		
		if (valor<21) {
			//Mesa Derrota
		}
		return;
	}
	private void checkBlackjack() {//Checa se ocorreu um Blackjack
		boolean flagDealer = false;
		boolean flagJogador = false;
		List<Jogador> vencedores = new ArrayList<Jogador>();
		
		if (dealer.caclMesa() == 21) {
			flagDealer = true;
		}
		
		for (Jogador jogador : jogadores) {
			if (jogador.caclHand() == 21) {
				flagJogador = true;
				vencedores.add(jogador);
			}
		}
		
		
		if (flagDealer && flagJogador) {
			//Empate função
		}else if(flagDealer) {
				//Dealer vence função
		}else if (flagJogador) {
				//Jogador vence função
			}
		return;
	}
}
