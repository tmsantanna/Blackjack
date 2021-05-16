package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Botao extends JButton {

	public Botao(int x, int y, int largura, int altura, String imagePath, Runnable acao) {
		setBounds(x, y, largura, altura);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setFocusTraversalKeysEnabled(false);
		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "");

		BufferedImage bufferImage;

		try {
			bufferImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			System.err.println("Erro ao carregar a imagem: " + imagePath);
			System.exit(-1);
			return;
		}

		setIcon(new ImageIcon(bufferImage));
		bufferImage = darker(bufferImage);
		setRolloverIcon(new ImageIcon(bufferImage));
		bufferImage = darker(bufferImage);
		setPressedIcon(new ImageIcon(bufferImage));
		bufferImage = darker(bufferImage);
		setDisabledIcon(new ImageIcon(bufferImage));

		addActionListener(a -> acao.run());
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
