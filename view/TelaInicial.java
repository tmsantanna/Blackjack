package view;

import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends Frame {
	private DrawImagem fundo = new DrawImagem(this, 0, 0, "imagens/inicial.png");
	private EntradaTexto t1 = new EntradaTexto(366,265,100,30);
	private EntradaTexto t2 = new EntradaTexto(366,325,100,30);
	private EntradaTexto t3 = new EntradaTexto(366,385,100,30);
	private EntradaTexto t4 = new EntradaTexto(366,445,100,30);
    private Botao novo = new Botao(304, 500, 114, 40, "imagens/novoJogo.png");
    private Botao load = new Botao(423, 500, 114, 40, "imagens/carregar.png");
	
    TelaInicial() {
    setTitle("TelaInicial");
    
    	fundo.add();
    	getContentPane().add(t1);
    	getContentPane().add(t2);
    	getContentPane().add(t3);
    	getContentPane().add(t4);
    	getContentPane().add(novo);
    	getContentPane().add(load);
    }
   
    public List<String >getText(){
    	 List<String> s = new ArrayList<>();
    	 s.add(t1.getText());
    	 s.add(t2.getText());
    	 s.add(t3.getText());
    	 s.add(t4.getText());
    	 
    	 
    	 return s;
    }
}
