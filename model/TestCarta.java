/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCarta {


	/**
	 * Testa o naipe e o número das cartas
	 */
	@Test
	public void testCarta() {
		Carta e = new Carta(1,1,0);
		
		assertEquals(1,e.pegaNaipe());
		assertEquals(1,e.pegaNum());
		
		e = new Carta(10,2,0);
		
		assertEquals(2,e.pegaNaipe());
		assertEquals(10,e.pegaNum());
		
		
		e = new Carta(13,4,0);
		
		assertEquals(4,e.pegaNaipe());
		assertEquals(13,e.pegaNum());
	}

	/**
	 * Testa o valor das cartas
	 */
	@Test
	public void testCalculaValor() {
		Carta e = new Carta(1,1,0);
		assertEquals(1,e.pegaValor());//Teste se gera corretamente o numero
		e =  new Carta(13,1,0);
		assertEquals(10,e.pegaValor());
	}

	/**
	 * Testa a mudança de visibilidade das cartas
	 */
	@Test
	public void testFlip() {
		Carta e = new Carta(1,1,0);
		
		assertTrue(e.pegaVisibilidade());
		e.flip();
		assertFalse(e.pegaVisibilidade());
	}

}
