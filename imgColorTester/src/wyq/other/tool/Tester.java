package wyq.other.tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Tester {

	public void run(String[] args) throws IOException {
		File inputImgFile = new File("C:\\test.jpg");
		BufferedImage bi = ImageIO.read(inputImgFile);

		int[] rgb = new int[3];
		long[] sum = new long[] { 0, 0, 0 };
		long count = 0;

		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		System.out.println("width=" + width + ",height=" + height + ".");
		System.out.println("minx=" + minx + ",miniy=" + miny + ".");

		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				count++;
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				sum[0] += rgb[0];
				sum[1] += rgb[1];
				sum[2] += rgb[2];
				// System.out.println("i=" + i + ",j=" + j + ":(" + rgb[0] + ","
				// + rgb[1] + "," + rgb[2] + ")");
			}
		}

		int[] avg = new int[3];
		avg[0] = Math.round(sum[0] / count);
		avg[1] = Math.round(sum[1] / count);
		avg[2] = Math.round(sum[2] / count);

		System.out
				.println("avg:(" + avg[0] + "," + avg[1] + "," + avg[2] + ")");

		BufferedImage out = new BufferedImage(255, 255,
				BufferedImage.TYPE_INT_RGB);
		int avgPixel = (avg[0] << 16) | (avg[1] << 8) | (avg[2]);

		System.out.println("avgPixel:" + avgPixel);

		for (int x = out.getMinX(); x < out.getWidth(); x++) {
			for (int y = out.getMinY(); y < out.getHeight(); y++) {
				out.setRGB(x, y, avgPixel);
			}
		}

		FileOutputStream outstream = new FileOutputStream("C:\\out.jpg");
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outstream);
		encoder.encode(out);
		outstream.close();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Tester().run(args);
	}

}
