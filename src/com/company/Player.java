package com.company;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player {

    public static final int STAND_STILL = 1,
                            RUNNING = 2,
                            JUMPING = 3,
                            DIE = 4;

    private static int state = STAND_STILL;
    private static int ground;
    private static int dinoUpY, dinoLeftX;
    private float speedX;
    private float speedY;

    private Rectangle rect;
    public int score = 0;

    private static Animation running;
    private static BufferedImage jumping;
    private static BufferedImage death;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;

    public Player() {

        rect = new Rectangle();
        running = new Animation(90);
        running.addFrame(new Resource().getResourceImage("./images/main-character1.png"));
        running.addFrame(new Resource().getResourceImage("./images/main-character2.png"));
        jumping = new Resource().getResourceImage("./images/main-character3.png");
        death = new Resource().getResourceImage("./images/main-character4.png");
        dinoLeftX = 50;
        ground = Ground.Ground_Axis_Y - jumping.getHeight() + 5; //give up pixel of dino
        dinoUpY = ground;

        try {
            jumpSound =  Applet.newAudioClip(new URL("file","","./images/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","./images/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","./images/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void showPlayer(Graphics g) {
        switch(state) {
            case STAND_STILL:
                g.drawImage(jumping, dinoLeftX, dinoUpY, null);
                break;
            case RUNNING:
                g.drawImage(running.getFrame(), dinoLeftX, dinoUpY, null);
                break;
            case JUMPING:
                g.drawImage(jumping, dinoLeftX, dinoUpY, null);
            case DIE:
                g.drawImage(death, dinoLeftX, dinoUpY, null);
                break;
        }
    }

    public void update() {
        running.updateFrame();
        if(dinoUpY >= ground) {
            dinoUpY = ground;
            state = RUNNING;
        } else {
            speedY += 0.4f;
            dinoUpY += speedY;
        }
    }

    public void jump() {
        if(dinoUpY >= ground) {
            if(jumpSound != null) {
                jumpSound.play();
                System.out.println("asdasd");
            }
            speedY = -7.5f;
            dinoUpY += speedY;
            state = JUMPING;
        }
    }

    public void die() {
        state = DIE;
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public Rectangle getDino() {
        rect = new Rectangle();
        rect.x = dinoLeftX;
        rect.y = dinoUpY;
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

    public void startRunning() {
        state = RUNNING;
    }

    private class DinoImages {

    }
}
