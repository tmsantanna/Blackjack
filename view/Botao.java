package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Botao extends JButton {

	private BufferedImage image, imagePressionado, imageInativo;

	private boolean pressionado = false;

	public Botao(int x, int y, int largura, int altura, String imagePath) {
		setBounds(x, y, largura, altura);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				pressionado = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pressionado = false;
				repaint();
			}
		});

		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		}
		imagePressionado = darker(image);
		imageInativo = darker(imagePressionado);
	}

	@Override
	public void paint(Graphics g) {
		if (!isEnabled()) {
			g.drawImage(imageInativo, 0, 0, null);
		} else if (pressionado) {
			g.drawImage(imagePressionado, 0, 0, null);
		} else {
			g.drawImage(image, 0, 0, null);
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
