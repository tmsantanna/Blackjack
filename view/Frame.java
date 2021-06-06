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

    boolean add(Componente c) {
        return ((ContentPane) getContentPane()).add(c);
    }

}
