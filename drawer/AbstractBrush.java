package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractBrush {

	protected List<Integer[]> strokeCoordinates = new ArrayList<Integer[]>();
	private Color brushColor;

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
	public boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color newColor) {

		computeStrokeCoordinates(sourceImg, x, y);
		brushColor = newColor;
		
		double oldDiff = 0, newDiff = 0;

		for (Integer[] coordinates: strokeCoordinates) {
			
				Color oldColor = new Color(sourceImg.getRGB(coordinates[0],coordinates[1] ), true);

				oldDiff += colorDistance(oldColor, new Color(destImg.getRGB(coordinates[0],coordinates[1] ), true));
				newDiff += colorDistance(oldColor, newColor);
		}

		return newDiff < oldDiff;
	}

	public void draw(BufferedImage destImg) {
		for (Integer[] coordinates: strokeCoordinates)
			destImg.setRGB(coordinates[0], coordinates[1], brushColor.getRGB());
	}

	public void computeStrokeCoordinates(BufferedImage image, int x, int y) {
		strokeCoordinates.clear();
		addCurrentCoordinates(image, x, y);
	}

	protected abstract void addCurrentCoordinates(BufferedImage image, int x, int y);

}

