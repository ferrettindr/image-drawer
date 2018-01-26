package drawer;

import java.awt.image.BufferedImage;

public class RectangleBrush extends AbstractBrush {

	private int xside, yside;

	public RectangleBrush(int base, int height) {
		xside = base;
		yside = height;
	}

	protected void addCurrentCoordinates(BufferedImage image, int x, int y) {

		int width, height;
		
		if (x < xside/2)
			width = 0;
		else
			width = x - xside/2;

		while (width < image.getWidth() && width < x+xside/2) {
			if (y < yside/2)
				height = 0;
			else
				height = y - yside/2;
			while (height < image.getHeight() && height < y+yside/2) {
				Integer[] coordinates = new Integer[2];
				coordinates[0] = width;
				coordinates[1] = height;
				strokeCoordinates.add(coordinates);

				height++;
			}
			width++;
		}
	}
}
