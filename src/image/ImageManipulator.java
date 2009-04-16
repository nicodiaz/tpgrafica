package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulator {
	private BufferedImage imgSrc = null;
	
	public ImageManipulator( String fileName ) throws IOException {
		
		// Preconditions
		if (fileName == null)
		{
			throw new IllegalArgumentException();
		}

		imgSrc = ImageIO.read(new File(fileName));
			
		
	}
	
	public ImageManipulator( BufferedImage imgSrc ) {
		
	}
	
	public void applyGrayscale( ) {
		
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public int getPixel( int x, int y, ColorElementType type )
	{
		return 0;
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

}
