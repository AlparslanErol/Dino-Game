package gui;

import javax.swing.JFrame;

/**
 * @author ALPARSLAN
 *
 * Top class of this game which extends...
 * @code JFrame class => One of the subclass of Component=> Container => Window
 * General specification of the game window are held in this class.
 * Interface class HAS An another class that suppose to the game engine.
 * HAS Main class with an object of 'manager'.
 */
public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800;
    public static int HEIGHT = 500;
    private Main manager;

    /**
     * Constructor...
     * Configure general concepts of the JFrame window.
     * Create an object from Main class and add this object to the window.
     */
    public Interface() {
        super("DINO - GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finish program click on exit button.
        setSize(WIDTH,HEIGHT); // WIDTH and HEIGHT CONFIGURATION
        setLocation(400,200); // SET WINDOW LOCATION ONT THE SCREEN
        setResizable(false); // DEFAULT (FALSE), CASE TRUE => YOU CAN RESIZE THE WINDOW AS YOU WISH.
        manager = new Main(); // CREATE MAIN OBJECT THAT USE THREAD RUNNABLE
        addKeyListener(manager); //ADD KEY LISTENER BY OVERRRIDE METHODS IN KEYLISTENER INTERFACE
        add(manager); // ADD PROCESS ON THE SCREEN AND DISPLAY
    }

    /**
     * This method is to trigger the game engine. Also called the THREAD with startGame() method.
     * And set visible process maden by manager object on our game window.
     */
    public void startGame() {
        setVisible(true);
        manager.startGame();
    }

    public static void main(String[] args) {
        Interface frame = new Interface();
        frame.startGame();
    }
}
