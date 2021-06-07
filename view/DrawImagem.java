package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class DrawImagem extends Componente {

	private final int x, y;//Local na tela
	private BufferedImage image;

	public DrawImagem(Frame frame, int x, int y, String path) {
		super(frame);
		this.x = x;
		this.y = y;

		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Erro ao carregar a imagem: " + path);
			System.exit(-1);
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
}
