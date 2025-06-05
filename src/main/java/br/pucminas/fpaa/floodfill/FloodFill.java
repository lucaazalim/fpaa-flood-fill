package br.pucminas.fpaa.floodfill;

import java.util.Stack;

/**
 * Abstract base class for implementing flood fill algorithms on a 2D matrix.
 * This class provides the core flood fill functionality using an iterative
 * stack-based approach to avoid stack overflow issues with large regions.
 */
public abstract class FloodFill {

    /**
     * The width of the matrix (number of columns)
     */
    private final int width;
    /**
     * The height of the matrix (number of rows)
     */
    private final int height;
    /**
     * The value that represents border cells (obstacles)
     */
    private final int borderValue;
    /**
     * The value that represents empty cells to be filled
     */
    private final int emptyValue;
    /**
     * The 2D matrix on which flood fill operations are performed
     */
    private final int[][] matrix;

    /**
     * Constructs a FloodFill instance with the specified matrix and value
     * definitions.
     *
     * @param matrix      the 2D integer matrix to perform flood fill on
     * @param borderValue the value that represents border/obstacle cells
     * @param emptyValue  the value that represents empty cells to be filled
     * @throws IllegalArgumentException if matrix is null or empty
     */
    public FloodFill(int[][] matrix, int borderValue, int emptyValue) {
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
        this.borderValue = borderValue;
        this.emptyValue = emptyValue;
    }

    /**
     * Callback method invoked whenever a cell's value is changed during flood fill.
     * Subclasses should implement this method to handle value change notifications,
     * such as updating a graphical display or logging changes.
     *
     * @param x        the x-coordinate (column) of the changed cell
     * @param y        the y-coordinate (row) of the changed cell
     * @param newValue the new value assigned to the cell
     */
    public abstract void onValueChange(int x, int y, int newValue);

    /**
     * Generates a new value to be used for filling a connected region.
     * This method is called once per connected region and allows subclasses
     * to implement different value generation strategies (e.g., random colors,
     * sequential numbers, etc.).
     *
     * @return the value to use for filling the current region
     */
    public abstract int generateValue();

    /**
     * Executes the flood fill algorithm on the entire matrix.
     * Scans the matrix row by row, column by column, and fills each
     * connected region of empty cells with a generated value.
     */
    public void execute() {
        // Scan the entire matrix to find all empty regions
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Only process cells that are not borders and are empty
                if (!isBorder(x, y) && isEmpty(x, y)) {
                    // Fill the entire connected region starting from this cell
                    floodFillRegion(x, y, generateValue());
                }
            }
        }
    }

    /**
     * Performs flood fill on a connected region starting from the given
     * coordinates. Uses an iterative stack-based approach to avoid stack
     * overflow issues that can occur with recursive implementations on large regions.
     * <p>
     * The algorithm uses a 4-connected neighborhood (up, down, left, right)
     * and stops when it encounters borders or already-filled cells.
     *
     * @param startX   the starting x-coordinate (column)
     * @param startY   the starting y-coordinate (row)
     * @param newValue the value to fill the region with
     */
    private void floodFillRegion(int startX, int startY, int newValue) {
        // Use a stack to implement iterative flood fill (avoids recursion depth issues)
        Stack<Point> stack = new Stack<>();

        // Start with the initial point
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            int x = current.x();
            int y = current.y();

            // Skip points that are outside the matrix boundaries
            if (!isWithinBounds(x, y)) {
                continue;
            }

            // Check if this cell has already been processed or is a border
            boolean alreadyChanged = matrix[y][x] == newValue;

            if (alreadyChanged || isBorder(x, y)) {
                continue;
            }

            // Fill this cell with the new value
            matrix[y][x] = newValue;

            // Notify subclass about the value change
            onValueChange(x, y, newValue);

            // Add all 4-connected neighbors to the stack for processing
            // The order doesn't matter as we're using a stack (LIFO)
            stack.push(new Point(x + 1, y)); // Right
            stack.push(new Point(x - 1, y)); // Left
            stack.push(new Point(x, y + 1)); // Down
            stack.push(new Point(x, y - 1)); // Up
        }
    }

    /**
     * Checks if the given coordinates are within the matrix boundaries.
     *
     * @param x the x-coordinate (column) to check
     * @param y the y-coordinate (row) to check
     * @return true if the coordinates are within bounds, false otherwise
     */
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Checks if the cell at the given coordinates is a border cell.
     *
     * @param x the x-coordinate (column) of the cell
     * @param y the y-coordinate (row) of the cell
     * @return true if the cell contains the border value, false otherwise
     */
    private boolean isBorder(int x, int y) {
        return matrix[y][x] == borderValue;
    }

    /**
     * Checks if the cell at the given coordinates is empty (available for filling).
     *
     * @param x the x-coordinate (column) of the cell
     * @param y the y-coordinate (row) of the cell
     * @return true if the cell contains the empty value, false otherwise
     */
    private boolean isEmpty(int x, int y) {
        return matrix[y][x] == emptyValue;
    }

    /**
     * Simple record to represent a 2D point with x and y coordinates.
     * Used internally by the flood fill algorithm to track positions in the stack.
     *
     * @param x the x-coordinate (column)
     * @param y the y-coordinate (row)
     */
    private record Point(int x, int y) {
    }

}
