package drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class LineBrush extends AbstractBrush {

	private int length, thickness;
	private double slope, orientation;

	public LineBrush(int length, int thickness, double orientation) {
		this.length = length;
		this.thickness = thickness;
		this.orientation = orientation % 360;
		this.slope = Math.tan(Math.toRadians(orientation));
	}

	public boolean tryDraw(BufferedImage sourceImg, BufferedImage destImg, int x, int y, Color newColor) {
		double oldDiff = 0, newDiff = 0;
		Color oldColor;

		for (int thick = 0; thick < thickness; thick++) {
			int newX = x + thick, newY = y + thick;

			int xAxis, yAxis;

			//(x,y) is the center
			if (y < length/2)
				yAxis = 0;
			else
				yAxis = newY - length/2;

			for (int len = 0; len < length && yAxis < (y+length/2); len++) {
				//use the linear equation   y = mx + constant   to find the other points of the line
				xAxis = (int)((yAxis - newY)/slope + newX);

				if ((yAxis >= sourceImg.getHeight()) || (xAxis < 0) || (xAxis >= sourceImg.getWidth()))
					break;

				oldColor = new Color(sourceImg.getRGB(xAxis, yAxis), true);
				oldDiff += colorDistance(oldColor, new Color(destImg.getRGB(xAxis, yAxis), true));
				newDiff += colorDistance(oldColor, newColor);
				
				yAxis++;
			}
		}
		return newDiff < oldDiff;
	}

	public void draw(Graphics2D drawer, int x, int y, Color newColor) {
		drawer.setColor(newColor);
		int sinSide = (int)(((double)(length)/2) * (Math.sin(Math.toRadians(orientation % 90))));
		int cosSide = (int)(((double)(length)/2) * (Math.cos(Math.toRadians(orientation % 90))));

		for (int thick = 0; thick < thickness; thick++) {
			int newX = x + thick, newY = y + thick;
			//(x,y) is the center
			if ((orientation >= 90 && orientation < 180) || (orientation >= 270 && orientation < 360))
				drawer.drawLine(newX-sinSide, newY-cosSide, newX+sinSide, newY+cosSide);
			else
				drawer.drawLine(newX-cosSide, newY-sinSide, newX+cosSide, newY+sinSide);
		}
	}
}
