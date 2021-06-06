package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Botao extends JButton {

	private static final BufferedImage imagemFundo = carregaImagem("imagens/botao.png");

	public Botao(int x, int y, String imagePath, Runnable acao) {
		this(x, y, carregaImagem(imagePath), acao);
	}

	public Botao(String texto, int x, int y, Runnable acao) {
		this(x, y, criaImagem(texto, 24), acao);
	}

	public Botao(String texto, int x, int y, int tamanhoFonte, Runnable acao) {
		this(x, y, criaImagem(texto, tamanhoFonte), acao);
	}


	Botao(int x, int y, BufferedImage imagem, Runnable acao) {
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setFocusTraversalKeysEnabled(false);
		getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "");

		setBounds(x, y, imagem.getWidth(), imagem.getHeight());

		setIcon(new ImageIcon(imagem));
		imagem = darker(imagem);
		setRolloverIcon(new ImageIcon(imagem));
		imagem = darker(imagem);
		setPressedIcon(new ImageIcon(imagem));
		imagem = darker(imagem);
		setDisabledIcon(new ImageIcon(imagem));

		addActionListener(a -> acao.run());
	}

	private static BufferedImage carregaImagem(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Erro ao carregar a imagem: " + path);
			System.exit(-1);
		}
		return null;
	}

	private static BufferedImage criaImagem(String texto, int tamanhoFonte) {
		Font fonte =  new Font("Calibri", Font.BOLD, tamanhoFonte);

		Graphics2D graphics = (Graphics2D) imagemFundo.getGraphics();
		graphics.setFont(fonte);

		int widthTexto = graphics.getFontMetrics().stringWidth(texto);
		int heightTexto = graphics.getFontMetrics().getHeight();

		int width = Math.max(widthTexto + 10, imagemFundo.getWidth());
		int height = imagemFundo.getHeight();

		BufferedImage imagem = new BufferedImage(width, height, imagemFundo.getType());
		graphics = (Graphics2D) imagem.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		graphics.setFont(fonte);
		graphics.setColor(Color.DARK_GRAY);

		graphics.drawImage(imagemFundo, 20, 0, width - 20, height, 50, 0, 51, height, null);
		graphics.drawImage(imagemFundo, 0, 0, 20, height, 0, 0, 20, height, null);
		graphics.drawImage(imagemFundo, width - 20, 0, width, height, imagemFundo.getWidth() -20, 0, imagemFundo.getWidth(), height, null);
		graphics.drawString(texto, (width - widthTexto) / 2, (height + heightTexto / 2) / 2);

		return imagem;
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
