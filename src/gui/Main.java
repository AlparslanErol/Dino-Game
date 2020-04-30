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

/**
 * @author ALPARSLAN
 *
 * MAIN CLASS ==> Engine of the Dinosaur Game
 * MAIN CLASS has inheritance from...
 * @code JPanel class => one of the subclass of the Component => Container => JComponent
 *
 * MAIN CLASS has used and call override methods from...
 * @code KeyListener => Has key methods to override.
 * @code Runnable => Has method run() which also used from Thread. By the thread object we can implement interface.
 *
 * Main class HAS 3 Subclasses : CACTUS, GROUND, PLAYER
 * Main class HAS THREAD class with thread object.
 * Input images identified as BufferedImage class type to read them as BufferedImage type later.
 *
 * Game has 3 Stage:
 * 1.) Start Game Stage    : In this stage, dino does not run. Position just before the running start. (INITIAL)
 * 2.) Game Playing State  : In this stage, dino is running relatively :)
 * 3.) Game Over Stage     : Every thread, program checks the collision between dino and cactus with a reference of
 *                           Rectangle Class. If it is the case pursuing this stage with stop running.
 */
public class Main extends JPanel implements KeyListener, Runnable {

    // MAIN WINDOW CONFIGS
	private static final long serialVersionUID = 1L;
	public static int WIDTH;
    public static int HEIGHT;

    // MAIN GAME STATE CONFIGS
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;
    private int gameState = START_GAME_STATE;

    // MAIN GAME_OVER_STATE IMAGES CONFIGS
    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    // MAIN ASSOCIATED AND INHERITED CLASSES CONFIGS
    private Thread thread;
    private Ground ground;
    private Cactus cactus;
    private Player dino;


    /**
     * CONSTRUCTOR OF MAIN CLASS
     * Initialize sub class objects and read the game over images.
      */
    public Main() {
        WIDTH = Interface.WIDTH;
        HEIGHT = Interface.HEIGHT;

        thread = new Thread(this);
        ground = new Ground(HEIGHT);
        dino = new Player();
        cactus = new Cactus(WIDTH*2, dino);

        gameOverButtonImage = Resource.getResourceImage("./images/gameover_text.png");
        replayButtonImage = Resource.getResourceImage("./images/replay_button.png");
    }


    /**
     * Using Java Graphics to draw initial update on the game for every single GAME_STATE.
     * By calling repaint in run() method calls this method for every single iteration in thread. So we are allowed
     * to update drawing image in the window in every thread iteration with specified sleep time.
     *
     * Overridden from JComponent class.
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g); //Call upper class paint method.
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        g.drawString("QUIT -> Q", 10,20);
        g.drawString("SCORE = " + dino.score, getWidth()/2 + 200, getHeight()/4-25);
        g.drawString("High Score  = " + dino.high_score, getWidth()/2 + 200, getHeight()/4);

        // CHECK GAME_STATE AND DECIDE PAINT PROCESS
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


    /**
     * Overridden method run() from Interface Runnable
     * Thread class also implements runnable interface which means Thread itself has an override run() method.
     * So we are allowed to use Thread class into Runnable run() method.
     * This method is called for every single iteration.
     * Every single iteration has delta time as 10 milliseconds
     *
     * @code updateGame() ==> In every thread we control and trigger Main class method updateGame() to Up Date our game.
     * @code repaint() ==> This method is overridden inherited from JPanel but also from COMPONENT class to Update Graphics ==> paint(Graphics g)
     * @see #update(Graphics)
     */
    @Override
    public void run() {
        while (true) {
            updateGame(); //Main class method
            repaint(); // Inherited from JPanel ==> COMPONENT
            try {
                Thread.sleep(10); // DELTA TIME BETWEEN THREAD ITERATIONS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method is overridden from...
     * @code KeyListener interface.
     * Chcek if any key TYPED.
     * @param e -- any key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }


    /**
     * This method is overridden from...
     * @code KeyListener interface.
     * Chcek if any key PRESSED.
     * In this game only key that allowed to use is ' '.
     * For every GAME_STATE, written key cases.
     * - IF IN START_GAME_STATE AND PRESSED ' ' => Dino stars running and GAME_STATE => GAME_PLAYING_STATE
     * - IF IN GAME_PLAYING_STATE AND PRESSED ' ' => Dino jumps and no state change
     * - IF IN GAME_OVER_STATE AND PRESSED ' ' => Dino has initial posiiton and GAME_STATE => START_GAME_STATE
     *
     * - IF IN GAME_OVER_STATE AND PRESSED 'L' => Dino Game has been closed by System with stopGame() method.
     * @param e -- any key
     */
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
        if (gameState == GAME_OVER_STATE)
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                stopGame();
            }
    }


    /**
     * This method is overridden from...
     * @code KeyListener interface.
     * Chcek if any key RELEASED.
     * @param e -- any key
     */
    @Override
    public void keyReleased(KeyEvent e) { }


    /**
     * Method for Main class.
     * Has no parameter and no return
     * This method updates game process but to make it every single thread (iteration)...
     * This method is called from...
     * @code overridden run() method. So we are allowed to update game process in every single thread iteration.
     *
     * If any collision detected between player and obstacle, dino has sound, GAME_STATE => GAME_OVER_STATE and...
     * Check if the current score obtained by user is bigger than the best score keeping in .txt file...
     * If it is the case, update value in the file, if not continue.
     */
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


    /**
     * Method for Main class.
     * Has no input parameter and no return.
     * This method is used for re-setting the game configuration.
     */
    public void reset() {
        System.out.println("Reset");
        cactus.reset();
        dino.reset();
        dino.score = 0;
        dino.counter = 0;
    }


    /**
     * @code startGame()
     * @code stopGame()
     *
     * Methods for Main class.
     * Have no input parameter and no return.
     * This startGame() method is used Thread -> start() method which used to trigger the thread run().
     * In thread we do not call run() method directly. Just call start() method and iteration starts till it is ended.
     * When you invoke the program and get the game window. It means thread has already started even the dino has start_game_state.
     *
     * You can stop it either use stopGame() method from Main class or just click on the exit window button the program has been finished.
     */
    public void startGame() {
        thread.start();
    }
    private void stopGame() {
        System.exit(1);
    }
}