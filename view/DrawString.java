package view;

import java.awt.*;


class DrawString extends Componente {
	private int x,y;
	private String txt;
	private Color cor;
	private float font;
	
	public DrawString(int x, int y, String txt) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.cor = Color.black;
		this.font = 20f;
	}
	
	public DrawString(int x, int y, String txt,Color color) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.cor = color;
		this.font = 20f;
	}
	
	public DrawString(int x, int y, String txt,Color color, float font) {
		this.x = x;
		this.y = y;
		this.txt = txt;
		this.cor = color;
		this.font = font;
	}
	
	
	@Override
	public void paint(Graphics2D g) {

		g.setColor(cor);
		g.setFont(g.getFont().deriveFont(font));
		g.drawString(txt, x, y);//Desenha a String
		
	}
	
}
