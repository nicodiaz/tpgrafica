package image;

import java.io.IOException;

public class Mask extends ImageManipulator
{

	private int[] maskMark = new int[3];
	private int[] maskNoMark = new int[3];

	/**
	 * 
	 * @param fileName
	 * @param red
	 * @param green
	 * @param blue
	 * @throws IOException
	 */
	public Mask(String fileName, int[] markColor, int[] noMarkColor)
			throws IOException
	{
		super(fileName);

		// Preconditions
		if (markColor == null || noMarkColor == null)
		{
			throw new IllegalArgumentException();
		}

		maskMark[0] = markColor[0];
		maskMark[1] = markColor[1];
		maskMark[2] = markColor[2];
		maskNoMark[0] = noMarkColor[0];
		maskNoMark[1] = noMarkColor[1];
		maskNoMark[2] = noMarkColor[2];
	}

	/**
	 * This function indicate if a pixel in a specific location must be inpainted or not
	 * 
	 * @param x
	 *            The x-coordinate
	 * @param y
	 *            The y-coordinate
	 * @return true if the pixel is unmasked (and must be inpainted) or false
	 *         otherwise
	 */
	public boolean isMarked(int x, int y)
	{

		// Precondition
		if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight())
		{
			throw new IllegalArgumentException();
		}

		int[] pixel = this.getPixelRGB(x, y);

		return (pixel[0] == maskMark[0] && pixel[1] == maskMark[1] && pixel[2] == maskMark[2]);
	}

	public void unMark(int x, int y)
	{
		// Precondition
		if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight())
		{
			throw new IllegalArgumentException();
		}
		
		this.setPixelRGB(x, y, maskNoMark);
	}
}
