package photo_puzzle;

import static photo_puzzle.Parameter.*;

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
			int orgWidth = original.getWidth() / sampleColumn;
			int orgHeight = original.getHeight() / sampleLine;

			BufferedImage smp = new BufferedImage(sampleWidth * sampleColumn, sampleHeight * sampleLine, BufferedImage.TYPE_3BYTE_BGR);
			for (int i = 0; i < sampleColumn; i++) {
				for (int j = 0; j < sampleLine; j++) {
					System.out.println("Get the sample in coordinates: (" + i + ", " + j + ")");
					for (int x = 0; x < sampleWidth; x++) {
						for (int y = 0; y < sampleHeight; y++) {
							smp.setRGB(i * sampleWidth + x, j * sampleHeight + y,
									original.getRGB(i * orgWidth + orgWidth / 2, j * orgHeight + orgHeight / 2));
						}
					}
				}
			}

			ImageIO.write(smp, "jpg", sampleFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * The main test program.
	 */
	public static void main(String[] args) {
		getSample();
	}
}
