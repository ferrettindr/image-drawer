package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class RectangleBrush extends AbstractBrush {

	private int xside, yside;

	public RectangleBrush(int base, int height) {
		xside = base;
		yside = height;
	}

	public boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color newColor) {
		double oldDiff = 0, newDiff = 0;
		Color oldColor;

		int width, height;
		//(x,y) is the center
		if (x < xside/2)
			width = 0;
		else
			width = x - xside/2;

		while ((width < sourceImg.getWidth()) && (width < x+xside/2)) {
			if (y < yside/2)
				height = 0;
			else
				height = y - yside/2;
			while ((height < sourceImg.getHeight()) && (height < y+yside/2)) {

				oldColor = new Color(sourceImg.getRGB(width, height), true);

				oldDiff += colorDistance(oldColor, new Color(destImg.getRGB(width, height), true));
				newDiff += colorDistance(oldColor, newColor);

				height++;
			}
			width++;
		}

		return newDiff < oldDiff;
	}

	public void draw(Graphics2D drawer, int x, int y, Color newColor) {
		drawer.setColor(newColor);
		//(x,y) is the center
		drawer.fillRect(x-(xside/2), y-(yside/2), xside, yside);
	}
}
