package model;

import java.util.ArrayList;
import java.util.List;

class Ficha {
	private int valor;//Valor da ficha

	public Ficha(int v) {
		valor = v;
		return;
	}

	public int pegarValor() {
		return valor;
	}

	public List<Ficha> converte(int valor) {
		List<Ficha> fichas = new ArrayList<>();

		for (int i = 0; i < this.valor / valor; i++) {
			fichas.add(new Ficha(valor));
		}

		int resto = this.valor % valor;

		for (int i = 0; i < resto; i++) {
			fichas.add(new Ficha(1));
		}

		return fichas;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Ficha && valor == ((Ficha) o).valor;
	}

}
