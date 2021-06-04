package model;

public class JogadorPassouDe21 {

    public final int jogador;

    public final Mestre mestre;

    public final boolean split, segundaMao;

    JogadorPassouDe21(Mestre mestre, int jogador) {
        this.mestre = mestre;
        this.jogador = jogador;
        split = mestre.pegaJogadores().get(jogador).temDuasMaos();
        segundaMao = mestre.pegaJogadores().get(jogador).pegaSegunda();
    }

}
