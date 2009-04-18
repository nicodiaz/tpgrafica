import image.ImageManipulator;
import image.Mask;
import image.YanInpainter;

public class Runner
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		if (args.length != 2)
		{
			System.err.println("Debe ingresar el nombre del archivo de imagen y el nombre del"
				+ "archivo con la mascara");
			System.err.println("Ejemplo: java Runner image.jpg mask.jpg");
			System.exit(1);
		}

		int[] whiteArray = { 255, 255, 255 };
		int[] blackArray = { 0, 0, 0 };

		try
		{
			ImageManipulator image = new ImageManipulator(args[0]);
			Mask mask = new Mask(args[1], whiteArray, blackArray);

			YanInpainter yaniImpaint = new YanInpainter(false);
			ImageManipulator imgInpainted = yaniImpaint.inpaint(image, mask);
			imgInpainted.saveToFile("inpainted.jpg");
			mask.saveToFile("maskResult.jpg");

			// yaniImpaint.setConsiderCurrentIteration(true);
			// yaniImpaint.inpaint(image, mask);
			// image.saveToFile("inpainteada2");
		} catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Some Error ocurred.");
			System.exit(1);
		}
		
		System.out.println("Inpaint successfully done!"); 
	}

}
