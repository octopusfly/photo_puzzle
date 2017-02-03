package core;

import static core.Parameter.column;
import static core.Parameter.k;
import static core.Parameter.l;
import static core.Parameter.orgHeight;
import static core.Parameter.orgWidth;
import static core.Parameter.original;
import static core.Parameter.photoHeight;
import static core.Parameter.photoWidth;
import static core.Parameter.row;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class get the sample of original photo.
 * 
 * @version 2016-08-24.
 * @author Zhang Yufei.
 *
 */
public class Sample {
    /**
     * This function get the sample of the original photo.
     */
    public static void getSample() {
        try {
            BufferedImage smp = new BufferedImage(Parameter.resultWidth,
                    Parameter.resultHeight, BufferedImage.TYPE_3BYTE_BGR);
            System.out.println(orgWidth * l / k + " " + orgHeight * l / k);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    System.out.println("Get the sample in coordinates: (" + i
                            + ", " + j + ")");
                    for (int x = 0; x < photoWidth; x++) {
                        for (int y = 0; y < photoHeight; y++) {
                            smp.setRGB(i * photoWidth + x, j * photoHeight + y,
                                    original.getRGB(
                                            i * orgWidth / row
                                                    + orgWidth / 2 / row,
                                            j * orgHeight / column
                                                    + orgHeight / 2 / column));
                        }
                    }
                }
            }

            ImageIO.write(smp, "jpg", Parameter.sample);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
