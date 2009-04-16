import image.ImageManipulator;
import image.Mask;
import image.YanInpainter;


public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length != 2)
		{
			System.out.println(" Falta argumentoees");
			System.exit(2);
		}
		
		int[] whiteArray = {255, 255, 255};
		int[] blackArray = {0, 0, 0};
		
		try{
		ImageManipulator image = new ImageManipulator(args[0]);
		Mask mask = new Mask(args[1], whiteArray, blackArray);

		YanInpainter yaniImpaint = new YanInpainter(false);
		yaniImpaint.inpaint(image, mask);
		image.saveToFile("inpainteada1");
		
	
//		yaniImpaint.setConsiderCurrentIteration(true);
//		yaniImpaint.inpaint(image, mask);
//		image.saveToFile("inpainteada2");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
