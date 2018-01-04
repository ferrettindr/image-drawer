package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class LineBrush extends AbstractBrush {

	private int length;
	private double slope, orientation;

	public LineBrush(int length, double orientation) {
		this.length = length;
		this.orientation = orientation % 360;
		this.slope = Math.tan(Math.toRadians(orientation));
	}

	public boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color newColor) {
		double oldDiff = 0, newDiff = 0;
		Color oldColor;

		//use the linear equation   y = mx + constant   to find the other points of the line
		double constant = y-(slope*x);

		int xAxis, yAxis;

		if (x < length/2)
			xAxis = 0;
		else
			xAxis = x - length/2;

		//(x,y) is the center
		for (int len = 0; len < length && xAxis < (x+length/2); len++) {
			yAxis = (int)(slope * xAxis + constant);

			if ((xAxis >= sourceImg.getWidth()) || (yAxis < 0) || (yAxis >= sourceImg.getHeight()))
				break;

			oldColor = new Color(sourceImg.getRGB(xAxis, yAxis), true);
			oldDiff += colorDistance(oldColor, new Color(destImg.getRGB(xAxis, yAxis), true));
			newDiff += colorDistance(oldColor, newColor);
			
			xAxis++;
		}

		return newDiff < oldDiff;
	}

	public void draw(Graphics2D drawer, int x, int y, Color newColor) {
		drawer.setColor(newColor);
		int sinSide = (int)(((double)(length)/2) * (Math.sin(Math.toRadians(orientation % 90))));
		int cosSide = (int)(((double)(length)/2) * (Math.cos(Math.toRadians(orientation % 90))));
		//(x,y) is the center
		if ((orientation >= 90 && orientation < 180) || (orientation >= 270 && orientation < 360))
			drawer.drawLine(x-sinSide, y-cosSide, x+sinSide, y+cosSide);
		else
			drawer.drawLine(x-cosSide, y-sinSide, x+cosSide, y+sinSide);
	}
}
