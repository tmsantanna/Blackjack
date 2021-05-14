package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Botao extends Componente {

	private int x, y, largura, altura;

	private Runnable acao;

	private BufferedImage image, imagePressionado, imageInativo;

	private boolean pressionado = false, ativo = true;

	private final MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {

			if (ativo && e.getX() >= x && e.getX() < x + largura && e.getY() >= y && e.getY() < y + altura) {
				pressionado = true;
				frame.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!ativo || !pressionado) return;

			if (e.getX() >= x && e.getX() < x + largura && e.getY() >= y && e.getY() < y + altura & acao != null) {
				acao.run();
			}

			pressionado = false;
			frame.repaint();
		}
	};

	public Botao(Frame frame, int x, int y, int largura, int altura, String imagePath) {
		super(frame);
		this.x = x;
		this.y = y;
		this.largura = largura;
		this.altura = altura;

		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		}
		imagePressionado = darker(image);
		imageInativo = darker(imagePressionado);
	}

	void setAtivo(boolean b) {
		ativo = b;
		frame.repaint();
	}

	boolean pegaAtivo() {
		return ativo;
	}

	void setAcao(Runnable acao) {
		this.acao = acao;
	}

	@Override
	void onAdd() {
		frame.getContentPane().addMouseListener(mouseAdapter);
	}

	@Override
	void onRemove() {
		frame.getContentPane().removeMouseListener(mouseAdapter);
	}

	@Override
	public void paint(Graphics2D g) {
		if (!ativo) {
			g.drawImage(imageInativo, x, y, null);
		} else if (pressionado) {
			g.drawImage(imagePressionado, x, y, null);
		} else {
			g.drawImage(image, x, y, null);
		}
	}

	private static BufferedImage darker(BufferedImage img) {
		BufferedImage darker = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int argb = img.getRGB(x, y);
				int a = argb & 0xFF000000, r = argb >> 16 & 0xFF, g = argb >> 8 & 0xFF, b = argb & 0xFF;
				r = (int) (r * 0.75) << 16;
				g = (int) (g * 0.75) << 8;
				b = (int) (b * 0.75);
				darker.setRGB(x, y, a | r | g | b);
			}
		}

		return darker;
	}

}
