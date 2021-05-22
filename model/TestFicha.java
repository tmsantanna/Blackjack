package model;

	import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestFicha {

	/**
	 * Testa a conversão de fichas de valores maiores para valores menores
	 */
	@Test
	public void testConverte() {
		List<Ficha> f1 = new ArrayList<Ficha>();
		Ficha f = new Ficha(100);
		f1 = f.converte(20);

		assertEquals(5,f1.size());
		assertEquals(20,f1.get(0).pegarValor());
	}
}
