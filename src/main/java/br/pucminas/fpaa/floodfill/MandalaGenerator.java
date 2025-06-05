package br.pucminas.fpaa.floodfill;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Generates mandala patterns with circles, radial lines, and
 * geometric shapes. Creates black line art on white background
 * suitable for flood fill coloring.
 */
public class MandalaGenerator {

    /**
     * Random generator for pattern variations
     */
    private final Random random;

    /**
     * Creates a new mandala generator.
     */
    public MandalaGenerator() {
        this.random = new Random();
    }

    /**
     * Generates a mandala image with random patterns.
     *
     * @param width  image width in pixels
     * @param height image height in pixels
     * @return BufferedImage containing the mandala
     */
    public BufferedImage generateMandala(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Create white background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set up black drawing pen
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.0f));

        double centerX = width / 2.0;
        double centerY = height / 2.0;

        double maxRadius = Math.sqrt(centerX * centerX + centerY * centerY);

        // Randomize mandala structure
        int numPetals = 6 + random.nextInt(6);
        int numRings = 3 + random.nextInt(4);
        double ringSpacing = maxRadius / (numRings + 1);

        // Draw concentric circles
        for (int ring = 1; ring <= numRings; ring++) {
            double radius = ring * ringSpacing;
            drawCircle(g2d, centerX, centerY, radius);
        }

        // Draw radial lines (petals)
        for (int petal = 0; petal < numPetals; petal++) {
            double angle = (2 * Math.PI * petal) / numPetals;
            drawRadialLine(g2d, centerX, centerY, angle, maxRadius * 0.8);
        }

        addGeometricPatterns(g2d, centerX, centerY, maxRadius * 0.6);

        g2d.dispose();
        return image;
    }

    /**
     * Draws a circle centered at the given point.
     */
    private void drawCircle(Graphics2D g2d, double centerX, double centerY, double radius) {
        int diameter = (int) (2 * radius);
        int x = (int) (centerX - radius);
        int y = (int) (centerY - radius);
        g2d.drawOval(x, y, diameter, diameter);
    }

    /**
     * Draws a line from center outward at the specified angle.
     */
    private void drawRadialLine(Graphics2D g2d, double centerX, double centerY, double angle, double length) {
        int endX = (int) Math.round(centerX + length * Math.cos(angle));
        int endY = (int) Math.round(centerY + length * Math.sin(angle));
        g2d.drawLine((int) centerX, (int) centerY, endX, endY);
    }

    /**
     * Adds random geometric polygons to the mandala.
     */
    private void addGeometricPatterns(Graphics2D g2d, double centerX, double centerY, double maxRadius) {
        int numPatterns = 2 + random.nextInt(3);

        for (int pattern = 0; pattern < numPatterns; pattern++) {
            double patternRadius = maxRadius * (0.3 + random.nextDouble() * 0.6);
            double angleOffset = random.nextDouble() * 2 * Math.PI;

            int sides = 3 + random.nextInt(5);
            drawPolygon(g2d, centerX, centerY, patternRadius, sides, angleOffset);
        }
    }

    /**
     * Draws a regular polygon with the specified number of sides.
     */
    private void drawPolygon(Graphics2D g2d, double centerX, double centerY, double radius, int sides,
                             double angleOffset) {
        int[] xPoints = new int[sides];
        int[] yPoints = new int[sides];

        for (int i = 0; i < sides; i++) {
            double angle = angleOffset + (2 * Math.PI * i) / sides;
            xPoints[i] = (int) Math.round(centerX + radius * Math.cos(angle));
            yPoints[i] = (int) Math.round(centerY + radius * Math.sin(angle));
        }

        g2d.drawPolygon(xPoints, yPoints, sides);
    }

}
