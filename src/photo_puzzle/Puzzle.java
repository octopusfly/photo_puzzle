package photo_puzzle;

import static photo_puzzle.Parameter.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class compute the puzzle of photo from photos in <b>sizePath</b>
 * 
 * @version 2016-08-24.
 * @author Zhang Yufei.
 *
 */
public class Puzzle {
	/**
	 * @param x
	 *            The x coordinate of the picture.
	 * @param y
	 *            The y coordinate of the picture.
	 * @param picture
	 *            The file name of the picture;
	 * @return The similarity of two picture;
	 */
	public static int compare(int x, int y, String picture) {
		try {
			File file = new File(sizePath + picture);
			BufferedImage image = ImageIO.read(file);
			int count = 0;
			for (int i = 0; i < 75; i++) {
				for (int j = 0; j < 75; j++) {
					int rgb1 = image.getRGB(i, j);
					int rgb2 = original.getRGB(5 * x + i / 15, 5 * y + j / 15);
					int r1 = rgb1 & 0x000000ff;
					int g1 = (rgb1 & 0x0000ff00) >> 8;
					int b1 = (rgb1 & 0x00ff0000) >> 16;
					int r2 = rgb2 & 0x000000ff;
					int g2 = (rgb2 & 0x0000ff00) >> 8;
					int b2 = (rgb2 & 0x00ff0000) >> 16;

					if (Math.abs(r2 - r1) < 10 && Math.abs(g2 - g1) < 10 && Math.abs(b2 - b1) < 10) {
						count++;
					}
				}
			}
			return count;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * This function combines the small picture into a big photo.
	 */
	public static void combine() {
		try {
			File file = new File(sizePath);
			File[] list = file.listFiles();
			boolean[] tag = new boolean[list.length];
			for (int i = 0; i < list.length; i++) {
				tag[i] = true;
			}

			BufferedImage dst = new BufferedImage(18000, 13500, BufferedImage.TYPE_3BYTE_BGR);

			for (int i = 0; i < 240; i++) {
				for (int j = 0; j < 180; j++) {
					System.out.println("Set photo in (" + i + ", " + j + ").");
					int max = 0;
					int max_index = -1;
					for (int k = 0; k < list.length; k++) {
						if (!tag[k]) {
							continue;
						}
						int r = compare(i, j, list[k].getName());
						if (max < r) {
							max = r;
							max_index = k;
						}
					}
					if (max_index == -1) {
						for (int k = 0; k < list.length; k++) {
							tag[k] = true;
						}
						for (int k = 0; k < list.length; k++) {
							if (!tag[k]) {
								continue;
							}
							int r = compare(i, j, list[k].getName());
							if (max < r) {
								max = r;
								max_index = k;
							}
						}
					}
					tag[max_index] = false;
					File f = new File(sizePath + list[max_index].getName());
					// System.out.println(sizePath + list[max_index]);
					BufferedImage b = ImageIO.read(f);
					for (int x = 0; x < 75; x++) {
						for (int y = 0; y < 75; y++) {
							int rgb = b.getRGB(x, y);
							dst.setRGB(i * 75 + x, j * 75 + y, rgb);
						}
					}
				}

			}
			File dstFile = new File("E:\\dst2.jpg");
			ImageIO.write(dst, "jpg", dstFile);
			System.out.println("finish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
