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
	public int caclHand() {//Calcula o melhor valor da mão considerando o valor do Às
		int temp, resultado, resultadoB;
		List <Carta> save = new ArrayList<Carta>();
		
		
		resultado = 0;
		for (Carta carta: mesa) {//Passa por cada carta da mao
			temp = carta.pegaValor();
			if (temp == 1) {//Verifica se a carta é um Às
				save.add(carta);//guarda o Às para ser calculado mais tarde
			}
			else {
				resultado += temp;
			}
		}
		
		if (!save.isEmpty()) {//Checa se está vazio
			resultadoB = resultado;//Resultado alternativo é carregado
		
			for (Carta carta: mesa) {//Resolve os Àses					
				if (11 + resultado <= 21) {
					resultado += 11;
				} else {
					resultado += 1;
				}
			}
			
			if (resultado > 21) {//Verifica se deu mais de 21 para dar uma segunda chance
				for (Carta carta: mesa) {//Resolve os Àses (Alternativo)
					resultadoB +=1;
				}
				if(resultadoB < resultado) {//Checa se depois de passar de 21 como o Resultado normal, se o resultado B é melhor
					resultado = resultadoB;
				}
			}
			
		}
		return resultado;	
	}
}
