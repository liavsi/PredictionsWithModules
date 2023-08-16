package engine.world.utils;

import java.util.Map;

public class NumericExpression implements Expression{
    private float value;

    public NumericExpression(String numberAsString) {
        try {

            value = Float.parseFloat(numberAsString);
        }
        catch (NumberFormatException e) {

        }
    }
    @Override
    public Float evaluate() {
        return value;
    }
}
