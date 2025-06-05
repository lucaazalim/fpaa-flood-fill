package br.pucminas.fpaa.floodfill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FloodFillTest {

    @Test
    public void testSimpleFloodFill_SingleEmptyCell() {

        int[][] matrix = {
                { 1, 1, 1 },
                { 1, 0, 1 },
                { 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 5;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "Single empty cell should be filled");
        assertEquals(1, floodFill.getValueChanges().size(), "Should have exactly one value change");

        TestFloodFill.ValueChange change = floodFill.getValueChanges().get(0);
        assertEquals(1, change.x());
        assertEquals(1, change.y());
        assertEquals(fillValue, change.newValue());
    }

    @Test
    public void testFloodFill_LargeEmptyRegion() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 3;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int y = 1; y < 4; y++) {
            for (int x = 1; x < 4; x++) {
                assertEquals(fillValue, matrix[y][x],
                        String.format("Cell at (%d, %d) should be filled", x, y));
            }
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(borderValue, matrix[0][i], "Top border should remain unchanged");
            assertEquals(borderValue, matrix[4][i], "Bottom border should remain unchanged");
            assertEquals(borderValue, matrix[i][0], "Left border should remain unchanged");
            assertEquals(borderValue, matrix[i][4], "Right border should remain unchanged");
        }

        assertEquals(9, floodFill.getValueChanges().size(), "Should have 9 value changes");
    }

    @Test
    public void testFloodFill_MultipleDisconnectedRegions() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 7;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "First region should be filled");
        assertEquals(fillValue, matrix[1][3], "Second region should be filled");
        assertEquals(fillValue, matrix[3][1], "Third region should be filled");
        assertEquals(fillValue, matrix[3][3], "Fourth region should be filled");

        assertEquals(4, floodFill.getValueChanges().size(), "Should have 4 value changes for 4 regions");
    }

    @Test
    public void testFloodFill_LShapedRegion() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 1, 1, 1 },
                { 1, 0, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 9;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1]);
        assertEquals(fillValue, matrix[1][2]);
        assertEquals(fillValue, matrix[1][3]);
        assertEquals(fillValue, matrix[2][1]);
        assertEquals(fillValue, matrix[3][1]);

        assertEquals(borderValue, matrix[2][2]);
        assertEquals(borderValue, matrix[2][3]);
        assertEquals(borderValue, matrix[3][2]);
        assertEquals(borderValue, matrix[3][3]);

        assertEquals(5, floodFill.getValueChanges().size(), "Should have 5 value changes for L-shaped region");
    }

    @Test
    public void testFloodFill_NoEmptyRegions() {

        int[][] matrix = {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 1 }
        };
        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 5;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(borderValue, matrix[y][x],
                        String.format("Cell at (%d, %d) should remain as border", x, y));
            }
        }

        assertEquals(0, floodFill.getValueChanges().size(), "Should have no value changes");
    }

    @Test
    public void testFloodFill_AllEmptyExceptBorders() {

        int[][] matrix = {
                { 1, 1, 1, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 1 }
        };
        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 2;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1]);
        assertEquals(fillValue, matrix[1][2]);
        assertEquals(fillValue, matrix[2][1]);
        assertEquals(fillValue, matrix[2][2]);

        assertEquals(4, floodFill.getValueChanges().size(), "Should have 4 value changes");
    }

    @Test
    public void testFloodFill_SingleRowMatrix() {
        int[][] matrix = { { 1, 0, 0, 1 } };
        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 8;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[0][1]);
        assertEquals(fillValue, matrix[0][2]);
        assertEquals(borderValue, matrix[0][0]);
        assertEquals(borderValue, matrix[0][3]);

        assertEquals(2, floodFill.getValueChanges().size(), "Should have 2 value changes");
    }

    @Test
    public void testFloodFill_SingleColumnMatrix() {
        int[][] matrix = { { 1 }, { 0 }, { 0 }, { 1 } };
        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 6;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(borderValue, matrix[0][0]);
        assertEquals(fillValue, matrix[1][0]);
        assertEquals(fillValue, matrix[2][0]);
        assertEquals(borderValue, matrix[3][0]);

        assertEquals(2, floodFill.getValueChanges().size(), "Should have 2 value changes");
    }

    @Test
    public void testFloodFill_ComplexMatrix() {

        int[][] matrix = {
                { 1, 1, 1, 1, 1, 1, 1 },
                { 1, 0, 0, 1, 0, 0, 1 },
                { 1, 0, 1, 1, 1, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 0, 1, 1, 1 },
                { 1, 0, 0, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1 }
        };

        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 4;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[1][1], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[1][2], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[2][1], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[3][1], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[3][2], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[3][3], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[3][4], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[3][5], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[4][3], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[5][1], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[5][2], "Should be filled - part of main region");
        assertEquals(fillValue, matrix[5][3], "Should be filled - part of main region");

        assertEquals(fillValue, matrix[1][4], "Should be filled - separate region");
        assertEquals(fillValue, matrix[1][5], "Should be filled - separate region");
        assertEquals(fillValue, matrix[2][5], "Should be filled - separate region");

        assertEquals(fillValue, matrix[5][5], "Should be filled - isolated cell");

        assertEquals(16, floodFill.getValueChanges().size(), "Should have 16 value changes total");
    }

    @Test
    public void testFloodFill_MinimalMatrix() {

        int[][] matrix = { { 0 } };
        int borderValue = 1;
        int emptyValue = 0;
        int fillValue = 9;

        TestFloodFill floodFill = new TestFloodFill(matrix, borderValue, emptyValue, fillValue);

        floodFill.execute();

        assertEquals(fillValue, matrix[0][0], "Single empty cell should be filled");
        assertEquals(1, floodFill.getValueChanges().size(), "Should have exactly one value change");
    }
}
