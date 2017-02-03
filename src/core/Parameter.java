package core;

/*
 * Update log:
 * 2017-02-02:
 *      Change the input parameters. 
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class record the paths and directories used in this program.
 * 
 * @version 2016-08-24
 * @author Zhang Yufei
 *
 */
class Parameter {
    /**
     * The <code>BufferedImage</code> object of the original picture.
     */
    static BufferedImage original;

    /**
     * The directory store the ratio changed photos.
     */
    static String ratioPath;

    /**
     * The directory to store the size changed photos.
     */
    static String sizePath; 

    /**
     * The directory which contains element photo.
     */
    static File photo;
    
    /**
     * The directory to store the sample.
     */
    static File sample;

    /**
     * The file to store the result.
     */
    static File result;

    /**
     * The width and height of the element photo.
     */
    static int photoWidth = 50, photoHeight = 50;

    /**
     * The width and height of the source picture.
     */
    static int orgWidth = 1600, orgHeight = 1200;

    /**
     * The relation between the original picture and element photo. in pixel.
     * The <code>k</code> pixels in original picture will be related to
     * <code>l</code> pixels in element photo.
     */
    static int k = 1, l = 10;

    /**
     * The row and column number of the element photo.
     */
    static int row, column;

    /**
     * The width and height of the result picture.
     */
    static int resultWidth, resultHeight;

    /**
     * Init the parameter.
     */
    public static void init(String srcPath, String photoPath, String resultPath,
            int width, int height, int l) {
        try {
            original = ImageIO.read(new File(srcPath));
            ratioPath = resultPath + "\\ratio";
            File ratio = new File(ratioPath);
            ratio.mkdirs();
            
            sizePath = resultPath + "\\size";
            File size = new File(sizePath);
            size.mkdirs();

            photo = new File(photoPath);
            result = new File(resultPath + "\\dst.jpg");
            sample = new File(resultPath + "\\sample.jpg");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        orgWidth = original.getWidth();
        orgHeight = original.getHeight();
        
        photoWidth = width;
        photoHeight = height;

        Parameter.l = l;
        k = 1;

        column = (int) (orgHeight / (photoHeight * k / (double) l));
        row = (int) (orgWidth / (photoWidth * k / (double) l));

        resultWidth = orgWidth * l / k;
        resultHeight = orgHeight * l / k;
    }
}
