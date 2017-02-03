package core;

import static core.Parameter.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class used to change the size of photo.
 * 
 * @version 2016-08-24.
 * @author Zhang Yufei.
 *
 */
public class Size {

    /*
     * The number of photo, this is the number of photos which have been changed
     * the ratio.
     */
    private static int photoCount = 1;

    /**
     * This function change the size of photo.
     * 
     * @param photo
     *            The photo file to deal.
     * @return None.
     */
    public static void changeSize(File photo) {
        System.out
                .println("Start change the size of photo: " + photo.getName());
        try {
            BufferedImage image = ImageIO.read(photo);
            int orgWidth = image.getWidth();
            int orgHeight = image.getHeight();

            File file2 = new File(sizePath + "\\" + photoCount + ".jpg");
            BufferedImage image2 = new BufferedImage(photoWidth, photoHeight,
                    BufferedImage.TYPE_3BYTE_BGR);

            for (int i = 0; i < orgWidth; i += orgWidth / photoWidth) {
                for (int j = 0; j < orgHeight; j += orgHeight / photoHeight) {
                    image2.setRGB(i * photoWidth / orgWidth,
                            j * photoHeight / orgHeight, image.getRGB(i, j));
                }
            }

            ImageIO.write(image2, "jpg", file2);
            System.out.println("Change the size of picture:" + photo.getName()
                    + " Finish.");
            photoCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function deals with all photos in the <b>ratioPath</b> changing the
     * photos' ratio to <b>width * height</b>;
     */
    public static void changeSize() {
        File src = new File(ratioPath);
        File[] fileList = src.listFiles();
        for (File file : fileList) {
            changeSize(file);
        }

        System.out
                .println("Finish. The number of photos is " + photoCount + ".");
    }
}
