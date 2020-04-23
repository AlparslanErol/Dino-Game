package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Interface {

    JFrame mainPage = new JFrame("DINOOO - GAAMEE");
    public static int WIDTH = 800;
    public static int HEIGHT = 500;


    public void createWindow(){
        mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = mainPage.getContentPane();

        Main general = new Main();
        general.addKeyListener(general);
        general.setFocusable(true);

        container.setLayout(new BorderLayout());

        container.add(general, BorderLayout.CENTER);

        mainPage.setSize(WIDTH, HEIGHT);
        mainPage.setResizable(false);
        mainPage.setVisible(true);

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { new Interface().createWindow(); }
        });
    }
}
