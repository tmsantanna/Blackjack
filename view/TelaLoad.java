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

    private final JScrollPane scroll;

    private final JPanel grid = new JPanel();

    TelaLoad(Consumer<Mestre> onLoad, Runnable onCancel) {
        setTitle("Load");
        grid.setBackground(Color.BLACK);
        getContentPane().setBackground(Color.BLACK);

        this.onLoad = onLoad;

        //mostrar todos os saves em um ScrollPane?

        getContentPane().add(new Botao("VOLTAR", 380, 600, onCancel));

        grid.setLayout(new BoxLayout(grid, BoxLayout.Y_AXIS));

        scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(6);
        scroll.setBounds(0, 0, GUI.LARGURA - 6, 580);
        scroll.setBorder(null);
        getContentPane().add(scroll);

        mostraSaves();
    }

    void atualizaSaves(boolean manterPosicaoScroll) {
        grid.removeAll();
        mostraSaves();

        // força o scroll a atualizar, para evitar um bug
        int posicaoScroll = scroll.getVerticalScrollBar().getValue();
        scroll.getVerticalScrollBar().setValue(1);
        scroll.getVerticalScrollBar().setValue(manterPosicaoScroll ? posicaoScroll : 0);
    }

    private void mostraSaves() {
        grid.add(Box.createRigidArea(new Dimension(0, 20)));

        for (String pegaSave : pegaSaves()) {
            grid.add(novoBotao(pegaSave));
            grid.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private Botao novoBotao(String fileName) {
        int indicePonto = fileName.lastIndexOf('.');
        String texto = indicePonto < 0 ? fileName : fileName.substring(0, indicePonto);

        Botao botao = new Botao(texto, 0, 0, () -> loadFile(fileName));
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
