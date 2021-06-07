/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import java.awt.*;

abstract class Componente {

	protected final Frame frame;

	protected Componente(Frame frame) {
		this.frame = frame;
		frame.add(this);
	}

	abstract void paint(Graphics2D g);

}
