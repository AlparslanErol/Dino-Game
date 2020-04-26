package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


class Main extends JPanel implements KeyListener, Runnable {

    public static int WIDTH;
    public static int HEIGHT;
    private Thread animator;

    private boolean running = false;
    private boolean gameOver = false;

    Ground ground;
    Cactus cactus;


    private int score;

    public Main() throws IOException {
        WIDTH = Interface.WIDTH;
        HEIGHT = Interface.HEIGHT;

        ground = new Ground(HEIGHT);
        cactus = new Cactus(WIDTH*2);

        score = 0;

        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString(Integer.toString(score), getWidth() / 2 - 5, 100);
        ground.showGround(g);
        cactus.showCactus(g);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println(e);
        if(e.getKeyChar() == ' ') {
            if(gameOver) reset();
            if (animator == null || !running) {
                System.out.println("Game starts");
                animator = new Thread(this);
                animator.start();
            }
        }
    }

    @Override
    public void run() {
        running = true;

        while(running) {
            updateGame();
            repaint();
            try {
                Thread.sleep(50);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateGame() {
        score += 1;
        cactus.update();
        ground.update();
    }
    public void reset() {
        score = 0;
        System.out.println("reset");
        gameOver = false;
    }
}