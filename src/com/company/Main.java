package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


class Main extends JPanel implements KeyListener, Runnable {

    public static int WIDTH;
    public static int HEIGHT;
    private Thread animator;

    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;
    private int gameState = START_GAME_STATE;


    private Ground ground;
    private Cactus cactus;
    private Player dino;

    private static BufferedImage replayButtonImage;
    private static BufferedImage gameOverButtonImage;


    public Main() {
        WIDTH = Interface.WIDTH;
        HEIGHT = Interface.HEIGHT;

        ground = new Ground(HEIGHT);
        cactus = new Cactus(WIDTH*2);
        dino = new Player();

        gameOverButtonImage = new Resource().getResourceImage("./images/gameover_text.png");
        replayButtonImage = new Resource().getResourceImage("./images/replay_button.png");
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString("SCORE = " + dino.score, getWidth()/2 - 70, getHeight()/4);

        switch (gameState) {
            case START_GAME_STATE:
                dino.showPlayer(g);
                ground.showGround(g);
                cactus.showCactus(g);
                break;
            case GAME_PLAYING_STATE:
            case GAME_OVER_STATE:
                ground.showGround(g);
                cactus.showCactus(g);
                dino.showPlayer(g);
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOverButtonImage, 200, 30, null);
                    g.drawImage(replayButtonImage, 283, 50, null);
                }
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            updateGame();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == ' ') {
            switch (gameState) {
                case START_GAME_STATE:
                    gameState = GAME_PLAYING_STATE;
                    break;
                case GAME_PLAYING_STATE:
                    dino.jump();
                    break;
                case GAME_OVER_STATE:
                    gameState = START_GAME_STATE;
                    reset();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void updateGame() {
        if (gameState == GAME_PLAYING_STATE) {
            ground.update();
            dino.update();
            cactus.update();
            if (cactus.isCollision()) {
                dino.playDeadSound();
                gameState = GAME_OVER_STATE;
                dino.die();
            }
        }
    }

    public void reset() {
        System.out.println("reset");
    }

    public void startGame() {
        animator = new Thread(this);
        animator.start();
    }
}