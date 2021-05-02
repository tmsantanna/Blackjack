package model;

class Carta {
	private int valor;//Valor da Carta em blackjack
	private int num;//Valor da Carta de verdade Ás até Rei = 1 a 13
	private int naipe;//1 Ouros, 2 Espadas, 3 Copas, 4 Paus
	private boolean visible;//Se a carta está sendo vista ou não
	
	public Carta(int n, int na) {
		num = n;
		naipe = na;
		visible = true;
		calculaValor(n);
		return;
	}
	
	public void calculaValor(int n) {
		if (n>9) {
			valor = 10;
		}
		else {
			valor = n;
		}
		return;
	}
	
	public int pegaValor() {//Pega o valor
		return valor;
	}
	public int pegaNum() { //Pega o numero da carta
		return num;
	}
	public int pegaNaipe() { //Pega o numero da carta
		return naipe;
	}
	public boolean pegaVisibilidade() {//Pega para ver se a carta é visivel ou não
		return visible;
		
	}
	
	public void flip() {//Vira a carta
		if (visible) {//Se ela é visivel, fica invisivel
			visible = false;
		}
		else {//Se ela não é visivel, fica visivel
			visible = true;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Carta)) return false;

		Carta c = (Carta) o;

		return naipe == c.naipe && num == c.num;
	}

}


