package engine.world.utils;

import engine.world.HasProperties;
import engine.world.property.ActualProperty;

public class BaseExpression {

    String expression;

    public BaseExpression(String expression) {
        this.expression = expression;
    }

    public Expression expressionType(HasProperties mainEntity){
        Class<EnvironmentFunction> clazz = EnvironmentFunction.class;
        Expression res;
        EnvironmentFunction[] types = clazz.getEnumConstants();
        for(EnvironmentFunction type: types){
            if(type.toString() == expression){
                res = EnvironmentFunction.valueOf(type.toString());
            }
        }
        ActualProperty property = mainEntity.getPropertyByName(expression);
        if(property != null){
            res = new PropertyExpression(expression);
        }
        else
            res = new NumericExpression(expression); //not just numeric.

      return res;
    }

}
