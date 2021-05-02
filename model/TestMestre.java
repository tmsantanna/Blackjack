package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMestre {

	@Test
	void testCreateBaralho() {
		Mestre mestre = new Mestre("1811526", false);//Shuffle off
		
		//Cria baralho por si proprio
		
		assertEquals(13,mestre.pegaBaralho().get(12).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(25).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(38).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(51).pegaNum());
		assertEquals(1,mestre.pegaBaralho().get(52).pegaNum());
		assertEquals(13,mestre.pegaBaralho().get(207).pegaNum());
	}
	@Test
	void testShuffleBaralho() {
		Mestre m1 = new Mestre("1811526", false);//Shuffle off
		Mestre m2 = new Mestre("1811526");//Shuffle Default:On
		
		
		assertNotSame(m1.pegaBaralho().get(15),m2.pegaBaralho().get(15));
		assertNotSame(m1.pegaBaralho().get(16),m2.pegaBaralho().get(16));
		assertNotSame(m1.pegaBaralho().get(17),m2.pegaBaralho().get(17));
		assertNotSame(m1.pegaBaralho().get(18),m2.pegaBaralho().get(18));
		assertNotSame(m1.pegaBaralho().get(19),m2.pegaBaralho().get(19));
	}

	
	@Test
	void testAddJogador_removeJogador() {
		Mestre m1 = new Mestre("1811526");
		
		assertEquals(1,m1.pegaJogadores().size());
		
		m1.addJogador("1910446");
		
		assertEquals(2,m1.pegaJogadores().size());
		
		m1.removeJogador();
		
		assertEquals(1,m1.pegaJogadores().size());
	}
	
	@Test
	void testDealCarta() {
		Mestre m1 = new Mestre("1811526");
		m1.dealCarta();
		
		assertTrue(m1.pegaJogadores().get(0).pegaHand().isEmpty());
	}
	
	void testDealMesaCarta() {
		Mestre m1 = new Mestre("1811526");
		
		m1.dealMesaCarta();
		assertFalse(m1.pegaDealer().pegaMesa().isEmpty());
		
		m1.dealMesaCarta(false);
		
		assertFalse(m1.pegaDealer().pegaMesa().get(1).pegaVisibilidade());
	}
	
	
	@Test
	void testDealStart_testClear() {
		Mestre m1 = new Mestre("1811526");
		
		m1.addJogador("1910446");
		
		m1.dealStart();
		assertEquals(2,m1.pegaJogadores().get(0).pegaHand().size());
		assertEquals(2,m1.pegaJogadores().get(1).pegaHand().size());
		assertEquals(2,m1.pegaDealer().pegaMesa().size());
		
		m1.clearCartas();
		assertTrue(m1.pegaJogadores().get(0).pegaHand().isEmpty());
		assertTrue(m1.pegaJogadores().get(1).pegaHand().isEmpty());
		assertTrue(m1.pegaDealer().pegaMesa().isEmpty());
	}
	
	
	@Test
	void testStand() {
		Mestre m1 = new Mestre("1811526");
		
		m1.addJogador("19010446");
		
		assertEquals(0,m1.pegaVez());
		m1.stand();
		assertEquals(1,m1.pegaVez());
	}
}
