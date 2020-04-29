package gui;

import javax.swing.JFrame;

public class Interface extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800;
    public static int HEIGHT = 500;
    private Main manager;


    public Interface() {
        super("DINO - GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocation(400,200);
        setResizable(true);


        manager = new Main();
        addKeyListener(manager);
        add(manager);
        setFocusable(true);
    }

    public void startGame() {
        setVisible(true);
        manager.startGame();
    }

    public static void main(String[] args) {
        Interface frame = new Interface();
        frame.startGame();
    }
}
