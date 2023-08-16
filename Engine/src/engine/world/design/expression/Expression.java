package engine.world.design.expression;

import engine.world.design.execution.context.Context;

import java.util.Random;

public class Expression {

//    public <T> T evaluate(String expression, Context context) {
//        int openingParen = expression.indexOf("(");
//        int closingParen = expression.indexOf(")");
//        if(openingParen != -1 && closingParen != -1) {
//            String envFunc = expression.substring(0,openingParen);
//            String envFuncArg = expression.substring(openingParen + 1,closingParen);
//            switch (envFunc) {
//                case ("environment"): {
//                    T envPropertyVal = (T) context.getEnvironmentVariable(envFuncArg).getValue();
//                    return envPropertyVal;
//                }
//                case ("random"): {
//                    int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
//                    Random random = new Random();
//                    int res = random.nextInt(randomVal + 1);
//                    return (T) res;
//                }
//                default:
//                    throw new RuntimeException("There is no such environment function");
//            }
//        }
//        else if(context.getPrimaryEntityInstance().getPropertyByName(expression) != null){
//            return (int) context.getPrimaryEntityInstance().getPropertyByName(expression).getValue();
//        }
//        else{
//            try{
//                int floatFreeVal = Integer.parseInt(expression);
//                return floatFreeVal;
//            }
//            catch (NumberFormatException e2){
//                //"Unable to convert the string to float"
//            }
//        }
//        return null;
//    }
}
