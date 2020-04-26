package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Ground {

    private class GroundImage {
        BufferedImage image;
        int Axis_X;

        public GroundImage(BufferedImage image, int axis_X) {
            this.image = image;
            this.Axis_X = axis_X;
        }
    }

    public static int Ground_Axis_Y;
    private BufferedImage Ground_Image;
    private ArrayList<GroundImage> groundImageList;


    public Ground(int height) {
        Ground_Axis_Y = (int) (height - 0.25 * height);
        try {
            Ground_Image = ImageIO.read(getClass().getResource("./images/Ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        groundImageList = new ArrayList<GroundImage>();

        for( int i = 0 ; i  < 3 ; i ++) {
            GroundImage temp = new GroundImage(Ground_Image,0);
            groundImageList.add(temp);
        }
    }

    public void showGround(Graphics g){
        for ( int i = 0 ; i < groundImageList.size(); i++){
            g.drawImage(groundImageList.get(i).image, groundImageList.get(i).Axis_X, Ground_Axis_Y,null);
        }
    }
    public void update(){
        Iterator<GroundImage> loop = groundImageList.iterator();
        GroundImage firstOne = loop.next();

        firstOne.Axis_X -= 15;

        int previos = firstOne.Axis_X;

        while(loop.hasNext()){
            GroundImage next = loop.next();
            next.Axis_X = previos + Ground_Image.getWidth();
            previos = next.Axis_X;
        }

        if(firstOne.Axis_X < -Ground_Image.getWidth()){
            groundImageList.remove(firstOne);
            firstOne.Axis_X = previos + Ground_Image.getWidth();
            groundImageList.add(firstOne);
        }
    }
}
