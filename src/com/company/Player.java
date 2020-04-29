package com.company;

import javax.sound.sampled.AudioInputStream;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

public class Player {

    private static final int STAND_STILL = 1;
    private static final int RUNNING = 2;
    public static final int JUMPING = 3;
    private static final int DIE = 4;
    public int state = STAND_STILL;

    private float dinoUpY;
    private float dinoLeftX;
    private float speedX;
    private float speedY;

    private Rectangle rect;
    public static int reference;
    public int score = 0;
    public int high_score = 0;

    private static Animation running;
    private static BufferedImage jumping;
    private static BufferedImage death;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;

    public  File tempFile  =  new File("HighScore.txt");

    public Player() {
        rect = new Rectangle();
        running = new Animation(90);
        running.addFrame(new Resource().getResourceImage("./images/main-character1.png"));
        running.addFrame(new Resource().getResourceImage("./images/main-character2.png"));
        jumping = new Resource().getResourceImage("./images/main-character3.png");
        death = new Resource().getResourceImage("./images/main-character4.png");
        dinoLeftX = 50;
        reference = Ground.Ground_Axis_Y - jumping.getHeight() + 5; //give up pixel of dino
        dinoUpY = reference;


        System.out.println(tempFile.toString());
        boolean exists = tempFile.exists();
        System.out.println(exists);

        if(exists == true){
            try {
                FileReader reader = new FileReader("HighScore.txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                line = bufferedReader.readLine();
                high_score = Integer.parseInt(line);
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            try {
                System.out.println(tempFile.getName());
                tempFile.createNewFile();
                FileWriter myWriter = new FileWriter("HighScore.txt");
                myWriter.write(String.valueOf(high_score));
                myWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jumpSound =  Applet.newAudioClip(new URL("file","","./images/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","./images/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","./images/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void changeHighScore(String score){
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("HighScore.txt");
            myWriter.flush();
            myWriter.write(String.valueOf(score));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showPlayer(Graphics g) {
        switch(state) {
            case STAND_STILL:
                g.drawImage(jumping, (int) dinoLeftX, reference, null);
                break;
            case RUNNING:
                g.drawImage(running.getFrame(), (int) dinoLeftX, (int) dinoUpY, null);
                break;
            case JUMPING:
                g.drawImage(jumping, (int) dinoLeftX, (int) dinoUpY, null);
                break;
            case DIE:
                g.drawImage(death, (int) dinoLeftX, (int) dinoUpY, null);
                break;
        }
    }

    public void update() {
        running.updateFrame();
        if(dinoUpY >= reference) {
            state = RUNNING;
        } else {
            speedY += 0.35f;
            dinoUpY += speedY;
        }
    }

    public void jump() {
        if(jumpSound != null) {
            playJumpSound();
            System.out.println("asdasd");
        }
        speedY = -7.5f;
        dinoUpY += speedY;
        state = JUMPING;
    }

    public void playJumpSound() {
        jumpSound.play();
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public void die() {
        state = DIE;
    }

    public Rectangle getDino() {
        rect = new Rectangle();
        rect.x = (int) dinoLeftX;
        rect.y = (int) dinoUpY;
        rect.width = running.getFrame().getWidth();
        rect.height = running.getFrame().getHeight();
        return rect;
    }

    public void upScore() {
        score += 1;
        if(score % 5 == 0) {
            scoreUpSound.play();
        }
    }

    public void reset() {
        state = STAND_STILL;
    }

    private class DinoImages {

    }
}
