package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static com.company.Interface.HEIGHT;

public class Ground extends Canvas {

    private class GroundImage {
        BufferedImage image;
        int Axis_X;

        public GroundImage(BufferedImage image, int axis_X) {
            this.image = image;
            Axis_X = axis_X;
        }
    }

    public static int Ground_Axis_Y;
    private BufferedImage Ground_Image;
    private ArrayList<GroundImage> groundImageList;


    public Ground() {
        Ground_Axis_Y = (int) (HEIGHT - 0.25 * HEIGHT);
        try {
            Ground_Image = ImageIO.read(getClass().getResource("/home/samet/Desktop/Dino-Game/images/Ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        groundImageList = new ArrayList<GroundImage>();

        for( int i = 0 ; i  < 3 ; i ++) {
            GroundImage temp = new GroundImage(Ground_Image,0);
            groundImageList.add(temp);
        }
    }

    public void showGround(Graphics graphic){
        for ( int i = 0 ; i < groundImageList.size(); i++){
            graphic.drawImage(groundImageList.get(i).image,groundImageList.get(i).Axis_X,HEIGHT,null);
        }
    }

}
