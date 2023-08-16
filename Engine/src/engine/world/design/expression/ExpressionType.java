package engine.world.design.expression;

import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import engine.world.design.execution.context.Context;

import java.util.Random;

public enum ExpressionType {

    FLOAT {
        @Override
        public Float evaluate(String expression, Context context) {
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        float envPropertyVal = PropertyType.FLOAT.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return (float) res;
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            else if(context.getPrimaryEntityInstance().getPropertyByName(expression) != null){
                return PropertyType.FLOAT.convert(context.getPrimaryEntityInstance().getPropertyByName(expression).getValue());
            }
            else{
                try{
                    float floatFreeVal = Float.parseFloat(expression);
                    return floatFreeVal;
                }
                catch (NumberFormatException e2){
                    //"Unable to convert the string to float"
                }
            }
            return null;
        }
    },
    DECIMAL{
        @Override
        public Integer evaluate(String expression, Context context) {
            int openingParen = expression.indexOf("(");
            int closingParen = expression.indexOf(")");
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        int envPropertyVal = PropertyType.DECIMAL.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return res;
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            else if(context.getPrimaryEntityInstance().getPropertyByName(expression) != null){
                return PropertyType.DECIMAL.convert(context.getPrimaryEntityInstance().getPropertyByName(expression).getValue());
            }
            else{
                try{
                    int freeVal = Integer.parseInt(expression);
                    return freeVal;
                }
                catch (NumberFormatException e2) {
                    //"Unable to convert the string to float"
                }
            }
            return null;
        }
    }, 
    STRING{
        @Override
        public String evaluate(String expression, Context context) {// TODO: 15/08/2023
            return null;
        }
    },
    BOOLEAN{
        @Override
        public Boolean evaluate(String expression, Context context) { // TODO: 15/08/2023
            return false;
        }
    };
    // TODO: 13/08/2023 complete this implementation
    ;

    public abstract <T> T evaluate(String expression, Context context);
}
