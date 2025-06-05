package br.pucminas.fpaa.floodfill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FloodFillEdgeCasesTest {

    @Test
    public void testFloodFill_EmptyMatrixAllZeros() {
        int[][] matrix = {
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 5;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(fillValue, matrix[y][x],
                        String.format("Cell at (%d, %d) should be filled", x, y));
            }
        }

        assertEquals(9, floodFill.getValueChanges().size(), "Should have 9 value changes");
    }

    @Test
    public void testFloodFill_SameValueGeneration() {

        int[][] matrix = {
                { 1, 1, 1 },
                { 1, 0, 1 },
                { 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 0;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "Center cell should have the fill value");

        assertEquals(0, floodFill.getValueChanges().size(),
                "Should have no value changes when fill value equals empty value");
    }

    @Test
    public void testFloodFill_AlreadyFilledRegion() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1 },
                { 1, 5, 5, 0, 1 },
                { 1, 5, 5, 0, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 5;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][3], "Empty cell should be filled");
        assertEquals(fillValue, matrix[2][3], "Empty cell should be filled");

        assertEquals(fillValue, matrix[1][1], "Already filled cell should remain");
        assertEquals(fillValue, matrix[1][2], "Already filled cell should remain");
        assertEquals(fillValue, matrix[2][1], "Already filled cell should remain");
        assertEquals(fillValue, matrix[2][2], "Already filled cell should remain");

        assertEquals(2, floodFill.getValueChanges().size(), "Should have 2 value changes for new fills only");
    }

    @Test
    public void testFloodFill_NegativeValues() {

        int[][] matrix = {
                { -1, -1, -1 },
                { -1, -2, -1 },
                { -1, -1, -1 }
        };
        int borderValue = -1;
        int emptyValue = -2;
        int fillValue = 10;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "Negative empty value should be filled");
        assertEquals(borderValue, matrix[0][0], "Negative border should remain unchanged");
        assertEquals(1, floodFill.getValueChanges().size(), "Should have exactly one value change");
    }

    @Test
    public void testFloodFill_LargeValues() {

        int[][] matrix = {
                { 1000000, 1000000, 1000000 },
                { 1000000, 999999, 1000000 },
                { 1000000, 1000000, 1000000 }
        };

        int borderValue = 1000000;
        int emptyValue = 999999;
        int fillValue = 888888;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "Large empty value should be filled");
        assertEquals(borderValue, matrix[0][0], "Large border should remain unchanged");
        assertEquals(1, floodFill.getValueChanges().size(), "Should have exactly one value change");
    }

    @Test
    public void testFloodFill_RectangularMatrix() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 7;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int x = 1; x < 5; x++) {
            assertEquals(fillValue, matrix[1][x],
                    String.format("Empty cell at (1, %d) should be filled", x));
        }

        assertEquals(4, floodFill.getValueChanges().size(), "Should have 4 value changes");
    }

    @Test
    public void testFloodFill_TallRectangularMatrix() {

        int[][] matrix = {
                { 1, 1, 1 },
                { 1, 0, 1 },
                { 1, 0, 1 },
                { 1, 0, 1 },
                { 1, 0, 1 },
                { 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 9;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int y = 1; y < 5; y++) {
            assertEquals(fillValue, matrix[y][1],
                    String.format("Empty cell at (%d, 1) should be filled", y));
        }

        assertEquals(4, floodFill.getValueChanges().size(), "Should have 4 value changes");
    }

    @Test
    public void testFloodFill_DiagonalPattern() {

        int[][] matrix = {
                { 0, 1, 0, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 3;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[0][0], "Top-left corner should be filled");
        assertEquals(fillValue, matrix[0][2], "Top-center should be filled");
        assertEquals(fillValue, matrix[0][4], "Top-right corner should be filled");
        assertEquals(fillValue, matrix[2][0], "Middle-left should be filled");
        assertEquals(fillValue, matrix[2][2], "Center should be filled");
        assertEquals(fillValue, matrix[2][4], "Middle-right should be filled");
        assertEquals(fillValue, matrix[4][0], "Bottom-left corner should be filled");
        assertEquals(fillValue, matrix[4][2], "Bottom-center should be filled");
        assertEquals(fillValue, matrix[4][4], "Bottom-right corner should be filled");

        assertEquals(9, floodFill.getValueChanges().size(), "Should have 9 separate regions filled");
    }

    @Test
    public void testFloodFill_ComplexConnectedRegion() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 0, 0, 1 },
                { 1, 0, 1, 1, 1, 0, 1 },
                { 1, 0, 1, 0, 1, 0, 1 },
                { 1, 0, 1, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 4;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int x = 1; x < 6; x++) {
            assertEquals(fillValue, matrix[1][x], String.format("Top row (%d, 1) should be filled", x));
            assertEquals(fillValue, matrix[5][x], String.format("Bottom row (%d, 5) should be filled", x));
        }

        for (int y = 2; y < 5; y++) {
            assertEquals(fillValue, matrix[y][1], String.format("Left column (1, %d) should be filled", y));
            assertEquals(fillValue, matrix[y][5], String.format("Right column (5, %d) should be filled", y));
        }

        assertEquals(fillValue, matrix[3][3], "Center isolated cell should be filled");
        assertEquals(fillValue, matrix[4][3], "Center isolated cell should be filled");

        assertEquals(borderValue, matrix[2][2], "Inner border should remain");
        assertEquals(borderValue, matrix[2][3], "Inner border should remain");
        assertEquals(borderValue, matrix[2][4], "Inner border should remain");

        assertEquals(18, floodFill.getValueChanges().size(), "Should have 18 cells filled (16 outer + 2 center)");
    }
}
