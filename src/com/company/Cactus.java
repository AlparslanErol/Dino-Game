package com.company;

import com.company.Ground;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Cactus {

    private class CactusImage {
        BufferedImage image;
        int Axis_X;
        int Axis_Y;

        public CactusImage(BufferedImage image, int axis_X,int Axis_Y) {
            this.image = image;
            this.Axis_X = axis_X;
            this.Axis_Y = Axis_Y;
        }
    }

    public static int Cactus_Axis_X;
    private BufferedImage Cactus_Image;
    private ArrayList<CactusImage> cactusImageList;
    private int interval;

    public Cactus(int position){
        Cactus_Axis_X = position;
        cactusImageList = new ArrayList<CactusImage>();
        String name = "./images/Cactus-";
        interval = 50;



        for(int i = 0 ; i < 5 ; i++){
            name = name + (i+1) + ".png";
            System.out.println("-----------> " + name);

            try {
                Cactus_Image = ImageIO.read(getClass().getResource(name));
            } catch (IOException e) {
                e.printStackTrace();
            }

            CactusImage obj = new CactusImage(Cactus_Image,Cactus_Axis_X,Ground.Ground_Axis_Y - Cactus_Image.getHeight() + 5);
            cactusImageList.add(obj);
            Cactus_Axis_X += interval;
            name = "./images/Cactus-";
        }
    }

    public void showCactus(Graphics g){
        for ( int i = 0 ; i < cactusImageList.size(); i++){
            g.drawImage(cactusImageList.get(i).image, cactusImageList.get(i).Axis_X, cactusImageList.get(i).Axis_Y,null);
        }
    }
}
