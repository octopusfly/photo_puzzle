package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Zhang Yufei
 * @Time 2016-07-10
 *
 */
public class Main {

	public static String srcPath = "E:\\zyc_photo\\";
	public static String ratioPath = "E:\\1-1\\";
	public static String sizePath = "E:\\min\\";
	public static String sampleFile = "E:\\sample.jpg";
	public static int photoCount = 1;
	public static BufferedImage orginal;
	public static boolean[] tag;

	/**
	 * This function change the ratio of photo (width : height) to 16:9.
	 * 
	 * @param photoName
	 *            The name of photo to deal.
	 * @return None.
	 */
	public static void changeRatio(String photoName) {
		System.out.println("Start dealing photo: " + photoName);
		try {
			File file = new File(srcPath + photoName);
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth();
			int height = image.getHeight();
			int new_height = width > height ? height : width;// width / 16 * 9;
			int new_width = width > height ? height : width;// height / 9 * 16;

			if (new_height > height) {
				new_height = height;
			}
			if (new_width > width) {
				new_width = width;
			}

			File file2 = new File(ratioPath + photoCount + ".jpg");
			BufferedImage image2 = new BufferedImage(new_width, new_height, BufferedImage.TYPE_3BYTE_BGR);

			for (int i = (width - new_width) / 2; i < width / 2 + new_width / 2; i++) {
				for (int j = (height - new_height) / 2; j < height / 2 + new_height / 2; j++) {
					image2.setRGB(i - (width - new_width) / 2, j - (height - new_height) / 2, image.getRGB(i, j));
				}
			}

			ImageIO.write(image2, "jpg", file2);
			System.out.println("Change the ratio of picture:" + photoName + "Finish.");
			photoCount++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function change the size of photo.
	 * 
	 * @param photoName
	 *            The name of photo to deal.
	 * @return None.
	 */
	public static void changeSize(String photoName) {
		System.out.println("Start dealing photo: " + photoName);
		try {
			File file = new File(ratioPath + photoName);
			BufferedImage image = ImageIO.read(file);
			int width = image.getWidth();
			int height = image.getHeight();

			File file2 = new File(sizePath + photoCount + ".jpg");
			BufferedImage image2 = new BufferedImage(75, 75, BufferedImage.TYPE_3BYTE_BGR);

			for (int i = 0; i < width; i += width / 75) {
				for (int j = 0; j < height; j += height / 75) {
					image2.setRGB(i * 75 / width, j * 75 / height, image.getRGB(i, j));
				}
			}

			ImageIO.write(image2, "jpg", file2);
			System.out.println("Change the size of picture:" + photoName + "Finish.");
			photoCount++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
					int rgb2 = orginal.getRGB(5 * x + i / 15, 5 * y + j / 15);
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
			tag = new boolean[list.length];
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

	/**
	 * This function get the sample of the original photo.
	 * 
	 * @param None.
	 * @return None.
	 */
	public static void sample() {
		BufferedImage smp = new BufferedImage(320, 320, BufferedImage.TYPE_3BYTE_BGR);
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				System.out.println("(" + i + ", " + j + ")");
				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 10; y++) {
						smp.setRGB(i * 10 + x, j * 10 + y, orginal.getRGB(i * 3 + 1, j * 3 + 1));
					}
				}
			}
		}
		File file = new File(sampleFile);
		try {
			ImageIO.write(smp, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			orginal = ImageIO.read(new File("E:\\org.jpg"));
			 sample();
//			File ratioFile = new File(ratioPath);
//			File[] list = ratioFile.listFiles();
//			for (int i = 0; i < list.length; i++)
//				changeSize(list[i].getName());
//			 combine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
