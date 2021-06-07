/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class ContentPane extends JPanel {

	private final List<Componente> componentes = new ArrayList<>();

	void add(Componente c) {
		if (componentes.contains(c)) return;

		EventQueue.invokeLater(() -> {
			componentes.add(c);
			repaint();
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Componente componente : componentes) {
			componente.paint((Graphics2D) g);
		}
	}

}
