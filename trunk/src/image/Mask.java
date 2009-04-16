package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.corba.se.spi.ior.MakeImmutable;

public class Mask extends ImageManipulator {
	
	private int [] maskMark = null;
	private int [] maskNoMark = null;
	
	/**
	 * 
	 * @param fileName
	 * @param red
	 * @param green
	 * @param blue
	 * @throws IOException
	 */
	public Mask (String fileName, int [] markColor, int [] noMarkColor) throws IOException {
		super(fileName);
		maskMark = new int[3];
		maskMark[0] = markColor[0];
		maskMark[1] = markColor[1];
		maskMark[2] = markColor[2];
		maskNoMark[0] = noMarkColor[0];
		maskNoMark[1] = noMarkColor[1];
		maskNoMark[2] = noMarkColor[2];
	}
	
	public boolean isMarked(int x, int y) {
		int [] pixel = this.getPixelRGB(x, y);
		
		return pixel[0] == maskMark[0] && pixel[1] == maskMark[1] && pixel[2] == maskMark[2];
	}
	
	public void unMark(int x, int y) {
		this.setPixelHSB(x, y, maskNoMark);
	}
}
