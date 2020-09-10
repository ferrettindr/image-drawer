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

	public BufferedImage getDestImage() {
		return destImg;
	}


	public void tryAndDraw(int tries) {
		Color color;
		int x, y;
		Random rnd = new Random();
		
		for (int i = 0; i < tries; i++) {
			y = rnd.nextInt(sourceImg.getHeight());
			x = rnd.nextInt(sourceImg.getWidth());
			color = new Color(sourceImg.getRGB(x, y), true);
			if (brush.tryDraw(sourceImg, destImg, x, y, color))
				brush.draw(destImg, color);
		}
	}

}
