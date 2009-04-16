package image;

public interface Inpaintable {

	/**
	 * Inpaints the image img.
	 * 
	 * @param img The image to be inpainted.
	 * @param mask The mask that indicates the area to be inpainted.
	 * @return The inpainted image.
	 */
	public ImageManipulator inpaint (ImageManipulator img, Mask mask);
}
