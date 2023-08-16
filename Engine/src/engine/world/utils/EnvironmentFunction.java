package engine.world.utils;

import engine.world.HasProperties;
import engine.world.property.ActualProperty;

import java.util.Random;

public enum EnvironmentFunction implements Expression {

    ENVIRONMENT{
        @Override
        public ActualProperty evaluate() {
            ActualProperty environmentVariable = null;
            if (argument instanceof PropertyExpression) {
                // TODO: 01/08/2023 have to check if this is the right way to write this type of code
                PropertyExpression propertyExpression = (PropertyExpression)argument;
                environmentVariable =  myWorld.getPropertyByName(propertyExpression.evaluate());
            }
            else {
                throw new RuntimeException("not Property Expression delivered");
            }
            return environmentVariable;
        }
    },
    RANDOM{
        @Override
        public Integer evaluate() {
            Integer maxRangeNumber = null;
            if (argument instanceof NumericExpression) {
                maxRangeNumber  = (Integer) argument.evaluate();
            }
            else {
                throw new RuntimeException("Not a valid Number to Random Function");
            }
            Random random = new Random();

            // Generate a random number between 0 and maxRange
            return  random.nextInt(maxRangeNumber);
        }
    };

    protected Expression argument;
    protected HasProperties myWorld;
    // EVALUATE{},
    // PRECENT{},
    // TICKS{};

}
