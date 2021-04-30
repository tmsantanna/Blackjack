import java.util.*;

class Dealer {
	//Dealer sera responsavel por ter o baralho, colocar as cartas na mesa, embaralhar as cartas e assim em diante
	private List<Carta> baralho = new ArrayList<Carta>();//Cartas no baralho 
	private List<Carta> mesa =  new ArrayList<Carta>();//Cartas na mesa
	
	
	private void createBaralho() {//Cria os 4 baralhos
		int i,j;
		int valor,num,naipe;
		
		for(i=0;i<4;i++) {
			//Inicia os contadores de baralho
			num = 1;
			naipe = 1;
			for(j=0;j<54;j++) {
				Carta novaCarta = new Carta(num,naipe);//Cria uma nova Carta
				baralho.add(novaCarta);//Coloca a nova carta no baralho
				
				num++;//Aumenta a carta
				if (num == 14) //Se completou o naipe, passa par o proximo
				{
					num = 1;//Reinicia o valor das Cartas
					naipe++;
				}
				
			}
		}
	}
	
	private void shuffleBaralho() {//Embaralha o baralho
		int i;
		int randomInt;
		Random rand = new Random();
		Carta temp;
		int tam = baralho.size();//Tamanho do baralho
		
		
		for(i=0;i<3*tam;i++) {//Percorre o baralho 3 vezes, mudando posições de cada carta
			randomInt = rand.nextInt(tam);//Pega um numero de 0 a tam-1
			temp = baralho.get(tam);//Pega a Carta do baralho
			baralho.remove(tam);//Remove a carta da posição
			baralho.add(temp);//Adiciona a carta novamente no final
		}
	}
	
	
	public Dealer() {
		createBaralho();//Dá para o Dealer um baralho
		shuffleBaralho();//Embaralha o baralho
	}
}
