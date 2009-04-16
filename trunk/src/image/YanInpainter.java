package image;

public class YanInpainter implements Inpaintable {

	private boolean considerCurrentIteration = false;
	
	/**
	 * @param considerCurrentIteration
	 *            the considerCurrentIteration to set
	 */
	public YanInpainter (boolean considerCurrentIteration) {
		this.considerCurrentIteration = considerCurrentIteration;
	}
	
	/**
	 * @param considerCurrentIteration
	 *            the considerCurrentIteration to set
	 */
	public void setConsiderCurrentIteration(boolean considerCurrentIteration) {
		this.considerCurrentIteration = considerCurrentIteration;
	}

	public ImageManipulator inpaint(ImageManipulator img, Mask mask) {
		if (img == null || mask == null || img.getWidth() != mask.getWidth()
				|| img.getHeight() != mask.getHeight()) {
			throw new IllegalArgumentException();
		}

		ImageManipulator result = null;
		int ringWidth, ringHeight, rowIdx, colIdx;

		try {
			result = (ImageManipulator) img.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}

		for (ringHeight = mask.getHeight(), ringWidth = mask.getWidth(); ringHeight > 0
				|| ringWidth > 0; ringHeight--, ringWidth--) {
			// We iterate through the rectangle
			
			// top side
			for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++) {
				if (mask.isMarked(rowIdx, colIdx)) {
					result.setPixelRGB(rowIdx, colIdx, inpaintPixel(img, mask, rowIdx, colIdx));
				}
					
			}
			// right side
			for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++) {
				if (mask.isMarked(rowIdx, colIdx)) {
					result.setPixelRGB(rowIdx, colIdx, inpaintPixel(img, mask, rowIdx, colIdx));
				}
			}
			// bottom side
			for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--) {
				if (mask.isMarked(rowIdx, colIdx)) {
					result.setPixelRGB(rowIdx, colIdx, inpaintPixel(img, mask, rowIdx, colIdx));
				}
			}
			// left side
			for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--) {
				if (mask.isMarked(rowIdx, colIdx)) {
					result.setPixelRGB(rowIdx, colIdx, inpaintPixel(img, mask, rowIdx, colIdx));
				}
			}
			// mark the ring as NoMark in order to be considered as information
			// later.
			markRing(mask, ringWidth, ringHeight);
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
	private int[] inpaintPixel(ImageManipulator img, Mask mask, int x, int y) {
		
	}
	
	private void markRing(Mask mask, int ringWidth, int ringHeight) {
		int rowIdx, colIdx;
		
		// top side
		for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++) {
			mask.unMark(rowIdx, colIdx);
		}
		// right side
		for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++) {
			mask.unMark(rowIdx, colIdx);
		}
		// bottom side
		for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--) {
			mask.unMark(rowIdx, colIdx);
		}
		// left side
		for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--) {
			mask.unMark(rowIdx, colIdx);
		}
	}
}
