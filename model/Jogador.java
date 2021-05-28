package model;
import java.util.*;

public class Mestre {// A fun��o dessa classe � manter a no��o do jogo, do que est� acontencendo, jogadores e tudo mais.
	private List<Carta> baralho = new ArrayList<Carta>();//Cartas no baralho 
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private Dealer dealer = new Dealer();
	private int vez = 0;
	

	public Mestre(String nome) {//Vers�o com um nome
		jogadores.add(new Jogador(nome));
		createBaralho();//Cria o baralho
		shuffleBaralho();
		return;
	}
	
	public Mestre(String nome, boolean shuffle) {//Vers�o com um nome e opção para shuffle
		jogadores.add(new Jogador(nome));
		createBaralho();//Cria o baralho
		if (shuffle) {
			shuffleBaralho();
		}
		return;
	}

	public Mestre(List<String> nomes) {
		for (String nome : nomes) {
			jogadores.add(new Jogador(nome));
		}
		createBaralho();
		shuffleBaralho();
	}

	private void createBaralho() {//Cria os 4 baralhos
		int i,j;
		int num,naipe;
		
		baralho.clear();//Renicia o baralho
		
		for(i=0;i<4;i++) {
			//Inicia os contadores de baralho
			num = 1;
			naipe = 1;
			for(j=0;j<52;j++) {
				Carta novaCarta = new Carta(num,naipe,i%2);//Cria uma nova Carta
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
		Collections.shuffle(baralho);
	}

	public void addJogador(String nome) {//Adiciona jogador
		jogadores.add(new Jogador(nome));
		return;
	}
	
	public Jogador removeJogador() {//Remove um jogador da lista
		Jogador jogador = jogadores.get(vez);
		jogador.clearHand();
		return jogadores.remove(vez);
	}
	
	public void clearCartas() {//Tira cartas de jogo da m�o dos jogadores e da mesa
		for(Jogador jogador : jogadores) {//Para cada jogador
			jogador.clearHand();//Clear na m�o
		}
		dealer.clearMesa();//Clear na mesa
	}

	public List<Carta> pegaBaralho(){
		return baralho;
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
		if (vez == pegaNumJogadores()) {
			dealerTurn();
		}
		return;
	}
	
	public void hit() {//Pede mais uma Carta
		
		dealCarta();//Da a carta
		
		if (jogadores.get(vez).caclHand(jogadores.get(vez).pegaHand()) < 21 && jogadores.get(vez).caclHand(jogadores.get(vez).pegaSplitHand()) < 21) {//Verifica se as duas mão estão cheias
			vez++;//Passa a vez
			if (vez == pegaNumJogadores()) {
				dealerTurn();
				}
			}
		
		if (jogadores.get(vez).caclHand() < 21) {//Se o Jogador quebrou
			if (jogadores.get(vez).pegaSplitHand().size()>0){//Se possui cartas numa mão split, continua, porem, agora tudo itera com a segunda mão				
				jogadores.get(vez).trocaMao();//Passa para a segunda mão.
			}else{
				vez++;//Passa a vez
				if (vez == pegaNumJogadores()) {
					dealerTurn();
				}
			}
		}
		return;
	}
	
	public boolean doubleAposta() {//Dobrar a aposta
		if(jogadores.get(vez).podeDobrarAposta()) {//Verifica se pode dobrar aposta
			jogadores.get(vez).apostar(jogadores.get(vez).pegaAposta());//Dobra a aposta
			return true;
		}
		else {
			return false;//Não pode apostar
		}
		
	}
	
	public void clear() {
		
		jogadores.get(vez).receber(jogadores.get(vez).pegaAposta()/2);//Devolve metade das apostas
		
		jogadores.get(vez).clearHand();
		
		vez++;//Passa a vez
		if (vez == pegaNumJogadores()) {
			dealerTurn();
		}
		
		return;
	}
	
	public boolean split() {
		if(jogadores.get(vez).podeSplit()){
			
			jogadores.get(vez).apostar(jogadores.get(vez).pegaAposta());//Pega a aposta atual e aposta novamente para a segunda mão
			
			jogadores.get(vez).split();//Faz o split
			
			dealCarta(jogadores.get(vez));//Da uma carta para a primeira mão
			jogadores.get(vez).trocaMao();//Troca para a segunda mão
			
			dealCarta(jogadores.get(vez));//Da uma carta para a segunda mão
			jogadores.get(vez).trocaMao();//Troca de volta para a primeira mão
			return true;
		}else{
			return false;
		}
	}
	
	public void dealerTurn() {//Depois fazer AI do Dealer
		int valor;
		checkBlackjack();
		
		valor = dealer.caclMesa();
		
		while (valor<17) {
			dealMesaCarta();
			valor = dealer.caclMesa();
		}
		
		if (valor>21) {
			//Mesa derrota
		}
		return;
	}
	
	public void checkBlackjack() {//Checa se ocorreu um Blackjack
		boolean flagDealer = false;
		List<Jogador> vencedores = new ArrayList<Jogador>();
		
		if (dealer.caclMesa() == 21) {
			flagDealer = true;//Blackjack
		}
		
		for (Jogador jogador : jogadores) {
			if (jogador.caclHand() == 21 && jogador.pegaSplitHand().size()>0) {//Se o jogador tem 21 e não tem uma segunda mão...
				vencedores.add(jogador);
			}
		}
		
		if (flagDealer && vencedores.size()>0) {
			//Empate
			return;
		}
		if(vencedores.size()>0) {
			//Jogadores Blackjack
			return;
		}
		if(flagDealer){
			//Dealer Blackjack
			return;
		}
		return;
	}
	
	public void checkFinal() {//Check final, depois do dealer pegar todas a pecas qure precisava
		//Check Final
	}
	
	public List<Jogador>pegaJogadores(){//Pega os jogadores
		return jogadores;
	}
	
	public Dealer pegaDealer() {//Pega o Dealer
		return dealer;
	}
	
	public int pegaVez() {
		return vez;
	}
	
	public int pegaNumJogadores() {
		return jogadores.size();
	}
}
