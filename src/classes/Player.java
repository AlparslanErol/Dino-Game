package classes;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import pack.Animation;
import pack.Resource;


/**
 * @author ALPARSLAN
 *
 * PLAYER CLASS IS REPRESENTING THE RUNNING CHARACTER. IN THIS GAME IT REPRESENT DINOSAUR AS A PLAYER.
 * PLAYER HAS 4 SITUATION:
 * 1. PLAYER IS IN STAND-STILL SITUATION (INITIAL SITUATION),
 * 2. PLAYER IS IN RUNNING SITUATION,
 * 3. PLAYER IS IN JUMPING SITUATION,
 * 4. PLAYER IS IN DIE SITUATION.
 * THANKS TO THESE KEEPING SITUATIONS, WE CAN DEFINE AND GIVE SPECIFICATION FROM CASE BY CASE EASILY.
 *
 * TO KEEP PLAYER'S POITION ON THE FRAME...
 * @code RECTANGLE CLASS IS USED TO CHECK IF ANY COLLISION OCCURS BETWEEN PLAYER AND OBSTACLES.
 *
 * TO KEEP HIGHEST SCORE THAT OBTAINED FROM BEGINNING HishScore.txt file is used.
 * After gameState => GAME_OVER_STATE, compare current value and highes in file. If it is the case use...
 * @code changeHighScore() method to update high score keeping in .txt file to. For this reason...
 * @code File() class is used to read and write .txt file.
 *
 * TO KEEP PLAYER IMAGES ON DIFFERENT SITUATION,
 * @code BufferedImage class is used. By the way, left & right foot images are animated by using...
 * @code Animation(delta time) class in package 'pack'.
 * This class has parameter delta time. And update another image by checking
 * system time has enough delta time.
 * updateFrame() method is called in thread, if enough delta time detected => nextImage.
 *
 * TO KEEP PLAYER SOUNDS FOR DIFFERENT ACTIONS,
 * @code AudioClip() class is used. Read .wav files with URL to use them in...
 * IN JUMPING SITUATION,
 * IN DEAD SITUATION,
 * IN UP_SCORE SITUATION => SOUNDS ON AFTER RUNNER REACHES SCORE IN EVERY 500 POINT.
 */
public class Player {

    // PLAYER SITUATIONS
    private static final int STAND_STILL = 1;
    private static final int RUNNING = 2;
    public static final int JUMPING = 3;
    private static final int DIE = 4;
    public int state = STAND_STILL;

    // PLAYER LOCALIZATION AND SPEED CONFIGS
    private float dinoUpY;
    private float dinoLeftX;
    private float speedY;
    private Rectangle rect;
    public static int reference; // KEEP INITIAL POSITION OF PLAYER BY USING IN reset() method.

    // PLAYER SCORE VARIABLE CONFIGS
    public int counter;
    public int score = 0;
    public int high_score = 0; // USED TO KEEP HIGH SCORE IN A .TXT FILE
    public  File tempFile;


    // PLAYER IMAGES CONFIGS
    private static Animation running;
    private static BufferedImage jumping;
    private static BufferedImage death;

    // PLAYER SOUNDS CONFIGS
    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;


    /**
     * @code Player() -> CONSTRUCTOR METHOD,
     *
     * INITIALIZE RECTANGLE OBJECT,
     * INITIALIZE IMAGES,
     * INITIALIZE ANIMATION OBJECT TO ADD RIGHT LEFT FOOT PLAYER,
     * INITIALIZE FILE OBJECT TO KEEP HIGHEST SCORE,
     * INITIALIZE AUDIOCLIP OBJECTS TO KEEP PLAYER SOUNDS,
     *
     */
    public Player() {
        // RECT Configs
        rect = new Rectangle();

        // Animation Configs
        running = new Animation(90);
        running.addFrame(Resource.getResourceImage("images/main-character1.png"));
        running.addFrame(Resource.getResourceImage("images/main-character2.png"));

        // Jump and Dead Images Configs
        jumping = Resource.getResourceImage("images/main-character3.png");
        death = Resource.getResourceImage("images/main-character4.png");

        // Player localization configs
        dinoLeftX = 50;
        reference = Ground.Ground_Axis_Y - jumping.getHeight() + 5; //give up pixel of dino
        dinoUpY = reference;

        // File Configs
        tempFile  =  new File("HighScore.txt");
        System.out.println(tempFile.toString());
        boolean exists = tempFile.exists();
        System.out.println(exists);

        // IF FILE EXIST
        if(exists == true){
            // USE TRY CATCH
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
         // IF FILE DOES NOT EXIST
        }else {
            // USE TRY CATCH
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

        // PLAYER SOUNDS CONFIG WITH TRY CATCH
        try {
            jumpSound =  Applet.newAudioClip(new URL("file","","images/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","images/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","images/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    /**
     * PLAYER CLASS METHOD...
     * @code changeHighScore() => Supposed to update value in HighScore.txt file if required.
     * @code FileWriter Class is used to write on it.
     * @param score
     */
    public void changeHighScore(String score){
        // FileWriter Object
        FileWriter myWriter = null;
        // Write with TRY/CATCH
        try {
            myWriter = new FileWriter("HighScore.txt");
            // Clear File
            myWriter.flush();
            // Write on File
            myWriter.write(String.valueOf(score));
            // Close File
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * PLAYER CLASS METHOD...
     * @code showPlayer() => Supposed to update Graphics of Player.
     * This method is called from Main Class paint() method which overridden from JComponent Class.
     * @param g
     * NO RETURN
     */
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


    /**
     * PLAYER CLASS METHOD...
     * @code update() => Supposed to update Player specifications in every thread.
     * This method is called from Main Class updateGame() method.
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void update() {
        // Update lef right foot frame if it is required.
        running.updateFrame();
        // To keep player up to ground reference.
        if(dinoUpY >= reference) {
            state = RUNNING;
            // If up to the ground then it means, player needs to go down to the reference point.
        } else {
            // Update y axis frame and speedY in every thread iteration.
            speedY += 0.35f;
            dinoUpY += speedY;
        }
    }


    /**
     * PLAYER CLASS METHOD...
     * @code jump() => Supposed to decrease player's y axis frame. So it looks like to jump :)
     * This method is invoked from Main Class updateGame() method when...
     * GAME_STATE => GAME_PLAYING_STATE and KEY PRESSED => ' '
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void jump() {
        // If jump and file exist than sounds on!
        if(jumpSound != null) {
            playJumpSound();
        }

        // On jumping action, Y-Axis frames need to be decrease to go higher. Update speedY -7.5f, update Y frame of Player
        speedY = -7.5f;
        dinoUpY += speedY;
        // Set Player Situation as JUMPING;
        state = JUMPING;
    }


    /**
     * PLAYER CLASS METHOD...
     * @code getDino() => Supposed to return an object of RECTANGLE class to keep player localization and
     * Check if any collision has been occured.
     *
     * This method is invoked from Cactus Class isCollision() method when...
     * Keeping Rect object of Player and Cactus has been collided.
     * @code Rectangle intersect() method has been used.
     *
     * NO INPUT PARAMETER
     * @return
     */
    public Rectangle getDino() {
        rect = new Rectangle();
        rect.x = (int) dinoLeftX;
        rect.y = (int) dinoUpY;
        rect.width = running.getFrame().getWidth();
        rect.height = running.getFrame().getHeight();
        return rect;
    }


    /**
     * PLAYER CLASS METHOD...
     * @code upScore() => Supposed to increase score as the runner reaches as distance.
     * It is written on the window every MOO(10) score update.
     * UpSound is playing every MOD(500) score update.
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void upScore() {
        counter += 1;
        if(counter%10 == 0)
            score = counter;
        if (score != 0)
	        if(score % 500  == 0) {
	            scoreUpSound.play();
	        }
    }


    /**
     * PLAYER CLASS METHOD...
     * @code reset() => Supposed to RE-SET player specification when
     * GAME_STATE -> GAME_OVER STATE AND PRESSED ' '
     * SET PLAYER SITUATION-> STAND_STILL
     *
     * It is written on the window every MOO(10) score update.
     * UpSound is playing every MOD(500) score update.
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void reset() {
        state = STAND_STILL;
    }


    /**
     * PLAYER CLASS METHOD...
     * @code playJumpSound() => Supposed to play jumoSound.wav
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void playJumpSound() {
        jumpSound.play();
    }


    /**
     * PLAYER CLASS METHOD...
     * @code playDeadSound() => Supposed to play deadSound.wav
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void playDeadSound() {
        deadSound.play();
    }


    /**
     * PLAYER CLASS METHOD...
     * @code die() => Supposed to make player situation => DIE
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void die() {
        state = DIE;
    }
}