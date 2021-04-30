
class Carta {
	private int valor;//Valor da Carta em blackjack
	private int num;//Valor da Carta de verdade Ás até Rei = 1 a 13
	private int naipe;//1 Ouros, 2 Espadas, 3 Copas, 4 Paus
	
	public Carta(int n, int na) {
		num = n;
		naipe = na;
		calculaValor(n);
		return;
	}
	
	private void calculaValor(int n) {
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
}
