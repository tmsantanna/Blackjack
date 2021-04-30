package model;
import java.util.*;

class Dealer {
	private List<Carta> mesa =  new ArrayList<Carta>();//Cartas na mesa
	
	

	public Dealer() {
		return;
	}
	
	void dealCarta(Carta novaCarta, boolean visible) {//Adiciona cartas a mesa
		if (!visible) {
			novaCarta.flip();//Vira a carta se ela não for para ser vista
		}
		mesa.add(novaCarta);
	}
	
	public void clearMesa(){//clear a mesa de cartas
		mesa.clear();//Clear
		return;
	}
}
