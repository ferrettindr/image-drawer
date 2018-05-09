package drawer;

import java.util.Random;
import java.awt.image.BufferedImage;


public class RandomBrush extends AbstractBrush {

	private int area;
	private Random random;

	public RandomBrush(int areaSize) {
		area = areaSize;
		random = new Random();
	}

	//randomly select points inside the area*area square
	protected void addNewCoordinates(BufferedImage image, int x, int y) {
		for (int lines = (y-area); lines < y+area; lines++)
			if ( lines >= 0 && lines < image.getHeight())
				for (int columns = (x-area); columns < x+area; columns++)
					if ( columns >= 1 && columns < image.getWidth())
						if (random.nextBoolean()) {
							Integer[] coordinates = new Integer[2];
							coordinates[0] = columns;
							coordinates[1] = lines;
							strokeCoordinates.add(coordinates);
						}
	}

}
