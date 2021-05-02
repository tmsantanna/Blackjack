package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestDealer {

	/**
	 * Testa se o dealer recebe as cartas corretamente, viradas para cima ou para baixo
	 */
	@Test
	void testDealCarta() {
		Dealer dealer = new Dealer();
		Carta carta = new Carta(1,1);
		Carta carta2 = new Carta(13,1);
		
		dealer.dealCarta(carta, true);
		
		assertEquals(1,dealer.pegaMesa().size());
		
		assertEquals(1,dealer.pegaMesa().get(0).pegaNum());//Checa se o numero é o mesmo
		
		assertTrue(dealer.pegaMesa().get(0).pegaVisibilidade());
		
		
		dealer.dealCarta(carta2, false);
	
		assertEquals(2,dealer.pegaMesa().size());
		
		assertEquals(13,dealer.pegaMesa().get(1).pegaNum());//Checa se o numero é o mesmo
		
		assertFalse(dealer.pegaMesa().get(1).pegaVisibilidade());
	}

	/**
	 * Testa se as cartas são reomvidas após o final de uma rodada
	 */
	@Test
	void testClearMesa() {
		Dealer dealer = new Dealer();
		Carta c1 = new Carta(1,1);
		Carta c2 = new Carta(1,1);
		
		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		
		assertEquals(2,dealer.pegaMesa().size());
		
		dealer.clearMesa();
		
		assertTrue(dealer.pegaMesa().isEmpty());
	}

	/**
	 * Testa se a soma das cartas é correta
	 */
	@Test
	void testCaclMesa() {
		Dealer dealer = new Dealer();
		Carta c1 = new Carta(1,1);
		Carta c2 = new Carta(13,1);
		Carta c3;
		Carta c4;
		
		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		
		
		assertEquals(21,dealer.caclMesa());
		dealer.clearMesa();
		
		c1 = new Carta(1,1);
		c2 = new Carta(1,1);
		
		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		
		
		assertEquals(12,dealer.caclMesa());//Testa dois ases seguidos
		
		
		dealer.clearMesa();
		
		c1 = new Carta(13,1);
		c2 = new Carta(13,1);
		c3 = new Carta(1,1);
		
		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		dealer.dealCarta(c3, false);
		
		
		assertEquals(21,dealer.caclMesa());
		
		dealer.clearMesa();
		
		c1 = new Carta(5,1);
		c2 = new Carta(5,1);
		c3 = new Carta(5,1);
		c4 = new Carta(1,1);
		
		
		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		dealer.dealCarta(c3, false);
		dealer.dealCarta(c4, false);
		
		
		assertEquals(16,dealer.caclMesa());
		
	}

}
