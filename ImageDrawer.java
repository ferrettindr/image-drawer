import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import drawer.*;
import java.awt.image.BufferedImage;


public class ImageDrawer {

	public static void main (String[] args) {

		//arguments parsing
		if (args.length < 3)
			throw new IllegalArgumentException("Wrong arguments. The first argument should be the image name, the second the number of tries, the third the size of the brush");

		BufferedImage originalImg = null;
		try {
			originalImg = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int tries = Integer.parseInt(args[1]);
		int brushSize = Integer.parseInt(args[2]);
		
		//create a drawer by passing it a BufferedImage
		Drawer imgDrawer = new Drawer(originalImg);

		//create a brush and set it as the drawer brush
		AbstractBrush imgBrush = new SquareBrush(brushSize);
		imgDrawer.setBrush(imgBrush);

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
}
