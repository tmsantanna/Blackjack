/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import model.Evento.Tipo;

import java.util.*;

public class Mestre extends Observable {// A fun��o dessa classe � manter a no��o do jogo, do que est� acontecendo, jogadores e tudo mais.
	private final List<Carta> baralho = new ArrayList<>();//Cartas no baralho
	private final List<Jogador> jogadores = new ArrayList<>();
	private final Dealer dealer = new Dealer(this);
	private final Map<Jogador, Status> status = new HashMap<>();
	private int vez = 0;
	private boolean deal = true;

	public Mestre(String nome) {//Vers�o com um nome
		jogadores.add(new Jogador(nome));
		createBaralho();//Cria o baralho
		shuffleBaralho();
	}

	public Mestre(String nome, boolean shuffle) {//Vers�o com um nome e opção para shuffle
		jogadores.add(new Jogador(nome));
		createBaralho();//Cria o baralho
		if (shuffle) {
			shuffleBaralho();
		}
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
	}

	public void removeJogador() { //Remove um jogador da lista
		Jogador jogador = jogadores.get(vez);
		jogador.clearHand();
		status.remove(jogador);
		jogadores.remove(vez);
	}

	public void removeJogador(int j) { //Remove um jogador da lista
		Jogador jogador = jogadores.get(j);
		jogador.clearHand();
		status.remove(jogador);
		jogadores.remove(j);
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

	public boolean comecarRodada() {
		if (status.values().stream().anyMatch(status -> status != null && status != Status.CLEAR)) {
			return false;
		}

		for (Jogador jogador : jogadores) {
			status.put(jogador, Status.JOGANDO);
		}

		deal = true;
		vez = 0;
		dealer.clearMesa();

		createBaralho();
		shuffleBaralho();

		for (Jogador jogador : jogadores) {
			jogador.clearHand();
			notifyObservers(jogadores.indexOf(jogador), Tipo.MUDANCA_NA_APOSTA, 0f, jogador.pegaFichas());
		}

		notifyObservers(-1, Tipo.CLEAR_CARTAS);
		notifyObservers(Tipo.PROXIMO_JOGADOR); //Notifica que o primeiro jogador pode fazer a aposta

		return true;
	}

	private void proximoJogador() {
		int ultimoJogador = vez;

		vez++;
		while (vez < pegaNumJogadores() && status.get(jogadores.get(vez)) != Status.JOGANDO) {
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

	public void doubleAposta() {//Dobrar a aposta
		Jogador jogador = jogadores.get(vez);

		//Verifica se pode dobrar aposta
		if (!jogador.podeDobrarAposta()) return;

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
	}

	public void clear(int jogador) {
		if (!podeClear()) return;

		jogadores.get(jogador).clearHand();
		status.put(jogadores.get(jogador), Status.CLEAR);
		notifyObservers(jogador, Tipo.CLEAR_CARTAS);
		notifyObservers(jogador, Tipo.MUDANCA_NA_APOSTA, 0f, jogadores.get(jogador).pegaFichas());
	}

	public void split() {
		Jogador jogador = jogadores.get(vez);

		if (!jogador.podeSplit()) return;

		jogador.split(); //Faz o split

		dealCartaMao(jogador, false); //Da uma carta para a primeira mão
		dealCartaMao(jogador, true); //Da uma carta para a segunda mão

		notifyObservers(Tipo.MUDANCA_NA_APOSTA, jogador.pegaAposta(), jogador.pegaFichas());
		notifyObservers(Tipo.SPLIT);

		if (jogador.pegaFlagSplitAs()) { // pula a vez nas duas mãos
			stand();
			stand();
		}
	}

	public void surrender() {
		Jogador jogador = jogadores.get(vez);

		status.put(jogador, Status.SURRENDER);

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

	public void dealerTurn() {
		notifyObservers(-1, Tipo.MOSTRAR_CARTAS);

		dealer.pegaMesa().get(0).flip();
		dealer.jogar();

		if (dealer.caclMesa() > 21) {
			notifyObservers(-1, Tipo.PASSOU_DE_21);
		}

		calcularResultados(false);

		notifyObservers(-1, Tipo.FIM_DE_RODADA);
	}

	private void calcularResultados(boolean blackjackDealer) {
		for (Jogador jogador : jogadores) {
			if (status.get(jogador) != Status.SURRENDER) {
				boolean blackjack = status.get(jogador) == Status.BLACKJACK;
				float multiplicador = multiplicadorAposta(blackjackDealer, blackjack, jogador.caclHand(false));
				jogador.receber(multiplicador * jogador.pegaAposta());

				if (jogador.temDuasMaos()) {
					multiplicador = multiplicadorAposta(blackjackDealer, blackjack, jogador.caclHand(true));
					jogador.receber(multiplicador * jogador.pegaAposta());
				}
			}

			jogador.zerarAposta();
			notifyObservers(jogadores.indexOf(jogador), Tipo.MUDANCA_NA_APOSTA, 0f, jogador.pegaFichas());
		}

	}

	float multiplicadorAposta(boolean blackjackDealer, boolean blackjackJogador, int maoJogador) {
		int resultadoDealer = dealer.caclMesa() <= 21 ? dealer.caclMesa() : -1;
		int resultadoJogador = maoJogador <= 21 ? maoJogador : -1;

		if (blackjackDealer) {
			return blackjackJogador ? 2 : 0;
		} else if (blackjackJogador) {
			return 2.5f;
		} else if (resultadoJogador > 0 && resultadoJogador >= resultadoDealer) {
			return 2;
		}
		return 0;
	}

	float multiplicadorAposta(boolean blackjackDealer, int jogador, boolean segunda) {
		int valorMao = jogadores.get(jogador).caclHand(segunda);
		return multiplicadorAposta(blackjackDealer, status.get(jogadores.get(jogador)) == Status.BLACKJACK, valorMao);
	}

	public void checkBlackjack() { //Checa se ocorreu um Blackjack
		for (int index = 0; index < jogadores.size(); index++) {
			Jogador jogador = jogadores.get(index);

			if (jogador.caclHand() == 21) { //Se o jogador tem 21
				status.put(jogador, Status.BLACKJACK);
				notifyObservers(index, Tipo.BLACKJACK);

				if (index == 0) proximoJogador();
			}
		}

		if (dealer.caclMesa() == 21) {
			vez = -1;
			notifyObservers(Tipo.MOSTRAR_CARTAS);
			notifyObservers(Tipo.BLACKJACK);
			notifyObservers(Tipo.PROXIMO_JOGADOR);
			notifyObservers(Tipo.FIM_DE_RODADA);
			calcularResultados(true);
		}
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
		return !deal && jogador == vez && status.get(jogadores.get(jogador)) == Status.JOGANDO;
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

	public int caclHand(int jogador, boolean segunda) {
		if (jogador == -1) return dealer.caclMesa();

		return jogadores.get(jogador).caclHand(segunda);
	}

	private void notifyObservers(int jogador, Tipo tipo, Object... args) {
		notifyObservers(new Evento(this, jogador, tipo, args));
	}

	private void notifyObservers(Tipo tipo, Object... args) {
		notifyObservers(vez, tipo, args);
	}

	private enum Status { JOGANDO, CLEAR, BLACKJACK, SURRENDER }

}
