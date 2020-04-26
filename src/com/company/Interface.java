package com.company;

import javax.swing.JFrame;

public class Interface extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 500;
    private Main general;


    public Interface() {
        super("DINO - GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocation(400,200);
        setResizable(true);


        general = new Main();
        addKeyListener(general);
        add(general);
        setFocusable(true);
    }

    public void startGame() {
        setVisible(true);
        general.startGame();
    }

    public static void main(String[] args) {
        Interface frame = new Interface();
        frame.startGame();
    }
}
