package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulator {
	private BufferedImage imgSrc = null;
	private String filePath = null;
	
	public ImageManipulator(String fileName) throws IOException {
		
		// Preconditions
		if (fileName == null)
		{
			throw new IllegalArgumentException();
		}

		File imgFile = new File(fileName);
		imgSrc = ImageIO.read(imgFile);
			
		
		filePath = imgFile.getCanonicalPath();
	}
	
	public ImageManipulator(BufferedImage imgSrc) {
		this.imgSrc = imgSrc;
		
	}
	
	public void applyGrayscale() {
		
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		ImageManipulator theClone = null;
		
		try {
			theClone = new ImageManipulator(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CloneNotSupportedException();
		}

		return theClone;
	}
	
	

	/*
	 * Recover a single pixel value for any channel (RED GREEN OR BLUE).
	 * Return -1 in error case
	 */
	public int getPixelRGB(int x, int y, ColorElementType type)
	{
		
		// Precondition
		if (x < 0 || x >= imgSrc.getWidth() || y < 0 || y >= imgSrc.getHeight() || type == null 
			|| type == ColorElementType.HUE || type == ColorElementType.SAT	|| type == ColorElementType.BRIGHT)
		{
			throw new IllegalArgumentException();
		}
		
		int[] pixelData = new int[3];
		int result = -1;
		imgSrc.getData().getPixel(x, y, pixelData);
		
		switch (type) {
			case RED: result = pixelData[0];
				break;
			case GREEN: result = pixelData[1];
				break;
			case BLUE: result = pixelData[2];
				break;
			default:
				break;
		}
		
		return result;
	}
	
	public int getPixelHSB(int x, int y, ColorElementType type)
	{
		
		return 0;
		
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
		
	}
	
	public void setPixelHSB(int x, int y, int[] pixelData)
	{
		
	}
	
	public int[] getPixelHSB(int x, int y)
	{
		return null;
	}
	
	public void saveToFile(String fileName) throws IOException
	{
		
		// Preconditions
		if (fileName == null)
		{
			throw new IllegalArgumentException();
		}
		
		// Now we save the file
		BufferedImage imgDst = new BufferedImage(imgSrc.getWidth(), imgSrc.getHeight(), 
			BufferedImage.TYPE_INT_RGB);
				
		ImageIO.write(imgDst, "jpg", new File(fileName));
		
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
