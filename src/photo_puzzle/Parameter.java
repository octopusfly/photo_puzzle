package photo_puzzle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class record the paths and directories used in
 * this program.
 * 
 * @version 2016-08-24
 * @author Zhang Yufei
 *
 */
public class Parameter {
	/**
	 * The directory of the source photos used to 
	 * get the final photo.
	 */
	public static String srcPath = "E:\\src\\";
	
	/**
	 * The directory to store the ratio changed photos. 
	 */
	public static String ratioPath = "E:\\ratio\\";
	
	/**
	 * The director to store the size changed photos. 
	 */
	public static String sizePath = "E:\\size\\";
	
	/**
	 * The original photo.
	 */
	public static BufferedImage original = null;
	
	/**
	 * The sample file get from original photo.
	 */
	public static File sampleFile = new File("E:\\sample.jpg");
	
	/**
	 * The final result file.
	 */
	public static String resultFile = "E:\\puzzle.jpg";
	
	/**
	 * The ratio of the destination photo. The ratio of <b>width : height =
	 * ratio_A : ratio_B</b>.
	 */
	public static int ratio_A = 1;
	public static int ratio_B = 1;
	
	/**
	 * The width and height of the destination photo.
	 */
	public static int sizeWidth = 50;
	public static int sizeHeight = 50;
	
	/**
	 * The width and height of sample.
	 */
	public static int sampleWidth = 50;
	public static int sampleHeight = 50;

	/**
	 * The line and column of the sample.
	 */
	public static int sampleLine = 120;
	public static int sampleColumn = 160;
	
	/**
	 * Init the parameter.
	 */
	public static void init() {
		try {
			original = ImageIO.read(new File("E:\\org.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
