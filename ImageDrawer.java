import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import drawer.*;
import java.util.Arrays;
import java.lang.StringBuffer;
import java.awt.image.BufferedImage;


public class ImageDrawer {

	private static String[][] shapesNsizes = {{"rectangle", "2"}, {"line", "3"}, {"circle", "1"}, {"random", "1"}};


	public static void main (String[] args) {

		BufferedImage originalImg = null;
		try {
			originalImg = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			System.out.println("Wrong arguments. The first argument should be the relative path to the image");
			e.printStackTrace();
		}
		//create a drawer by passing it a BufferedImage
		Drawer imgDrawer = new Drawer(originalImg);

		int tries;
		try {

			tries = Integer.parseInt(args[1]);
			
			//create a brush and set it as the drawer brush
			AbstractBrush imgBrush = brushCreator(args[2], Arrays.copyOfRange(args, 3, args.length));
			imgDrawer.setBrush(imgBrush);

		} catch (Exception e) {
			StringBuffer sb = new StringBuffer("Wrong arguments.\n");
			sb.append("The first argument should be the image path.\n");
			sb.append("The second the number of tries.\n");
			sb.append("The third the shape of the brush followed by its measurements.\n");
			sb.append("The available shapes and the number of theirs measurements are:\n");

			for (String[] shape: shapesNsizes)
				sb.append("Shape: " + shape[0] + " - Measurements#: " + shape[1] + "\n");

			throw new IllegalArgumentException(sb.toString());
		}
	
		//draw the image
		imgDrawer.tryAndDraw(tries);


		//save the file
		try {
			String fileName;
			if (args[0].contains("."))
				fileName = args[0].substring(0, args[0].lastIndexOf('.'));
			else
				fileName = args[0];
			fileName += "_drawn.png";	
			File outputfile = new File(fileName);
			ImageIO.write(imgDrawer.getDestImage(), "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static AbstractBrush brushCreator(String brushShape, String[] sizes) {
		
		AbstractBrush brush;

		switch (brushShape) {
			case "rectangle":
				brush = new RectangleBrush(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
				break;
			case "line":
				brush = new LineBrush(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]), Integer.parseInt(sizes[2]));
				break;
			case "circle":
				brush = new CircleBrush(Integer.parseInt(sizes[0])); 
				break;
			case "random":
				brush = new RandomBrush(Integer.parseInt(sizes[0])); 
				break;
			default:
				throw new IllegalArgumentException("Not existing shape");
		}

		return brush;
	}

}
