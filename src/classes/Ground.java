package classes;


import pack.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author ALPARSLAN
 *
 * GROUND CLASS IS REPRESENTING THE PATH THAT PLAYER RUNNING ON IT.
 * TO GET INFINITE PATH WITH ONE IMAGE FILE, AN ARRAYLIST KEPT WITH REFERENCE X-AXIS AS AN ANOTHER CLASS...
 * @code GroundImage Class is used to keep ground images an ArrayList with...
 * @param image Object,
 * @param Axis_X to Reference X-Axis pixel.
 * Only  x-axis is keeping, because to get infinite path with ground image we need to slide Ground.png file on every thread.
 * So there is nothing to do with reference y axis.
 */
public class Ground {

    /**
     * Internal Subclass of Groun Class => class GroundImage ()
     * To Keep 3 Ground image on an ArrayList and iterate them in every thread to get infinite ground pathway.
     *
     * @param image
     * @param Axis_X
     */
    private class GroundImage {
        // Take BufferImage Type to read image file and put it on ArrayList
        BufferedImage image;
        // Keep only X axis. No need Y axis for every image on list. Keep one Y reference enough.
        int Axis_X;

        // CONSTRUCTOR
        public GroundImage(BufferedImage image, int axis_X) {
            this.image = image;
            this.Axis_X = axis_X;
        }
    }

    // KEEP CONSTANT Y POSITION
    public static int Ground_Axis_Y;
    // USED TO READ IMAGE FROM SOURCE DIRECTORY
    private BufferedImage Ground_Image;
    // USED ITERATION OF IMAGES TO GET INFINITE WAY
    private ArrayList<GroundImage> groundImageList;

    /**
     * Constructor
     *
     * @param height => Has parameter because, it is given height of game frame from main class
     */
    public Ground(int height) {
        // Initialize Y reference pixel and keep it without using.
        Ground_Axis_Y = (int) (height - 0.25 * height);

        // Read image from source
        Ground_Image = Resource.getResourceImage("./images/Ground.png");

        // Add 3 times same image on the list to iterate and slide future.
        groundImageList = new ArrayList<GroundImage>();
        for( int i = 0 ; i  < 3 ; i ++) {
            GroundImage temp = new GroundImage(Ground_Image,0);
            groundImageList.add(temp);
        }
    }

    /**
     * Draw ground images on frame.
     * Invoked from main class -> paint(Graphics g) method.
     * @param g
     */
    public void showGround(Graphics g){
        for ( int i = 0 ; i < groundImageList.size(); i++){
            g.drawImage(groundImageList.get(i).image, groundImageList.get(i).Axis_X, Ground_Axis_Y,null);
        }
    }

    /**
     * Ground Class Method:
     * @code update() -> Suppose to update ground image object in every thread
     * This method invoked in main class -> updateGame() method.
     * Images are sliding on the x axis without any change on the y axis.
     *
     * @code if (firstOne.Axis_X < - Ground_Image.getWidth()) {}
     * Check if first image on list is totally outside of the frame
     * firstOne.Axis_X < -Ground_Image.getWidth() => This will not be true since first image is totally outside.
     *
     * If it is the case:
     * -> Remove first image from the ArrayList
     * -> Take First image's x position at the end of the last one
     * -> Add First image at the end of the list.
     *
     * Now first image is the last image.
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void update(){
        Iterator<GroundImage> loop = groundImageList.iterator();
        GroundImage firstOne = loop.next();
        firstOne.Axis_X -= 5;
        int previous = firstOne.Axis_X;

        // Take second end of the first and third at the end of the second.
        while(loop.hasNext()){
            GroundImage next = loop.next(); // Take the second image
            next.Axis_X = previous + Ground_Image.getWidth(); // set second image at the end of the first
            previous = next.Axis_X; // keep x reference to use next image's previous x position
        }

        // Check if first image on list is totally outside of the frame
        if(firstOne.Axis_X < -Ground_Image.getWidth()){
            groundImageList.remove(firstOne);
            firstOne.Axis_X = previous + Ground_Image.getWidth(); // now previous is keep last one's x axis.
            groundImageList.add(firstOne);
        }
    }
}
