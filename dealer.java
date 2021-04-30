import java.util.Random;

class Dealer {
	//Dealer sera responsavel por ter o baralho, colocar as cartas na mesa, embaralhar as cartas e assim em diante
	private Carta baralho[];//Cartas no baralho 
	private Carta mesa[];//Cartas na mesa
	
	
	private void createBaralho() {//Cria os 4 baralhos
		int i,j;
		int valor,num,naipe;
		
		for(i=0;i<4;i++) {
			//Inicia os contadores de baralho
			num = 1;
			naipe = 1;
			for(j=0;j<54;j++) {
				Carta novaCarta = new Carta(num,naipe);//Cria uma nova Carta
				baralho[i*54+j] = novaCarta;//Coloca a nova carta no baralho
				
				num++;//Aumenta a carta
				if (num == 14) //Se completou o naipe, passa par o proximo
				{
					num = 1;
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
		int tam = baralho.length;//Tamanho do baralho
		
		
		for(i=0;i<3*tam;i++) {//Percorre o baralho 3 vezes, mudando posições de cada carta
			randomInt = rand.nextInt(tam);//Pega um numero de 0 a tam-1
			temp = baralho[randomInt];//Pega a Carta do baralho
			
			baralho[randomInt] = baralho[i];//Pega a carta e coloca no numero
			baralho[i] = temp;//Completa a troca de lugar
		}
	}
	
	
	
	public Dealer() {
		createBaralho();//Dá para o Dealer um baralho
		shuffleBaralho();//Embaralha o baralho
	}
}
