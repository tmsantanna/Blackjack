package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Botao extends Componente {

	private int x, y, largura, altura;

	private String texto;

	private Color corFundo, corTexto;

	private Runnable acao;

	private boolean pressionado = false, ativo = true;

	private final MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {

			if (ativo && e.getX() >= x && e.getX() < x + largura && e.getY() >= y && e.getY() < y + altura) {
				pressionado = true;
				GUI.pegaGUI().repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!ativo || !pressionado) return;

			if (e.getX() >= x && e.getX() < x + largura && e.getY() >= y && e.getY() < y + altura) {
				acao.run();
			}

			pressionado = false;
			GUI.pegaGUI().repaint();
		}
	};

	public Botao(int x, int y, int largura, int altura, String texto, Color corFundo, Color corTexto, Runnable acao) {
		this.x = x;
		this.y = y;
		this.largura = largura;
		this.altura = altura;
		this.texto = texto;
		this.corFundo = corFundo;
		this.corTexto = corTexto;
		this.acao = acao;
	}

	void setAtivo(boolean b) {
		ativo = b;
		GUI.pegaGUI().repaint();
	}

	boolean pegaAtivo() {
		return ativo;
	}

	@Override
	void onAdd() {
		GUI.pegaGUI().getContentPane().addMouseListener(mouseAdapter);
	}

	@Override
	void onRemove() {
		GUI.pegaGUI().getContentPane().removeMouseListener(mouseAdapter);
	}

	@Override
	public void paint(Graphics2D g) {
		if (!ativo) {
			g.setColor(Color.DARK_GRAY);
		} else if (pressionado) {
			g.setColor(corFundo.darker());
		} else {
			g.setColor(corFundo);
		}
		g.fillRect(x, y, largura, altura);

		int strX = (largura - g.getFontMetrics().stringWidth(texto)) / 2 + x;
		int strY = (altura - g.getFontMetrics().getHeight()) / 2 + y + g.getFontMetrics().getAscent();
		g.setColor(corTexto);
		g.drawString(texto, strX, strY);
	}

}
