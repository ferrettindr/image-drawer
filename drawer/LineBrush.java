package drawer;

import java.awt.image.BufferedImage;

public class LineBrush extends AbstractBrush {

	private int length, thickness, orientation;
	private double slope;

	public LineBrush(int length, int thickness, int orientation) {
		this.length = length;
		this.thickness = thickness;
		this.orientation = orientation % 180;
		this.slope = Math.tan(Math.toRadians(this.orientation));
	}


	protected void addCurrentCoordinates(BufferedImage image, int x, int y) {

		int yLength = (int) (Math.sin(Math.toRadians(orientation)) * length);

		for (int thick = 0; thick < thickness; thick++) {
			int newX = x + thick, newY = y + thick;

			if (newX >= image.getWidth())
				newX = image.getWidth() - 1;
			if (newY >= image.getHeight())
				newY = image.getHeight() - 1;


			int x0 = -1, y0 = newY, x1 = -1, y1 = newY;

			if (orientation % 180 > 5) {
				//(x,y) is the center
				if (newY < yLength/2)
					y0 = 0;
				else
					y0 = newY - yLength/2;

				if (newY + yLength/2 >= image.getHeight())
					y1 = image.getHeight() - 1;
				else
					y1 = newY + yLength/2;

				x0 = (int) Math.round((y0 - y)/slope + newX);
				x1 = (int) Math.round((y1 - y)/slope + newX);
			}
			else {
				//(x,y) is the center
				if (newX < length/2)
					x0 = 0;
				else
					x0 = newX - length/2;

				if (newX + length/2 >= image.getWidth())
					x1 = image.getWidth() - 1;
				else
					x1 = newX + length/2;
			}

			while ((x0 < 0 || x0 >= image.getWidth()) || (x1 < 0 || x1 >= image.getWidth())) {
				if (x0 < 0 || x0 >= image.getWidth()) {
					y0++;
					x0 = (int) Math.round((y0 - newY)/slope + newX);
				}

				if (x1 < 0 || x1 >= image.getWidth()) {
					y1--;
					x1 = (int) Math.round((y1 - newY)/slope + newX);
				}
			}

		//	System.out.println("x0= " + x0 + ", y0 = " + y0 + " -- x1 = " + x1 + ", y1= " + y1);
			plotLine(x0, y0, x1, y1);
		}
	}

	//using Bresenham's line algorithm
	protected void plotLine(int x0,int y0,int x1, int y1) {
		int dx = x1 - x0;
		int dy = y1 - y0;
		int ix1 = 0, iy1 = 0, ix2 = 0, iy2 = 0;
		if (dx < 0)
			ix1 = -1;
		else if (dx > 0)
			ix1 = 1;
		if (dy < 0)
			iy1 = -1;
		else if (dy > 0)
			iy1 = 1;
		if (dx < 0)
			ix2 = -1;
		else if (dx > 0)
			ix2 = 1;

		int longest = Math.abs(dx);
		int shortest = Math.abs(dy);

		if (!(longest>shortest)) {
			longest = Math.abs(dy);
			shortest = Math.abs(dx);
			if (dy < 0) iy2 = -1;
			else if (dy > 0) iy2 = 1;
			ix2 = 0;
		}

		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {
			Integer[] coordinates = new Integer[2];
			coordinates[0] = x0;
			coordinates[1] = y0;
			strokeCoordinates.add (coordinates);

			numerator += shortest ;
			if (!(numerator < longest)) {
				numerator -= longest;
				x0 += ix1;
				y0 += iy1;
			}
			else {
				x0 += ix2;
				y0 += iy2;
			}
		}
	}


}
