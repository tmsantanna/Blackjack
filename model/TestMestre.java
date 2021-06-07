/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestMestre {

	/**
	 * Testa se o baralho foi criado corretamente
	 */
	@Test
	public void testCreateBaralho() {
		Mestre mestre = new Mestre("1811526", false);//Shuffle off
		
		//Cria baralho por si proprio

		assertEquals(13,mestre.pegaBaralho().get(12).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(25).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(38).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(51).pegaNum());
		assertEquals(1,mestre.pegaBaralho().get(52).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(207).pegaNum());
	}

	/**
	 * Testa se o shuffle executou corretamente
	 */
	@Test
	public void testShuffleBaralho() {
		Mestre m1 = new Mestre("1811526", false);//Shuffle off
		Mestre m2 = new Mestre("1811526");//Shuffle Default:On

		assertNotEquals(m1.pegaBaralho(), m2.pegaBaralho());
	}

	/**
	 * Testa se um jogador é adicionado à partida corretamente
	 */
	@Test
	public void testAddJogador() {
		Mestre m1 = new Mestre("1811526");
		
		assertEquals(1,m1.pegaJogadores().size());
		
		m1.addJogador("1910446");
		
		assertEquals(2,m1.pegaJogadores().size());
	}

	/**
	 * Testa se um jogador é removida da partida corretamente
	 */
	@Test
	public void testRemoveJogador() {
		Mestre m1 = new Mestre("1811526");

		assertEquals(1,m1.pegaJogadores().size());

		m1.removeJogador();

		assertEquals(0,m1.pegaJogadores().size());
	}

	/**
	 * Testa se o jogador recebe uma carta corretamente
	 */
	@Test
	public void testDealCarta() {
		Mestre m1 = new Mestre("1811526");
		m1.dealCarta();
		
		assertFalse(m1.pegaJogadores().get(0).pegaHand().isEmpty());
	}

	/**
	 * Testa se o dealer recebe uma carta corretamente
	 */
	@Test
	public void testDealMesaCarta() {
		Mestre m1 = new Mestre("1811526");
		
		m1.dealMesaCarta();
		assertFalse(m1.pegaDealer().pegaMesa().isEmpty());
		
		m1.dealMesaCarta(false);
		
		assertFalse(m1.pegaDealer().pegaMesa().get(1).pegaVisibilidade());
	}

	/**
	 * Testa se os jogadores recebem duas cartas no deal inicial
	 */
	@Test
	public void testDealStart() {
		Mestre m1 = new Mestre("1811526");
		
		m1.addJogador("1910446");
		
		m1.dealStart();
		assertEquals(2,m1.pegaJogadores().get(0).pegaHand().size());
		assertEquals(2,m1.pegaJogadores().get(1).pegaHand().size());
		assertEquals(2,m1.pegaDealer().pegaMesa().size());
	}

	/**
	 * Testa se as cartas são removidas dos jogadores corretamente
	 */
	@Test
	public void testClear() {
		Mestre m1 = new Mestre("1811526");

		m1.addJogador("1910446");

		m1.dealStart();
		m1.clearCartas();

		assertTrue(m1.pegaJogadores().get(0).pegaHand().isEmpty());
		assertTrue(m1.pegaJogadores().get(1).pegaHand().isEmpty());
		assertTrue(m1.pegaDealer().pegaMesa().isEmpty());
	}

	/**
	 * Testa se a vez é passada para o próximo jogador ao dar stand
	 */
	@Test
	public void testStand() {
		Mestre m1 = new Mestre("1811526");
		
		m1.addJogador("19010446");
		
		assertEquals(0,m1.pegaVez());
		m1.stand();
		assertEquals(1,m1.pegaVez());
	}
}
