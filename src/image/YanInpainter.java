package image;

/**
 * @author Hari
 *
 */
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

	
	
	/**
	 * 
	 * ATENTION: When this method returns, the mask is MODIFIED.
	 * TODO: Implement clone en Mask Class.
	 * 
	 * @param img
	 *            The image to be inpainted
	 * @param mask
	 *            The mask to be used in the inpainted zone (wich Zone?? - Header zone!!)
	 *            
	 * @return The image modified, inpaint applied.
	 */
	public ImageManipulator inpaint(ImageManipulator img, Mask mask)
	{
		if (img == null || mask == null || img.getWidth() != mask.getWidth()
				|| img.getHeight() != mask.getHeight())
		{
			throw new IllegalArgumentException();
		}

		ImageManipulator result = null;

		try
		{
			result = (ImageManipulator) img.clone();
		} catch (CloneNotSupportedException e)
		{
			// Just for debug
			e.printStackTrace();
			return null;
		}

		int x, y, ring = 0;

		// We count the number of rings
		int min = Math.min(result.getWidth(), result.getHeight());
		int ringCount = min / 2;

		int imgHeight = img.getHeight();
		int imgWidth = img.getWidth();
		int ringHeight;
		int ringWidth;

		// Borrar esta linea:
		int[] pixelToSave = new int[3];
		
		// At first, the first ring matches with the image dimensions.
		ring = 0;
		ringHeight = imgHeight;
		ringWidth = imgWidth;
		while(ring < ringCount)
		{
			for (x = ring; x < ringWidth; x++)
			{
				for (y = ring; y < ringHeight; y++)
				{
					if (pixelInRing(x, y, ring, ringWidth, ringHeight) && mask.isMarked(x, y))
					{
//						inpaintPixel(result, mask, x, y);
						pixelToSave = inpaintUsingRGB(result, mask, x, y);				
						if (pixelToSave != null)
						{
							result.setPixelRGB(x, y, pixelToSave);
						}
					}
				}
			}
			
			// Now we must mark the circle in the mask
			// Podria mejorarse esto...
			for (x = ring; x < ringWidth; x++)
			{
				for (y = ring; y < ringHeight; y++)
				{
					if (pixelInRing(x, y, ring, ringWidth, ringHeight) && mask.isMarked(x, y))
					{
						mask.unMark(x, y);
					}
				}
			}
			
			// We must set the next ring limits
			ring++;			
			ringWidth = imgWidth - ring;
			ringHeight = imgHeight - ring;
		}
		
		return result;
	}

	/**
	 * @param x
	 *            The X coordinate of the pixel
	 * @param y
	 *            The Y coordinate of the pixel
	 * @param ring
	 *            The number of the working ring
	 * @param width
	 *            The ring width
	 * @param height
	 *            The ring height
	 * @return True if the pixel is contained in the ring, false otherwise
	 */
	private boolean pixelInRing(int x, int y, int ring, int width, int height)
	{
		// Preconditions
		if (x < 0 || y < 0 || ring < 0 || width < 0 || height < 0)
		{
			throw new IllegalArgumentException();
		}
		
		if (x == ring || x == (width - 1))
		{
			return (y >= ring && y < height)? true : false;
		}
		
		if (y == ring || y == (height - 1))
		{
			return (x >= ring && x < width)? true : false;
		}
		
		// if reach here, cannot be inside a ring
		return false;		
	}

	/**
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
		// Preconditions
		if (img == null || mask == null || x < 0 || y < 0)
		{
			throw new IllegalArgumentException();
		}
		
		
		int[][] directions = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, 
			{ -1, 1 }, { 0, 1 } };
		float[] accum = {0.0F, 0.0F, 0.0F};
		float[] color;
		int sumCount = 0;
		int newX, newY;
		int width = img.getWidth();
		int height = img.getHeight();

		for (int[] direction : directions)
		{
			newX = x + direction[0];
			newY = y + direction[1];

			if (newX >= 0 && newX < width && newY >= 0	&& newY < height)
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

		// If sumCount is zero, no pure pixel was used, and must return null 
		if (sumCount == 0)
		{
			return null;
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
		
		// Now we can set the pixel
		img.setPixelHSB(x, y, accum);
		
//		System.out.println("Inpaintee el pixel: (" + x + ", " + y + ").");

		return accum;

	}

//	private void unmarkRing(Mask mask, int ringWidth, int ringHeight)
//	{
//		int rowIdx, colIdx;
//
//		// top side
//		for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++)
//		{
//			mask.unMark(colIdx, rowIdx);
//		}
//		// right side
//		for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++)
//		{
//			mask.unMark(colIdx, rowIdx);
//		}
//		// bottom side
//		for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--)
//		{
//			mask.unMark(colIdx, rowIdx);
//		}
//		// left side
//		for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--)
//		{
//			mask.unMark(colIdx, rowIdx);
//		}
//	}
	
	
	private int[] inpaintUsingRGB(ImageManipulator img, Mask mask, int x, int y)
	{
		// Preconditions
		if (img == null || mask == null || x < 0 || y < 0)
		{
			throw new IllegalArgumentException();
		}
		
		int[] pixelAtXY = img.getPixelRGB(x, y);
		
		int[][] directions = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, 
			{ -1, 1 }, { 0, 1 } };
		int[] accum = {0, 0, 0};
		int[] color;
		int newX,newY;
		
		// sumCount has the number of pure pixel used
		int sumCount = 0;
		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int[] direction : directions)
		{
			newX = x + direction[0];
			newY = y + direction[1];
			
			if (newX >= 0 && newX < width && newY >= 0	&& newY < height)
			{
				if (!mask.isMarked(newX, newY))
				{
					// Is a pure pixel
					sumCount++;
					color = img.getPixelRGB(newX, newY);
					accum[0] += color[0];
					accum[1] += color[1];
					accum[2] += color[2];
				}
			}
		}
		
		// If sumCount is zero, no pure pixel was used, and must return null 
		if (sumCount == 0)
		{
			return null;
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
		
		// Now we can set the pixel
//		img.setPixelRGB(x, y, accum);
		
		return accum;
		
	}
}
