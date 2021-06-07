/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package model;

class Carta {
    private int valor;//Valor da Carta em blackjack
    private final int num;//Valor da Carta de verdade Ás até Rei = 1 a 13
    private final int naipe;//1 Ouros, 2 Espadas, 3 Copas, 4 Paus
    private boolean visible;//Se a carta está sendo vista ou não
    private final int deck;//0 azul e 1 vermelho

    Carta(int n, int na, int d) {
        num = n;
        naipe = na;
        visible = true;
        deck = d;
        calculaValor(n);
    }

    public void calculaValor(int n) {
        if (n > 9) {
            valor = 10;
        } else {
            valor = n;
        }
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

    public int pegaDeck() {
        return deck;
    }

    public Integer[] pegaInfo() {
        return new Integer[]{num, naipe, deck, visible ? 1 : 0};
    }

    public void flip() {//Vira a carta
        visible = !visible;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Carta)) return false;

        Carta c = (Carta) o;

        return naipe == c.naipe && num == c.num;
    }

}

