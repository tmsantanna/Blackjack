package model;

import model.Evento.Tipo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mestre extends Observable {// A fun��o dessa classe � manter a no��o do jogo, do que est� acontecendo, jogadores e tudo mais.
	private final List<Carta> baralho = new ArrayList<>();//Cartas no baralho
	private final List<Jogador> jogadores = new ArrayList<>();
	private final Dealer dealer = new Dealer();
	private final List<Jogador> blackjack = new ArrayList<>();
	private int vez = 0;
	private boolean deal = true;

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
	}

	private void createBaralho() {//Cria os 4 baralhos
		int i, j;
		int num, naipe;

		baralho.clear();//Reinicia o baralho

		for (i = 0; i < 4; i++) {
			//Inicia os contadores de baralho
			num = 1;
			naipe = 1;
			for (j = 0; j < 52; j++) {
				Carta novaCarta = new Carta(num, naipe, i % 2);//Cria uma nova Carta
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

	public Jogador removeJogador(int j) {//Remove um jogador da lista
		Jogador jogador = jogadores.get(j);
		jogador.clearHand();
		blackjack.remove(jogador);
		return jogadores.remove(j);
	}

	public void clearCartas() {//Tira cartas de jogo da m�o dos jogadores e da mesa
		for (Jogador jogador : jogadores) {//Para cada jogador
			jogador.clearHand();//Clear na m�o
		}
		dealer.clearMesa();//Clear na mesa
	}

	public List<Carta> pegaBaralho() {
		return baralho;
	}

	public void dealStart() {//Cartas no inicio da rodada
		for (Jogador jogador : jogadores) {
			dealCarta(jogador);
		}
		dealMesaCarta(false);

		for (Jogador jogador : jogadores) {
			dealCarta(jogador);
		}
		dealMesaCarta();

		checkBlackjack();
	}

	public void dealCarta() { //Da carta para o jogador da vez
		dealCarta(jogadores.get(vez));
	}

	public void dealCarta(Jogador jog) { //Da carta para um Jogador
		Carta carta = baralho.remove(0);
		jog.dealCarta(carta);

		notifyObservers(jogadores.indexOf(jog), Tipo.NOVA_CARTA, jog.pegaSegunda(), carta.pegaInfo());

		if (baralho.size() == 187) { //Embaralha depois de distribuir 10%
			shuffleBaralho();
		}
	}

	private void dealCartaMao(Jogador jog, boolean segunda) { //Da carta para uma mao de um Jogador
		Carta carta = baralho.remove(0);
		jog.dealCartaMao(carta, segunda);


		notifyObservers(jogadores.indexOf(jog), Tipo.NOVA_CARTA, segunda, carta.pegaInfo());
		if (baralho.size() == 187) { //Embaralha depois de distribuir 10%
			shuffleBaralho();
		}
	}

	public void dealMesaCarta() {//Da Carta para a mesa sem Boolean
		Carta carta = baralho.remove(0);
		dealer.dealCarta(carta, true);

		notifyObservers(-1, Tipo.NOVA_CARTA, false, carta.pegaInfo());

		if (baralho.size() == 187) { //Embaralha depois de distribuir 10%
			shuffleBaralho();
		}
	}

	public void dealMesaCarta(boolean visible) {//Da Carta para a mesa com Boolean
		Carta carta = baralho.remove(0);
		dealer.dealCarta(carta, visible);

		notifyObservers(-1, Tipo.NOVA_CARTA, false, carta.pegaInfo());

		if (baralho.size() == 187) { //Embaralha depois de distribuir 10%
			shuffleBaralho();
		}
	}

	public void stand() {//Stand, confirma a mão atual e passa a vez
		Jogador jogador = jogadores.get(vez);

		if (jogador.temDuasMaos() && !jogador.pegaSegunda()) {
			jogador.trocaMao();

			notifyObservers(Tipo.SEGUNDA_MAO);
			return;
		}

		proximoJogador(); //Passa a vez
	}

	public void deal() {
		jogadores.get(vez).deal();
		proximoJogador();
	}

	public void hit() {//Pede mais uma Carta
		dealCarta();//Da a carta

		if (jogadores.get(vez).caclHand() > 21) {
			notifyObservers(Tipo.PASSOU_DE_21);
			stand();
		}
	}

	public void comecarRodada() {
		deal = true;
		blackjack.clear();
		vez = 0;

		createBaralho();
		shuffleBaralho();

		for (Jogador jogador : jogadores) {
			jogador.clearHand();
		}

		notifyObservers(Tipo.PROXIMO_JOGADOR); //Notifica que o primeiro jogador pode fazer a aposta
	}

	private void terminarRodada() {
		vez = -1;
		deal = false;

		notifyObservers(-1, Tipo.PROXIMO_JOGADOR, -1);
		notifyObservers(Tipo.CLEAR_CARTAS);

		for (Jogador jogador : jogadores) {
			int indice = jogadores.indexOf(jogador);
			notifyObservers(indice, Tipo.CLEAR_CARTAS);
			notifyObservers(indice, Tipo.MUDANCA_NA_APOSTA, 0f, jogador.pegaFichas());
		}
	}

	private void proximoJogador() {
		int ultimoJogador = vez;

		vez++;
		while (vez < pegaNumJogadores() && blackjack.contains(jogadores.get(vez))) {
			vez++;
		}

		if (vez == pegaNumJogadores()) {
			if (deal) {
				vez = 0;
				deal = false;
				notifyObservers(ultimoJogador, Tipo.PROXIMO_JOGADOR, vez);
				dealStart();
			} else {
				vez = -1;
				notifyObservers(ultimoJogador, Tipo.PROXIMO_JOGADOR, vez);
				dealerTurn();
			}
		} else {
			notifyObservers(ultimoJogador, Tipo.PROXIMO_JOGADOR, vez);
		}
	}

	public boolean doubleAposta() {//Dobrar a aposta
		Jogador jogador = jogadores.get(vez);

		if (jogador.podeDobrarAposta()) {//Verifica se pode dobrar aposta
			jogador.dobrarAposta();//Dobra a aposta
			notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());

			dealCarta(jogador);

			if (jogador.caclHand() > 21) {
				notifyObservers(Tipo.PASSOU_DE_21);
			}

			stand();

			if (jogador.temDuasMaos()) {
				dealCartaMao(jogador, true);

				if (jogador.caclHand() > 21) {
					notifyObservers(Tipo.PASSOU_DE_21);
				}

				stand();
			}

			return true;
		} else {
			return false;//Não pode apostar
		}
	}

	public void clear(int jogador) {
		if (podeClear()) {
			jogadores.get(jogador).clearHand();
			notifyObservers(jogador, Tipo.CLEAR_CARTAS);
			notifyObservers(jogador, Tipo.MUDANCA_NA_APOSTA, 0f, jogadores.get(jogador).pegaFichas());
		}
	}

	public boolean split() {
		Jogador jogador = jogadores.get(vez);

		if (jogador.podeSplit()) {

			jogador.split(); //Faz o split

			dealCartaMao(jogador, false); //Da uma carta para a primeira mão
			dealCartaMao(jogador, true); //Da uma carta para a segunda mão

			notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());
			notifyObservers(Tipo.SPLIT);

			if (jogador.pegaFlagSplitAs()) { // pula a vez nas duas mãos
				stand();
				stand();
			}

			return true;
		} else {
			return false;
		}
	}

	public void surrender() {
		Jogador jogador = jogadores.get(vez);

		jogador.receber(jogador.pegaAposta() / 2);//Devolve metade das apostas
		jogador.clearHand();

		notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());
		notifyObservers(Tipo.CLEAR_CARTAS);

		proximoJogador();
	}

	public void quit(int jogador) {
		removeJogador(jogador);
		notifyObservers(jogador, Tipo.JOGADOR_REMOVIDO);

		if (vez != jogador) return;

		if (vez == pegaNumJogadores()) {
			if (deal) {
				vez = 0;
				deal = false;
				notifyObservers(jogador, Tipo.PROXIMO_JOGADOR, vez);
				dealStart();
			} else {
				vez = -1;
				notifyObservers(jogador, Tipo.PROXIMO_JOGADOR, vez);
				dealerTurn();
			}
		}
	}

	public void dealerTurn() {//Depois fazer AI do Dealer
		notifyObservers(-1, Tipo.MOSTRAR_CARTAS);

		dealer.pegaMesa().get(0).flip();

		int valor;

		valor = dealer.caclMesa();

		while (valor < 17) {
			dealMesaCarta();
			valor = dealer.caclMesa();
		}

		if (valor > 21) {
			notifyObservers(-1, Tipo.PASSOU_DE_21);
			//Mesa derrota
		}
	}

	public void checkBlackjack() {//Checa se ocorreu um Blackjack
		blackjack.clear();

		for (int index = 0; index < jogadores.size(); index++) {
			Jogador jogador = jogadores.get(index);

			if (jogador.caclHand() == 21) {//Se o jogador tem 21
				blackjack.add(jogador);
				notifyObservers(index, Tipo.BLACKJACK);
			}
		}

		if (dealer.caclMesa() == 21) {
			//vitoria jogadores / Dealer
			vez = -1;
			notifyObservers(Tipo.MOSTRAR_CARTAS);
			notifyObservers(Tipo.BLACKJACK);
			notifyObservers(Tipo.PROXIMO_JOGADOR);
		}
	}

	public void checkFinal() {//Check final, depois do dealer pegar todas a pecas que precisava
		//Check Final
	}

	public List<Jogador> pegaJogadores() {//Pega os jogadores
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

	public boolean podeJogar(int jogador) {
		return !deal && jogador == vez && !blackjack.contains(jogadores.get(jogador));
	}

	public boolean podeApostar(int jogador) {
		return deal && jogador == vez && jogadores.get(jogador).podeApostar();
	}

	public boolean podeDeal(int jogador) {
		return podeApostar(jogador) && jogadores.get(jogador).podeDeal();
	}

	public boolean podeHit(int jogador) {
		return podeJogar(jogador) && jogadores.get(jogador).podeHit();
	}

	public boolean podeStand(int jogador) {
		return podeJogar(jogador) && jogadores.get(jogador).podeStand();
	}

	public boolean podeDobrarAposta(int jogador) {
		return podeJogar(jogador) && jogadores.get(jogador).podeDobrarAposta();
	}

	public boolean podeSplit(int jogador) {
		return podeJogar(jogador) && jogadores.get(jogador).podeSplit();
	}

	public boolean podeClear() {
		return !deal && vez == -1;
	}

	public boolean podeApostar(int jogador, int valor) {
		return podeApostar(jogador) && jogadores.get(jogador).podeApostar(valor);
	}

	public boolean podeDiminuirAposta(int jogador, int valor) {
		return podeApostar(jogador) && jogadores.get(jogador).podeDiminuirAposta(valor);
	}

	public boolean podeSurrender(int jogador) {
		return podeJogar(jogador) && jogadores.get(jogador).podeSurrender();
	}

	public void apostar(int valor) {
		if (podeApostar(vez, valor)) {
			Jogador jogador = jogadores.get(vez);
			jogador.apostar(valor);
			notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());
		}
	}

	public void diminuirAposta(int valor) {
		if (podeDiminuirAposta(vez, valor)) {
			Jogador jogador = jogadores.get(vez);
			jogador.diminuirAposta(valor);
			notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());
		}
	}

	public String pegaNome(int jogador) {
		if (jogador == -1) {
			return "Dealer";
		}
		return jogadores.get(jogador).pegaNome();
	}

	public boolean pegaSegunda(int jogador) {
		return jogadores.get(jogador).pegaSegunda();
	}

	public boolean temDuasMaos(int jogador) {
		return jogadores.get(jogador).temDuasMaos();
	}

	private void notifyObservers(int jogador, Tipo tipo, Object... args) {
		notifyObservers(new Evento(this, jogador, tipo, args));
	}

	private void notifyObservers(Tipo tipo, Object... args) {
		notifyObservers(vez, tipo, args);
	}
}
