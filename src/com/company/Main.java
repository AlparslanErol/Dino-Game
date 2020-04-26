package com.company;

import java.awt.Color;
import java.awt.Graphics;
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
    private boolean isKeyPressed;

    private boolean running = false;
    private boolean gameOver = false;

    Ground ground;
    Cactus cactus;
    Player dino;

    private static BufferedImage replayButtonImage;
    private static BufferedImage gameOverButtonImage;

    private int score;

    public Main() {
        WIDTH = Interface.WIDTH;
        HEIGHT = Interface.HEIGHT;

        ground = new Ground(HEIGHT);
        cactus = new Cactus(WIDTH*2);
        dino = new Player();
        gameOverButtonImage = new Resource().getResourceImage("./images/gameover_text.png");
        replayButtonImage = new Resource().getResourceImage("./images/replay_button.png");

        score = 0;

        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

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
                g.setColor(Color.BLACK);
                g.drawString("HI " + dino.score, 500, 20);
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOverButtonImage, 200, 30, null);
                    g.drawImage(replayButtonImage, 283, 50, null);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void run() {

        running = true;
        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsed;
        long endProcessGame;

        int msSleep;
        int nanoSleep;

        while (true) {
            updateGame();
            repaint();
            endProcessGame = System.nanoTime();
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int) (elapsed / 1000000);
            nanoSleep = (int) (elapsed % 1000000);
            if (msSleep <= 0) {
                lastTime = System.nanoTime();
                continue;
            }
            try {
                Thread.sleep(msSleep, nanoSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTime = System.nanoTime();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case START_GAME_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = GAME_PLAYING_STATE;
                    }
                    break;
                case GAME_PLAYING_STATE:
                    dino.jump();
                    break;
                case GAME_OVER_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = GAME_PLAYING_STATE;
                        reset();
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
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
        score = 0;
        System.out.println("reset");
        gameOver = false;
    }

    public void startGame() {
        animator = new Thread(this);
        animator.start();
    }
}