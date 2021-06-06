package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ContentPane extends JPanel {

	private final List<Componente> componentes = new ArrayList<>();

	boolean add(Componente c) {
		if (componentes.contains(c)) return false;

		EventQueue.invokeLater(() -> {
			componentes.add(c);
			repaint();
		});
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Componente componente : componentes) {
			componente.paint((Graphics2D) g);
		}
	}

}
