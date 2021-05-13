package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestJogador {

	/**
	 * Testa se o jogador começa com 32 fichas
	 */
	@Test
	void testStartFicha() {
		Jogador j1 = new Jogador("1811526");//Start fichas dentro
		
		assertEquals(32,j1.pegaFichas().size());
	}

	/**
	 * Testa se o jogador recebe uma carta corretamente
	 */
	@Test
	void testDealCarta() {
		Jogador j1 = new Jogador("1811526");
		Carta c1 = new Carta(1,1,0);
		
		
		assertTrue(j1.pegaHand().isEmpty());
		
		j1.dealCarta(c1);
		
		assertFalse(j1.pegaHand().isEmpty());
	}

	/**
	 * Testa se as cartas são reomvidas após o final de uma rodada
	 */
	@Test
	void testClearHand(){
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
	void testCaclHand() {
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
	void testCaclFichas() {
		Jogador j1 = new Jogador("1811526");//Fichas já foram criadas
		
		assertEquals(500,j1.calcFichas());
	}
	
}
