package drawer;

import java.awt.image.BufferedImage;


public class CircleBrush extends AbstractBrush {

	private int radius;

	public CircleBrush(int radius) {
		this.radius = radius;
	}

	// Using Bresenham’s circle algorithm (x,y) is the center
	protected void addNewCoordinates(BufferedImage image, int x, int y) {
	
		int err = 3 - (2 * radius);
		int start = 0, finish = radius;
		
		while (start <= finish) {
			fillHorizontalLine(x+start, y+finish, x-start, y+finish, image);
			fillHorizontalLine(x+start, y-finish, x-start, y-finish, image);
			fillHorizontalLine(x+finish, y+start, x-finish, y+start, image);
			fillHorizontalLine(x+finish, y-start, x-finish, y-start, image);

			start++;

			if (err >= 0) {
				err += 4 * (start - finish) + 10;
				finish--;
			 }
			else
				err += 4 * start + 6;
			
			addPoint(x+start, y+finish, image);
			addPoint(x-start, y+finish, image);
			addPoint(x+start, y-finish, image);
			addPoint(x-start, y-finish, image);
			addPoint(x+finish, y+start, image);
			addPoint(x-finish, y+start, image);
			addPoint(x+finish, y-start, image);
			addPoint(x-finish, y-start, image);
		}
	}

	private void addPoint(int x, int y, BufferedImage image) {
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			Integer[] coordinates = new Integer[2];
			coordinates[0] = x;
			coordinates[1] = y;
			strokeCoordinates.add(coordinates);
		}
	}

	protected void fillHorizontalLine(int x0, int y0, int x1, int y1, BufferedImage image) {
		
		//x0 is always the smallest
		if (x0 > x1) {
			int temp = x0;
			x0 = x1;
			x1 = temp;
		}

		//check if inbound
		if (x0 < 0)
			x0 = 0;
		if (x1 >= image.getWidth())
			x1 = image.getWidth() - 1;


		if (y0 >= 0 && y0 < image.getHeight())
			for (int i = x0; i <= x1; i++) {
				Integer[] coordinates = new Integer[2];
				coordinates[0] = i;
				coordinates[1] = y0;
				strokeCoordinates.add(coordinates);
			}
	}
}
