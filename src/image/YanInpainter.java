package image;

public class YanInpainter implements Inpaintable {

	private boolean considerCurrentIteration = false;
	
	/**
	 * @param considerCurrentIteration the considerCurrentIteration to set
	 */
	public YanInpainter (boolean considerCurrentIteration) {
		this.considerCurrentIteration = considerCurrentIteration;
	}
	
	/**
	 * @param considerCurrentIteration the considerCurrentIteration to set
	 */
	public void setConsiderCurrentIteration(boolean considerCurrentIteration) {
		this.considerCurrentIteration = considerCurrentIteration;
	}

	public ImageManipulator inpaint(ImageManipulator img, ImageManipulator mask) {
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
			for (rowIdx = 0, colIdx = 0; colIdx < ringWidth; colIdx++) {
				if ()
			}
			for (colIdx = ringWidth - 1, rowIdx = 1; rowIdx < ringHeight; rowIdx++) {
				
			}
			for (rowIdx = ringHeight - 1, colIdx = ringWidth - 2; colIdx >= 0; colIdx--) {
				
			}
			for (colIdx = 0, rowIdx = ringHeight - 2; rowIdx >= 0; rowIdx--) {
				
			}
		}

		return result;
	}
}
