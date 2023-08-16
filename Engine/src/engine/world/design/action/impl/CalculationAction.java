package engine.world.design.action.impl;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.calculation.CalculationType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.ExpressionType;

public class CalculationAction extends AbstractAction {

    private CalculationType calculationType;
    private String property;
    private String arg1, arg2;


    protected CalculationAction(EntityDefinition entityDefinition, String property, String arg1, String arg2) {
        super(ActionType.CALCULATION, entityDefinition);
        this.property = property;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property);
        }
        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            int result = 0;
            int num1 = ExpressionType.DECIMAL.evaluate(arg1,context);
            int num2 = ExpressionType.DECIMAL.evaluate(arg2,context);
            if (CalculationType.MULTIPLY.equals(calculationType)){
                result = num1*num2;
            }
            else if (CalculationType.DIVIDE.equals(calculationType)){
                result = num1/num2;
            }
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            float result = 0;
            float num1 = ExpressionType.FLOAT.evaluate(arg1,context);
            float num2 = ExpressionType.FLOAT.evaluate(arg2,context);
            if (CalculationType.MULTIPLY.equals(calculationType)){
                result = num1*num2;
            }
            else if (CalculationType.DIVIDE.equals(calculationType)){
                result = num1/num2;
            }
            propertyInstance.updateValue(result);
        }
    }
}
