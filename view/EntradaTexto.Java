/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;
import javax.swing.*;

class EntradaTexto extends JTextField {

	public EntradaTexto(String txt,int x, int y, int largura, int altura) {
		setBounds(x,y,largura,altura);
		setText(txt);
		setFont(getFont().deriveFont(30f));
	}
	
	public EntradaTexto(int x, int y, int largura, int altura) {
		setBounds(x,y,largura,altura);
		setText("");
	}

}
