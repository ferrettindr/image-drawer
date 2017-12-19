package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class AbstractBrush {

	public double colorDistance(Color c1, Color c2) {
		double red = c1.getRed()-c2.getRed();
		double blue = c1.getBlue()-c2.getBlue();
		double green = c1.getGreen()-c2.getGreen();
		double alpha = c1.getAlpha()-c2.getAlpha();
		//Manhattan distance seems slightly faster than Euclidean
		//return Math.sqrt((red*red)+(blue*blue)+(green*green)+(alpha*alpha));
		return Math.abs(red) + Math.abs(green) + Math.abs(blue) + Math.abs(alpha);
	}

	//checks if the brush stroke for this try improves the drawn image, returning true if it does
	protected abstract boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color color); 

	protected abstract void draw(Graphics2D drawer, int x, int y, Color color);
}
