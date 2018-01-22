# image-drawer
java package to make a somewhat "artistic" copy of an image

## Usage

### Installation
Just put the drawer folder in your project directory and add `import drawer.*;` to the files that use the package.

### General usage
General guidelines for using the package:

- Create a Drawer object by passing its constructor a BufferedImage representation of the image you want to copy.
- Set a concrete implementation of an AbstractBrush for the Drawer.
- Let the Drawer try and draw the image.
- Get the resulting "drawn" copy as a BufferedImage.

Beofre getting the finished copy you can change the brush of the Drawer and continue drawing the image.
This way you'll be able to use brushes of different size and shape for the same drawn copy!

### Working example
The included class ImageDrawer.java contains a main method which shows a simple way to use the package.

Compile it with `javac ImageDrawer.java` and run it with `java ImageDrawer pathToImage numberOfTries brushShape [..shapeMeasurements]`.

For example, running `java ImageDrawer ./woodland.png 10000000 rectangle 10 10` should get you something similar to this:

***Original image***: woodland.png

<img src="https://i.imgur.com/YhhLDK2.jpg" width="700">

***Drawn copy***: woodland_drawn.png

<img src="https://i.imgur.com/83sPhFC.jpg" width="700">


## Structure
The logic with which the image is copied follows these rules:
- pick a random point on the copy
- pick a random colour choosing from the ones in the original image
- attempt to put a brush stroke on that point
- if the copy would be more similar to the original with said stroke, apply the stroke
- otherwise discard the stroke

The above steps are repeated for a fixed number of tries.

Picking a random point and colour for each try makes the process of copying nondeterministic,
hence the "artistic" aspirations of the package.

The point and the colour are picked by the Drawer object
while the Brush object has to be able to tell weather or not the stroke would make the copy closer to the original and apply the stroke to the copy.
