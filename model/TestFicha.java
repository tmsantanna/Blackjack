package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestFicha {

	@Test
	void testConveret() {
		List<Ficha> f1 = new ArrayList<Ficha>();
		Ficha f = new Ficha(100);
		f1 = f.converte(20);
		
		assertEquals(5,f1.size());
		assertEquals(20,f1.get(0).pegarValor());
	}
}
