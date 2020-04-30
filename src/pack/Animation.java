package pack;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ALPARSLAN
 *
 * @code ANIMATION CLASS IS REPRESENTING THE SOME LISTED BufferedImage TYPE IMAGES THAT CAN HAVE SOME PROPERTIES...
 * This class has parameter delta time. And update another image by checking system time has enough delta time.
 *
 * ANIMATION CLASS HAS DELTA TIME TO REQUIRE SOME TIME INTERVAL TO UPDATE NEXT FRAME
 * */
public class Animation {

    private List<BufferedImage> list;
    private long deltaTime;
    private int currentFrame = 0;
    private long previousTime;

    /**
     * ANMIATION CLASS CONSTRUCTOR
     * @code Animation() method is a constructor method for Animation class.
     * Has Delta Time and BufferedImage type ArrayList
     *
     * @param deltaTime
     * NO RETURN
     */
    public Animation(int deltaTime) {
        this.deltaTime = deltaTime;
        list = new ArrayList<BufferedImage>();
        previousTime = 0;
    }

    /**
     * ANIMATION CLASS METHOD...
     * @code updateFrame() => Update next image frame if required delta time gathered.
     *
     * NO INPUT PARAMETER
     * NO RETURN
     */
    public void updateFrame() {
        // If now - previous >= delta time ==> If have required delta time
        if (System.currentTimeMillis() - previousTime >= deltaTime) {
            // Update Next Frame As CurrentFrame
            currentFrame++;
            // If end of the list then turn it back to initial.
            if (currentFrame >= list.size()) {
                currentFrame = 0;
            }
            // update previous frame
            previousTime = System.currentTimeMillis(); // Give system time in milliseconds
        }
    }

    /**
     * ANIMATION CLASS METHOD...
     * @code addFrame() => add BufferedImage Type new frame on the ArrayList
     * @code list.add(image)
     *
     * @param image
     * NO RETURN
     */
    public void addFrame(BufferedImage image) {
        list.add(image);
    }

    /**
     * ANIMATION CLASS METHOD...
     * @code getFrame() => Return BufferedImage Type Frame to get current using frame in the program.
     * This method is invoked in Player Class showPlayer() to draw current image on window and rect() methods to keep current
     *
     * NO INPUT PARAMETER
     * @return list.get(currentFrame)
     */
    public BufferedImage getFrame() {
        return list.get(currentFrame);
    }
}