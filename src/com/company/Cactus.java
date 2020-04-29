package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

        Rectangle getObstacle() {
            Rectangle obstacle = new Rectangle();
            obstacle.x = Axis_X;
            obstacle.y = Axis_Y;
            obstacle.width = image.getWidth();
            obstacle.height = image.getHeight();

            return obstacle;
        }
    }

    public static int Cactus_Axis_X;
    public static int cactus_Reference;
    private BufferedImage Cactus_Image;
    private ArrayList<CactusImage> cactusImageList;
    private Player dino;
    private int speed;

    public Cactus(int position, Player dino){
        this.dino = dino;
        Cactus_Axis_X = position;
        cactus_Reference = Cactus_Axis_X;
        cactusImageList = new ArrayList<CactusImage>();
        String name = "./images/Cactus-";
        speed = 5;

        for(int i = 0 ; i < 5 ; i++){
            name = name + (i+1) + ".png";
            try {
                Cactus_Image = ImageIO.read(getClass().getResource(name));
            } catch (IOException e) {
                e.printStackTrace();
            }

            CactusImage obj = new CactusImage(Cactus_Image,Cactus_Axis_X,Ground.Ground_Axis_Y - Cactus_Image.getHeight() + 5);
            cactusImageList.add(obj);
            Cactus_Axis_X += (int)(Math.random()*((900-400)+1))+400;
            name = "./images/Cactus-";
        }
    }

    public void showCactus(Graphics g){
        for ( int i = 0 ; i < cactusImageList.size(); i++){
            g.setColor(Color.black);
            g.drawImage(cactusImageList.get(i).image, cactusImageList.get(i).Axis_X, cactusImageList.get(i).Axis_Y,null);
        }
    }

    public boolean isCollision() {
        for (int i = 0; i < cactusImageList.size(); i++){
            if (dino.getDino().intersects(cactusImageList.get(i).getObstacle())){
                return true;
            }
        }
        return false;
    }

    public void update(){
        int rand_interval = (int)(Math.random()*((900-400)+1))+400;
        for (int i = 0 ; i < cactusImageList.size() ; i++){
            cactusImageList.get(i).Axis_X -= speed;
        }
        if(cactusImageList.get(0).Axis_X < -cactusImageList.get(0).image.getWidth()){
            CactusImage temp = new CactusImage(cactusImageList.get(0).image,cactusImageList.get(0).Axis_X,cactusImageList.get(0).Axis_Y);
            temp.Axis_X = cactusImageList.get(cactusImageList.size()-1).Axis_X + rand_interval;
            cactusImageList.remove(0);
            cactusImageList.add(temp);
        }
    }
    public void reset() {
        int rand_interval = (int)(Math.random()*((900-400)+1))+400;
        cactusImageList.clear();
        Cactus_Axis_X = cactus_Reference;
        String name = "./images/Cactus-";
        for(int i = 0 ; i < 5 ; i++){
            name = name + (i+1) + ".png";
            try {
                Cactus_Image = ImageIO.read(getClass().getResource(name));
            } catch (IOException e) {
                e.printStackTrace();
            }

            CactusImage obj = new CactusImage(Cactus_Image,Cactus_Axis_X,Ground.Ground_Axis_Y - Cactus_Image.getHeight() + 5);
            cactusImageList.add(obj);
            Cactus_Axis_X += rand_interval;
            name = "./images/Cactus-";
        }

    }

}
