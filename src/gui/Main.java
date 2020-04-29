package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pack.Resource;
import classes.Cactus;
import classes.Ground;
import classes.Player;


public class Main extends JPanel implements KeyListener, Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int WIDTH;
    public static int HEIGHT;

    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;
    private int gameState = START_GAME_STATE;

    private static BufferedImage replayButtonImage;
    private static BufferedImage gameOverButtonImage;

    private Thread animator;
    private Ground ground;
    private Cactus cactus;
    private Player dino;


    public Main() {
        WIDTH = Interface.WIDTH;
        HEIGHT = Interface.HEIGHT;

        animator = new Thread(this);
        ground = new Ground(HEIGHT);
        dino = new Player();
        cactus = new Cactus(WIDTH*2, dino);

        gameOverButtonImage = Resource.getResourceImage("./images/gameover_text.png");
        replayButtonImage = Resource.getResourceImage("./images/replay_button.png");
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        g.drawString("SCORE = " + dino.score, getWidth()/2 + 200, getHeight()/4-25);
        g.drawString("High Score  = " + dino.high_score, getWidth()/2 + 200, getHeight()/4);

        switch (gameState) {
            case START_GAME_STATE:
                dino.showPlayer(g);
                ground.showGround(g);
                cactus.showCactus(g);
                break;
            case GAME_PLAYING_STATE:
                dino.showPlayer(g);
                ground.showGround(g);
                cactus.showCactus(g);
                break;
            case GAME_OVER_STATE:
                ground.showGround(g);
                cactus.showCactus(g);
                dino.showPlayer(g);
                if (gameState == GAME_OVER_STATE) {
                    g.drawImage(gameOverButtonImage, getWidth()/2 - 100, getHeight()/2 - 50, null);
                    g.drawImage(replayButtonImage, getWidth()/2 - 20, getHeight()/2 - 30, null);
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
                    if(dino.state != Player.JUMPING)
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
    public void keyReleased(KeyEvent e) { }

    public void updateGame() {
        if (gameState == GAME_PLAYING_STATE) {
            ground.update();
            dino.update();
            cactus.update();
            dino.upScore();
            if (cactus.isCollision()) {
                dino.playDeadSound();
                gameState = GAME_OVER_STATE;
                dino.die();
                if(dino.score > dino.high_score){
                    dino.high_score = dino.score;
                    dino.changeHighScore(String.valueOf(dino.score));
                }
            }
        }
    }

    public void reset() {
        System.out.println("Reset");
        cactus.reset();
        dino.reset();
        dino.score = 0;
        dino.counter = 0;
    }

    public void startGame() {
        animator.start();
    }
}
