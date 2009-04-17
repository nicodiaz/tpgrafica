package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulator implements Cloneable
{
	private BufferedImage imgSrc = null;

	public ImageManipulator(String fileName) throws IOException
	{

		// Preconditions
		if (fileName == null)
		{
			throw new IllegalArgumentException();
		}

		File imgFile = new File(fileName);
		imgSrc = ImageIO.read(imgFile);
	}

	public ImageManipulator(BufferedImage imgSrc)
	{
		// Preconditions
		if (imgSrc == null)
		{
			throw new IllegalArgumentException();
		}

		this.imgSrc = imgSrc;
	}

	public void applyGrayscale()
	{
		// TODO: Complete this function
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		WritableRaster dstWrite = imgSrc.copyData(null);

		BufferedImage imgDst = new BufferedImage(imgSrc.getWidth(), imgSrc.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		imgDst.setData(dstWrite);

		return (new ImageManipulator(imgDst));
	}

	/*
	 * Recover a single pixel value for any channel (RED GREEN OR BLUE). Return
	 * -1 in error case
	 */
	public int getPixelRGB(int x, int y, ColorElementType type)
	{

		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight()
				|| type == null || type == ColorElementType.HUE
				|| type == ColorElementType.SAT
				|| type == ColorElementType.BRIGHT)
		{
			throw new IllegalArgumentException();
		}

		int[] pixelData = new int[3];
		int result = -1;
		imgSrc.getData().getPixel(x, y, pixelData);

		switch (type)
		{
		case RED:
			result = pixelData[0];
			break;
		case GREEN:
			result = pixelData[1];
			break;
		case BLUE:
			result = pixelData[2];
			break;
		default:
			break;
		}

		return result;
	}

	/*
	 * Recover a single pixel value for any channel in HSB SYSTEN(HUE,
	 * SATURATION OR BRIGHT). Return -1 in error case
	 */
	public float getPixelHSB(int x, int y, ColorElementType type)
	{
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight()
				|| type == null || type == ColorElementType.RED
				|| type == ColorElementType.GREEN
				|| type == ColorElementType.BLUE)
		{
			throw new IllegalArgumentException();
		}

		int[] pixelDataRGB = new int[3];
		float[] pixelDataHSB = new float[3];
		imgSrc.getData().getPixel(x, y, pixelDataRGB);
		Color.RGBtoHSB(pixelDataRGB[0], pixelDataRGB[1], pixelDataRGB[2],
				pixelDataHSB);
		float result = -1;

		switch (type)
		{
		case HUE:
			result = pixelDataHSB[0];
			break;
		case SAT:
			result = pixelDataHSB[1];
			break;
		case BRIGHT:
			result = pixelDataHSB[2];
			break;
		default:
			break;
		}

		return result;
	}

	public int[] getPixelRGB(int x, int y)
	{
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight())
		{
			throw new IllegalArgumentException();
		}

		int[] pixelData = new int[3];
		imgSrc.getData().getPixel(x, y, pixelData);

		return pixelData;
	}

	public void setPixelRGB(int x, int y, int[] pixelData)
	{
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight()
				|| pixelData == null)
		{
			throw new IllegalArgumentException();
		}

		imgSrc.getRaster().setPixel(x, y, pixelData);

	}

	public void setPixelHSB(int x, int y, float[] pixelData)
	{
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight()
				|| pixelData == null)
		{
			throw new IllegalArgumentException();
		}

		int rgbData = Color.HSBtoRGB(pixelData[0], pixelData[1], pixelData[2]);

		imgSrc.setRGB(x, y, rgbData);

	}

	public float[] getPixelHSB(int x, int y)
	{
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight())
		{
			throw new IllegalArgumentException();
		}

		int[] pixelDataRGB = new int[3];
		float[] pixelDataHSB = new float[3];
		imgSrc.getData().getPixel(x, y, pixelDataRGB);
		Color.RGBtoHSB(pixelDataRGB[0], pixelDataRGB[1], pixelDataRGB[2],
				pixelDataHSB);

		return pixelDataHSB;
	}

	public void saveToFile(String fileName) throws IOException
	{

		// Preconditions
		if (fileName == null)
		{
			throw new IllegalArgumentException();
		}

		// Now we save the file
		ImageIO.write(imgSrc, "jpg", new File(fileName));

	}

	public int getWidth()
	{
		return imgSrc.getWidth();
	}

	public int getHeight()
	{
		return imgSrc.getHeight();
	}

}
