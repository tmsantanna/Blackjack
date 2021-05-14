package view;

import java.awt.*;

abstract class Componente {

	protected Frame frame;

	protected Componente(Frame frame) {
		this.frame = frame;
	}

	abstract void paint(Graphics2D g);

	//É executado quando o componente aparece na tela
	void onAdd() {
	}

	//É executado quando o componente é removido da tela
	void onRemove() {
	}

	//Adiciona o componente na tela
	void add() {
		if (frame.add(this)) {
			onAdd();
		}
	}

	//Remove o componente da tela
	void remove() {
		if (frame.remove(this)) {
			onRemove();
		}
	}

}
