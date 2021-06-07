/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestJogador {

	/**
	 * Testa se o jogador recebe uma carta corretamente
	 */
	@Test
	public void testDealCarta() {
		Jogador j1 = new Jogador("1811526");
		Carta c1 = new Carta(1,1,0);
		
		
		assertTrue(j1.pegaHand().isEmpty());
		
		j1.dealCarta(c1);
		
		assertFalse(j1.pegaHand().isEmpty());
	}

	/**
	 * Testa se as cartas são removidas após o final de uma rodada
	 */
	@Test
	public void testClearHand(){
		Jogador j1 = new Jogador("1811526");
		Carta c1 = new Carta(1,1,0);
		Carta c2 = new Carta(2,1,0);
		
		j1.dealCarta(c1);
		j1.dealCarta(c2);
		
		assertFalse(j1.pegaHand().isEmpty());
		
		j1.clearHand();
		
		assertTrue(j1.pegaHand().isEmpty());
	}

	/**
	 * Testa se a soma das cartas na mão é correta
	 */
	@Test
	public void testCaclHand() {
		Jogador j1 = new Jogador("1811526");
		Carta c1 = new Carta(1,1,0);
		Carta c2 = new Carta(13,1,0);
		Carta c3;
		Carta c4;
		
		j1.dealCarta(c1);
		j1.dealCarta(c2);
		
		
		assertEquals(21,j1.caclHand());
		j1.clearHand();
		
		c1 = new Carta(1,1,1);
		c2 = new Carta(1,1,1);
		
		j1.dealCarta(c1);
		j1.dealCarta(c2);
		
		
		assertEquals(12,j1.caclHand());//Testa dois ases seguidos
		
		
		j1.clearHand();
		
		c1 = new Carta(13,1,0);
		c2 = new Carta(13,1,0);
		c3 = new Carta(1,1,0);
		
		j1.dealCarta(c1);
		j1.dealCarta(c2);
		j1.dealCarta(c3);
		
		
		assertEquals(21,j1.caclHand());
		
		j1.clearHand();
		
		c1 = new Carta(5,1,0);
		c2 = new Carta(5,1,0);
		c3 = new Carta(5,1,0);
		c4 = new Carta(1,1,0);
		
		
		j1.dealCarta(c1);
		j1.dealCarta(c2);
		j1.dealCarta(c3);
		j1.dealCarta(c4);
		
		
		assertEquals(16,j1.caclHand());
	}

	/**
	 * Testa se a soma das fichas é correta
	 */
	@Test
	public void testCaclFichas() {
		Jogador j1 = new Jogador("1811526");//Fichas já foram criadas
		
		assertEquals(500, j1.pegaFichas(), 0);
	}

	/**
	 * Testa se a aposta é feita corretamente
	 */
	@Test
	public void testAposta() {
		Jogador j1 = new Jogador("1811526");//Fichas já foram criadas
		j1.apostar(100);

		assertEquals(100, j1.pegaAposta(), 0);
	}

	/**
	 * Testa se uma aposta inválida não é contabilizada
	 */
	@Test
	public void testApostaInvalida() {
		Jogador j1 = new Jogador("1811526");//Fichas já foram criadas
		assertFalse(j1.podeApostar(-100));
		assertFalse(j1.apostar(-100));

		assertEquals(0, j1.pegaAposta(), 0);
	}

}
