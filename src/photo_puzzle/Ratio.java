package photo_puzzle;

import static photo_puzzle.Parameter.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class changes the ratio of photo
 * 
 * @version 2016-8-24
 * @author Zhang Yufei
 *
 */
public class Ratio {
	/*
	 * The number of photo, this is the number of photos which have been changed
	 * the ratio.
	 */
	private static int photoCount = 1;

	/**
	 * This function change the ratio of one photo given the file name.
	 * 
	 * @param photo
	 *            The photo file to deal.
	 * @return None.
	 */
	private static void changeRatio(File photo) {
		try {
			System.out.println("Change the ratio of photos to width : height = " + ratio_A + " : " + ratio_B);
			System.out.println("Start dealing photo: " + photo.getName());

			BufferedImage image = ImageIO.read(photo);
			int width = image.getWidth();
			int height = image.getHeight();

			/*
			 * We'll either cut the width or height. So first we should decide
			 * what we should cut width or height.
			 */
			int new_width = width / ratio_A < height / ratio_B ? width : height / ratio_B * ratio_A;
			int new_height = height / ratio_B < width / ratio_A ? height : width / ratio_A * ratio_B;

			File file2 = new File(ratioPath + photoCount + ".jpg");
			BufferedImage image2 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_3BYTE_BGR);

			for (int i = (width - new_width) / 2; i < width / 2 + new_width / 2; i++) {
				for (int j = (height - new_height) / 2; j < height / 2 + new_height / 2; j++) {
					image2.setRGB(i - (width - new_width) / 2, j - (height - new_height) / 2, image.getRGB(i, j));
				}
			}

			ImageIO.write(image2, "jpg", file2);
			System.out.println("Change the ratio of picture:" + photo.getName() + " Finish.");
			photoCount++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function deals with all photos in the <b>srcPath</b> changing the
	 * photos' ratio to <b> ratio_A : ratio_B </b>
	 */
	public static void changeRatio() {
		File src = new File(srcPath);
		File[] fileList = src.listFiles();
		for (File file : fileList) {
			changeRatio(file);
		}

		System.out.println("Finish. The number of photos is " + photoCount + ".");
	}

	/**
	 * The main test program.
	 */
	public static void main(String[] args) {
		changeRatio();
	}
}
