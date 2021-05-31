package view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class TelaInicial extends Frame {
	private DrawImagem fundo = new DrawImagem(this, 0, -35, "imagens/inicial.png");
	private EntradaTexto t1 = new EntradaTexto(366,265,100,30);
	private EntradaTexto t2 = new EntradaTexto(366,325,100,30);
	private EntradaTexto t3 = new EntradaTexto(366,385,100,30);
	private EntradaTexto t4 = new EntradaTexto(366,445,100,30);

    TelaInicial(Consumer<List<String>> onNovo, Runnable onLoad) {
    	setTitle("TelaInicial");

    	getContentPane().add(t1);
    	getContentPane().add(t2);
    	getContentPane().add(t3);
    	getContentPane().add(t4);
    	getContentPane().add(new Botao(304, 500, "imagens/novoJogo.png", () -> onNovo.accept(getText())));
    	getContentPane().add(new Botao(423, 500, "imagens/carregar.png", onLoad));
    }

    private List<String> getText(){
    	List<String> nomes = new ArrayList<>();
    	nomes.add(t1.getText().trim());
    	nomes.add(t2.getText().trim());
    	nomes.add(t3.getText().trim());
    	nomes.add(t4.getText().trim());

		nomes.removeIf(String::isEmpty);

		return nomes;
    }
}
