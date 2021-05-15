package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EntradaTexto extends JTextField{
	private int x, y,largura;

	
	public EntradaTexto(String txt,int x, int y, int largura, int altura) {
		this.x = x;
		this.y = y;
		
		this.largura = largura;

		
		
		setBounds(x,y,largura,altura);
		setText(txt);
		setFont(getFont().deriveFont(30f));
		
		
	}
	
	public EntradaTexto(int x, int y, int largura, int altura) {
		this.x = x;
		this.y = y;
		
		this.largura = largura;

		
		
		setBounds(x,y,largura,altura);
		setText("");
	}

}
