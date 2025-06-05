package br.pucminas.fpaa.floodfill;

import java.util.ArrayList;
import java.util.List;

public class TestFloodFill extends FloodFill {

    private final int valueToGenerate;
    private final List<ValueChange> valueChanges;

    public TestFloodFill(int[][] matrix, int borderValue, int emptyValue, int valueToGenerate) {
        super(matrix, borderValue, emptyValue);
        this.valueToGenerate = valueToGenerate;
        this.valueChanges = new ArrayList<>();
    }

    @Override
    public void onValueChange(int x, int y, int newValue) {
        valueChanges.add(new ValueChange(x, y, newValue));
    }

    @Override
    public int generateValue() {
        return valueToGenerate;
    }

    public List<ValueChange> getValueChanges() {
        return valueChanges;
    }

    public record ValueChange(int x, int y, int newValue) {
    }
}
