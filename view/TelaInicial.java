/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import model.Mestre;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class TelaInicial extends Frame {
	private final DrawImagem fundo = new DrawImagem(this, 0, -35, "imagens/inicial.png");
	private final EntradaTexto t1 = new EntradaTexto(366,265,100,30);
	private final EntradaTexto t2 = new EntradaTexto(366,325,100,30);
	private final EntradaTexto t3 = new EntradaTexto(366,385,100,30);
	private final EntradaTexto t4 = new EntradaTexto(366,445,100,30);

    TelaInicial(Consumer<List<String>> onNovo, Consumer<Mestre> onLoad) {
    	setTitle("TelaInicial");
    	getContentPane().setBackground(Color.BLACK);

		t1.addActionListener(e -> onNovo.accept(getText()));
		t2.addActionListener(e -> onNovo.accept(getText()));
		t3.addActionListener(e -> onNovo.accept(getText()));
		t4.addActionListener(e -> onNovo.accept(getText()));

    	getContentPane().add(t1);
    	getContentPane().add(t2);
    	getContentPane().add(t3);
    	getContentPane().add(t4);
    	getContentPane().add(new Botao(304, 500, "imagens/novoJogo.png", () -> onNovo.accept(getText())));
    	getContentPane().add(new Botao(423, 500, "imagens/carregar.png", () -> GUI.mostraLoad(onLoad, this::onCancelLoad)));
    }

    private List<String> getText(){
    	List<String> nomes = new ArrayList<>();
    	nomes.add(t1.getText().trim());
    	nomes.add(t2.getText().trim());
    	nomes.add(t3.getText().trim());
    	nomes.add(t4.getText().trim());

		nomes.removeIf(String::isEmpty);

		t1.setText("");
		t2.setText("");
		t3.setText("");
		t4.setText("");
		return nomes;
    }

    private void onCancelLoad() {
    	GUI.mostraTelaInicial(null, null);
	}

}
