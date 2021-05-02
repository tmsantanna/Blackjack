package model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class TestCarta {


	/**
	 * Testa o naipe e o número das cartas
	 */
	@Test 
	void testCarta() {
		Carta e = new Carta(1,1);
		
		assertEquals(1,e.pegaNaipe());
		assertEquals(1,e.pegaNum());
		
		e = new Carta(10,2);
		
		assertEquals(2,e.pegaNaipe());
		assertEquals(10,e.pegaNum());
		
		
		e = new Carta(13,4);
		
		assertEquals(4,e.pegaNaipe());
		assertEquals(13,e.pegaNum());
	}

	/**
	 * Testa o valor das cartas
	 */
	@Test
	void testCalculaValor() {
		Carta e = new Carta(1,1);
		assertEquals(1,e.pegaValor());//Teste se gera corretamente o numero
		e =  new Carta(13,1);
		assertEquals(10,e.pegaValor());
	}

	/**
	 * Testa a mudança de visibilidade das cartas
	 */
	@Test
	void testFlip() {
		Carta e = new Carta(1,1);
		
		assertTrue(e.pegaVisibilidade());
		e.flip();
		assertFalse(e.pegaVisibilidade());
	}

}
