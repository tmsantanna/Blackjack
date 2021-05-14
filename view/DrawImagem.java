package view;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class DrawImagem extends Componente {
	
	private int x, y;//Local na tela
	private String path;//path
	
	
	public DrawImagem(int x, int y, String path) {
		this.x = x;
		this.y = y;
		this.path = path;
	}
	
	@Override
	public void paint(Graphics2D g) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g.drawImage(img,x,y,null);
	}
}
