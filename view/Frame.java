/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import javax.swing.*;

class Frame extends JFrame {

    Frame() {
        setSize(GUI.LARGURA, GUI.ALTURA);
        setResizable(false);
        setContentPane(new ContentPane());
        setLayout(null);
        setLocationRelativeTo(null);
    }

    void add(Componente c) {
        ((ContentPane) getContentPane()).add(c);
    }

}
