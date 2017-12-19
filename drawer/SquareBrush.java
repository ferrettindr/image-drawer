package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class SquareBrush extends AbstractBrush {

	private int side;

	public SquareBrush(int size) {
		side = size;
	}

	protected boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color newColor) {
		double oldDiff = 0, newDiff = 0;
		Color oldColor;
		
		int width, height;
		
		//(x,y) is the center
		if (x < side/2)
			width = 0;
		else
			width = x - side/2;

		while ((width < sourceImg.getWidth()) && (width < x+side/2)) {
			if (y < side/2)
				height = 0;
			else
				height = y - side/2;
			while ((height < sourceImg.getHeight()) && (height < y+side/2)) {

				oldColor = new Color(sourceImg.getRGB(width, height), true);

				oldDiff += colorDistance(oldColor, new Color(destImg.getRGB(width, height), true));
				newDiff += colorDistance(oldColor, newColor);

				height++;
			}
			width++;
		}

		return newDiff < oldDiff;
		
	}

	protected void draw(Graphics2D drawer, int x, int y, Color newColor) {
		drawer.setColor(newColor);
		//(x,y) is the center
		drawer.fillRect(x-(side/2), y-(side/2), side, side);
	}
}
