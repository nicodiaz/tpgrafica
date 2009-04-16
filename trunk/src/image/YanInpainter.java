package image;

public class YanInpainter implements Inpaintable
{

	private boolean considerCurrentIteration = false;

	/**
	 * @param considerCurrentIteration
	 *            the considerCurrentIteration to set
	 */
	public YanInpainter(boolean considerCurrentIteration)
	{
		this.considerCurrentIteration = considerCurrentIteration;
	}

	/**
	 * @param considerCurrentIteration
	 *            the considerCurrentIteration to set
	 */
	public void setConsiderCurrentIteration(boolean considerCurrentIteration)
	{
		this.considerCurrentIteration = considerCurrentIteration;
	}

	public ImageManipulator inpaint(ImageManipulator img, Mask mask)
	{
		if (img == null || mask == null || img.getWidth() != mask.getWidth()
				|| img.getHeight() != mask.getHeight())
		{
			throw new IllegalArgumentException();
		}

		ImageManipulator result = null;
		int ringWidth, ringHeight, rowIdx, colIdx;

		try
		{
			result = (ImageManipulator) img.clone();
		} catch (CloneNotSupportedException e)
		{
			// Just for debug
			e.printStackTrace();
			return null;
		}

		for (ringHeight = mask.getHeight(), ringWidth = mask.getWidth(); ringHeight > 0
				&& ringWidth > 0; ringHeight--, ringWidth--)
		{
			// We iterate through the rectangle
			System.out.println("Ancho: " + ringWidth + " Alto: " + ringHeight);
			// top side
			for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++)
			{
				if (mask.isMarked(colIdx, rowIdx))
				{
					result.setPixelHSB(colIdx, rowIdx, inpaintPixel(img, mask,
							colIdx, rowIdx));
				}

			}
			// right side
			for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++)
			{
				if (mask.isMarked(colIdx, rowIdx))
				{
					result.setPixelHSB(colIdx, rowIdx, inpaintPixel(img, mask,
							colIdx, rowIdx));
				}
			}
			// bottom side
			for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--)
			{
				if (mask.isMarked(colIdx, rowIdx))
				{
					result.setPixelHSB(colIdx, rowIdx, inpaintPixel(img, mask,
							colIdx, rowIdx));
				}
			}
			// left side
			for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--)
			{
				if (mask.isMarked(colIdx, rowIdx))
				{
					result.setPixelHSB(colIdx, rowIdx, inpaintPixel(img, mask,
							colIdx, rowIdx));
				}
			}
			// mark the ring as NoMark in order to be considered as information
			// later.
			if (!considerCurrentIteration)
			{
				unmarkRing(mask, ringWidth, ringHeight);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param img
	 *            The image which pixel will be inpainted.
	 * @param mask
	 *            The mask to be applied.
	 * @param x
	 *            The x coordinate of the pixel to be inpainted.
	 * @param y
	 *            The y coordinate of the pixel to be inpainted.
	 * @return A three element array with the color to be applied to the pixel
	 *         in RGB.
	 */
	private float[] inpaintPixel(ImageManipulator img, Mask mask, int x, int y)
	{
		int[][] directions = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 },
				{ -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 } };
		float[] accum = new float[3];
		float[] color;
		int sumCount = 0;
		int newX;
		int newY;

		for (int[] direction : directions)
		{
			newX = x + direction[0];
			newY = y + direction[1];

			if (newX >= 0 && newX < img.getWidth() && newY >= 0
					&& newY < img.getHeight())
			{
				if (!mask.isMarked(newX, newY))
				{
					sumCount++;
					color = mask.getPixelHSB(newX, newY);
					accum[0] += color[0];
					accum[1] += color[1];
					accum[2] += color[2];
				}
			}
		}

		accum[0] /= sumCount;
		accum[1] /= sumCount;
		accum[2] /= sumCount;

		// if the considerCurrentIteration is set to true we must unmark the
		// pixel in the mask
		if (considerCurrentIteration)
		{
			mask.unMark(x, y);
		}

		return accum;

	}

	private void unmarkRing(Mask mask, int ringWidth, int ringHeight)
	{
		int rowIdx, colIdx;

		// top side
		for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++)
		{
			mask.unMark(colIdx, rowIdx);
		}
		// right side
		for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++)
		{
			mask.unMark(colIdx, rowIdx);
		}
		// bottom side
		for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--)
		{
			mask.unMark(colIdx, rowIdx);
		}
		// left side
		for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--)
		{
			mask.unMark(colIdx, rowIdx);
		}
	}
}
