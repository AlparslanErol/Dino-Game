package pack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * @author ALPARSLAN
 *
 * Resource class is to read BufferedImage Type imput images from given source.
 * @code getResourceImage() => has path source as input parameter.
 * - Creating null BufferedImage type image.
 * - Read Image from file and assign on to BufferedImage Type variable.
 * - Return BufferedImage Type image
 * */
public class Resource {

	/**
	 * Look Explanation Above!
	 * @code getResourceImage(String path)
	 * @param path
	 * @return
	 */
	public static BufferedImage getResourceImage(String path) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}