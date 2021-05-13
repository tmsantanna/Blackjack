package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

	private static GUI gui;

	private GUI() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(new ContentPane());
	}

	public static GUI pegaGUI() {
		if (gui == null) {
			gui = new GUI();
		}
		return gui;
	}

	public static void main(String[] args) {
		GUI g = pegaGUI();

		List<Botao> l = new ArrayList<>();

		new Botao(100, 100, 100, 30, "Add", Color.RED, Color.BLACK, () -> {
			int i = l.size();

			Botao b = new Botao(100 + 120 * (i % 5), 150 + 50 * (i / 5), 100, 30, "Botao " + i, Color.RED, Color.BLACK, () -> {
				System.out.println(i);
			});
			l.add(b);
			b.add();
		}).add();

		new Botao(220, 100, 100, 30, "Remove", Color.RED, Color.BLACK, () -> {
			l.remove(l.size() - 1).remove();
		}).add();

		new Botao(340, 100, 100, 30, "Toggle", Color.RED, Color.BLACK, () -> {
			Botao b = l.get(l.size() - 1);
			b.setAtivo(!b.pegaAtivo());
		}).add();

		g.setVisible(true);
	}

	boolean add(Componente c) {
		return ((ContentPane) getContentPane()).add(c);
	}

	boolean remove(Componente c) {
		return ((ContentPane) getContentPane()).remove(c);
	}

}
