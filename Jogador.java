
class Jogador {
		String nome;
		Carta hand[];//Carta que o jogador tem é mão
		Ficha fichas[];//Fichas que o jogador tem
		
		
		public Jogador(String n) {
			nome = n;
			startFicha();
		}
		
		
		private void startFicha () {
			int i,j;
			j = 0;
			for (i=0;i<2;i++) {//Duas fichas de $100
				fichas[j] = new Ficha(100);
				j++;
			}
			
			for (i=0;i<2;i++) { //Duas fichas de $50
				fichas[j] = new Ficha(50);
				j++;
			}
			
			for (i=0;i<5;i++){//Cinco fichas de $20
				fichas[j] = new Ficha(20);
				j++;
			}
			
			for (i=0;i<5;i++){//Cinco fichas de $10
				fichas[j] = new Ficha(10);
				j++;
			}
			
			for (i=0;i<8;i++){
				fichas[j] = new Ficha(5);//Oito fichas de $5
				j++;
			}
			
			for (i=0;i<10;i++){
				fichas[j] = new Ficha(1);//Dez fichas de $1
				j++;
			}
			return;
		} 
		
		public void dealCarta(Carta novaCarta){//Da uma carta para a mão do jogador
			int tam = hand.length;
			
			hand[tam] = novaCarta;
			return;
		}
}
