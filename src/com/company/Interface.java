package com.company;

import javax.swing.*;
import java.awt.*;

public class Interface extends JPanel {

    JFrame mainPage = new JFrame("DINOOO - GAAMEE");
    public static int WIDTH = 800;
    public static int HEIGHT = 500;
    Ground temp = new Ground();

    public void paint(Graphics g){
        super.paint(g);
        temp.showGround(g);
    }

    public void createWindow(){
        mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = mainPage.getContentPane();


        container.setLayout(new BorderLayout());

        //container.add(gamePanel, BorderLayout.CENTER);

        mainPage.setSize(WIDTH, HEIGHT);
        mainPage.setResizable(false);
        mainPage.setVisible(true);





    }


}
