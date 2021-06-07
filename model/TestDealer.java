package model;

import org.junit.Test;

import static org.junit.Assert.*;

public  class TestDealer {

	/**
	 * Testa se o dealer recebe as cartas corretamente, viradas para cima ou para baixo
	 */
	@Test
	public void testDealCarta() {
		Dealer dealer = new Dealer(null);
		Carta carta = new Carta(1,1,0);
		Carta carta2 = new Carta(13,1,0);

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
	 * Testa se as cartas são removidas após o final de uma rodada
	 */
	@Test
	public void testClearMesa() {
		Dealer dealer = new Dealer(null);
		Carta c1 = new Carta(1,1,1);
		Carta c2 = new Carta(1,1,1);

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
	public void testCaclMesa() {
		Dealer dealer = new Dealer(null);
		Carta c1 = new Carta(1,1,0);
		Carta c2 = new Carta(13,1,0);
		Carta c3;
		Carta c4;

		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);


		assertEquals(21,dealer.caclMesa());
		dealer.clearMesa();

		c1 = new Carta(1,1,0);
		c2 = new Carta(1,1,0);

		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);


		assertEquals(12,dealer.caclMesa());//Testa dois ases seguidos


		dealer.clearMesa();

		c1 = new Carta(13,1,1);
		c2 = new Carta(13,1,0);
		c3 = new Carta(1,1,0);

		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		dealer.dealCarta(c3, false);


		assertEquals(21,dealer.caclMesa());

		dealer.clearMesa();

		c1 = new Carta(5,1,0);
		c2 = new Carta(5,1,1);
		c3 = new Carta(5,1,0);
		c4 = new Carta(1,1,1);


		dealer.dealCarta(c1, true);
		dealer.dealCarta(c2, false);
		dealer.dealCarta(c3, false);
		dealer.dealCarta(c4, false);

		assertEquals(16,dealer.caclMesa());
	}

}
