package view;

import javax.swing.*;

class Frame extends JFrame {

    Frame() {
        setSize(GUI.LARGURA, GUI.ALTURA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new ContentPane());
        setLayout(null);
    }

    boolean add(Componente c) {
        return ((ContentPane) getContentPane()).add(c);
    }

    boolean remove(Componente c) {
        return ((ContentPane) getContentPane()).remove(c);
    }

}
