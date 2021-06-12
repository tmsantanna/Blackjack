/*
Bruno Messeder dos Anjos
Thiago Melcop Sant’Anna
 */

package view;

import model.Mestre;
import controller.Controller;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.io.*;

class TelaLoad extends Frame {

    private final Consumer<Mestre> onLoad;

    private final JPanel saves = new JPanel();

    TelaLoad(Consumer<Mestre> onLoad, Runnable onCancel) {
        setTitle("Load");
        saves.setBackground(Color.BLACK);
        getContentPane().setBackground(Color.BLACK);

        this.onLoad = onLoad;

        getContentPane().add(new Botao("VOLTAR", 380, 600, onCancel));

        saves.setLayout(null);

        JScrollPane scroll = new JScrollPane(saves);
        scroll.getVerticalScrollBar().setUnitIncrement(6);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(0, 0, GUI.LARGURA - 6, 580);
        scroll.setBorder(null);
        getContentPane().add(scroll);

        atualizaSaves();
    }

    void atualizaSaves() {
        saves.removeAll();

        int y = 20;

        for (String pegaSave : pegaSaves()) {
            saves.add(novoBotao(pegaSave, y));
            y += 60;
        }

        saves.setPreferredSize(new Dimension(saves.getWidth(), y));
    }

    private Botao novoBotao(String fileName, int y) {
        int indicePonto = fileName.lastIndexOf('.');
        String texto = indicePonto < 0 ? fileName : fileName.substring(0, indicePonto);

        Botao botao = new Botao(texto, 0, 0, () -> loadFile(fileName));
        botao.setLocation((GUI.LARGURA - botao.getWidth()) / 2, y);

        botao.setAlignmentX(CENTER_ALIGNMENT);
        return botao;
    }

    private void loadFile(String fileName) {
    	Mestre m;
    	File root = new File(Mestre.SAVE_PATH);
    	fileName = root+"/"+fileName;
        try {
            FileInputStream arquivo = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(arquivo);
            m = (Mestre) in.readObject();
            in.close();
            arquivo.close();
         } catch (IOException i) {
        	 System.out.println("Arquivo não encontrado");
            i.printStackTrace();
            return;
         } catch (ClassNotFoundException c) {
            System.out.println("Classe Mestre não foi encontrada");
            c.printStackTrace();
            return;
         }
        onLoad.accept(m);//Da load no Mestre
        return;
    }

    private static List<String> pegaSaves() {
        List<String> saves = new ArrayList<>();

        File root = new File(Mestre.SAVE_PATH);

        String[] arquivos = root.list();

        if (arquivos == null) return saves;

        for (String arquivo : arquivos) {
            if (new File(root, arquivo).isFile()) {
                saves.add(arquivo);
            }
        }

        return saves;
    }

}
