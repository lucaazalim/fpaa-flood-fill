package br.pucminas.fpaa.floodfill;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Image-specific flood fill implementation that fills white regions with random
 * colors, treating black pixels as borders. Updates the BufferedImage in real-time.
 */
public class ImageFloodFill extends FloodFill {

    /**
     * RGB value for black (border pixels)
     */
    private static final int BLACK = -16777216;
    /**
     * RGB value for white (empty pixels to fill)
     */
    private static final int WHITE = -1;

    /**
     * The image being processed
     */
    private final BufferedImage image;
    /**
     * Random generator for fill colors
     */
    private final Random random;

    /**
     * Creates an ImageFloodFill for the given image.
     *
     * @param image the BufferedImage to process
     */
    public ImageFloodFill(BufferedImage image) {
        super(generatePixelMatrix(image), BLACK, WHITE);
        this.image = image;
        this.random = new Random();
    }

    /**
     * Converts BufferedImage to a 2D matrix of RGB values.
     *
     * @param image the image to convert
     * @return 2D array of RGB pixel values
     */
    private static int[][] generatePixelMatrix(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        int[][] pixelMatrix = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelMatrix[y][x] = image.getRGB(x, y);
            }
        }

        return pixelMatrix;
    }

    /**
     * Updates the image pixel when a value changes.
     */
    @Override
    public void onValueChange(int x, int y, int newColor) {
        image.setRGB(x, y, newColor);
    }

    /**
     * Generates a random color that's neither black nor white.
     *
     * @return a random RGB color value
     */
    @Override
    public int generateValue() {
        int newValue;
        do {
            newValue = random.nextInt();
        } while (newValue == BLACK || newValue == WHITE);
        return newValue;
    }

}
