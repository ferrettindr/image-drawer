package drawer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;


public class Drawer {
	
	private Color[] colors;
	private BufferedImage sourceImg;
	private BufferedImage destImg;
	private AbstractBrush brush;
	private Graphics2D gdrawer;


	public Drawer(BufferedImage source) {
		sourceImg = source;
		
		//get all colors from source image
		Set<Color> tempColor = new HashSet<Color>(); 
		for (int x = 0; x < sourceImg.getWidth(); x++)
			for (int y = 0; y < sourceImg.getHeight(); y++)
				tempColor.add(new Color(sourceImg.getRGB(x, y), true));
		colors = tempColor.toArray(new Color[tempColor.size()]);
	
		destImg = new BufferedImage(sourceImg.getWidth(), sourceImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//new blank image to draw on
		gdrawer  = destImg.createGraphics();
		gdrawer.setColor(Color.WHITE);
		gdrawer.fillRect(0, 0, destImg.getWidth(), destImg.getHeight());
	}

	public Drawer(BufferedImage source, AbstractBrush selectedBrush) {
		this(source);
		brush = selectedBrush;
	}

	public void setBrush(AbstractBrush selectedBrush) {
		brush = selectedBrush;
	}

	public Color getRandomColor() {
		int rnd = new Random().nextInt(colors.length);
		return colors[rnd];
	}

	public BufferedImage getDestImage() {
		return destImg;
	}


	public void tryAndDraw(int tries) {
		Color color;
		int x, y;
		Random rnd = new Random();
		
		for (int i = 0; i < tries; i++) {
			color = this.getRandomColor();
			y = rnd.nextInt(sourceImg.getHeight());
			x = rnd.nextInt(sourceImg.getWidth());
			if (brush.tryDraw(sourceImg, destImg, x, y, color))
				brush.draw(destImg);
		}
	}

}
