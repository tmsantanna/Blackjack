package view;

import java.awt.*;

abstract class Componente {

	abstract void paint(Graphics2D g);

	//É executado quando o componente aparece na tela
	void onAdd() {
	}

	//É executado quando o componente é removido da tela
	void onRemove() {
	}

	//Adiciona o componente na tela
	void add() {
		if (GUI.pegaGUI().add(this)) {
			onAdd();
		}
	}

	//Remove o componente da tela
	void remove() {
		if (GUI.pegaGUI().remove(this)) {
			onRemove();
		}
	}

}
