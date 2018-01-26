package drawer;

import java.awt.image.BufferedImage;

public class LineBrush extends AbstractBrush {

	private int length, thickness;
	private double slope, orientation;

	public LineBrush(int length, int thickness, double orientation) {
		this.length = length;
		this.thickness = thickness;
		this.orientation = orientation % 360;
		this.slope = Math.tan(Math.toRadians(this.orientation));
	}


	protected void addCurrentCoordinates(BufferedImage image, int x, int y) {

		for (int thick = 0; thick < thickness; thick++) {
			int newX = x + thick, newY = y + thick;

			int xAxis, yAxis;

			//(x,y) is the center
			if (y < length/2)
				yAxis = 0;
			else
				yAxis = newY - length/2;

			for (int len = 0; len < length && yAxis < (y+length/2); len++) {
				//use the linear equation   y = mx + constant
				//using the known point newX and newY and the slope
				//to find the other points of the line
				xAxis = (int) Math.round((yAxis - newY)/slope + newX);

				if (yAxis < image.getHeight() && xAxis > 0 && xAxis < image.getWidth()) {
					Integer[] coordinates = new Integer[2];
					coordinates[0] = xAxis;
					coordinates[1] = yAxis;
					strokeCoordinates.add(coordinates);
				}

				yAxis++;
			}
		}
	}

}
