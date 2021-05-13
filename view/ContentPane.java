package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ContentPane extends JPanel {

	private List<Componente> componentes = new ArrayList<>();

	boolean add(Componente c) {
		if (componentes.contains(c)) return false;

		EventQueue.invokeLater(() -> {
			componentes.add(c);
			repaint();
		});
		return true;
	}

	boolean remove(Componente c) {
		if (!componentes.contains(c)) return false;

		EventQueue.invokeLater(() -> {
			componentes.remove(c);
			repaint();
		});
		return true;
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		for (Componente componente : componentes) {
			componente.paint((Graphics2D) g);
		}

	}

}
