package engine.world.design.action.condition;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.impl.EntityInstanceImpl;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.ExpressionType;

import java.util.List;

public class SingleCondition implements Condition{
    private final String property;
    private final EntityDefinition entity;
    //private Operator operaton1;
    private final String operator;
    private final String value;

    public SingleCondition(EntityDefinition entity, String property, String value, String operator) {// TODO: 15/08/2023
        this.property = property;
        this.value = value;
        this.entity = entity;
        this.operator = operator;
    }

    @Override
    public boolean evaluate(Context context) {
        PropertyInstance propertyInstance =  context.getPrimaryEntityInstance().getPropertyByName(property);
        //return operaton1.runOperator(propertyInstance,value);

        PropertyType propertyType = propertyInstance.getPropertyDefinition().getType();
        Object realValue = ExpressionType.valueOf(propertyType.toString()).evaluate(value, context);
        // TODO: 15/08/2023  boolean res = false;
        switch (operator){
            case ("="):{
                return propertyInstance.getValue() == propertyType.convert(realValue);
            //   return (operaton1.runOperator1(propertyInstance.getValue(),propertyInstance.getPropertyDefinition().getType().convert(value)));
            }
            case("!="):{
                return propertyInstance.getValue() != propertyType.convert(realValue);
            }
            case("bt"):{
                if(verifyNumericPropertyType(propertyInstance)){
                    if (propertyType == PropertyType.DECIMAL) {
                        return (int) propertyType.convert(propertyInstance.getValue()) > (int) propertyType.convert(realValue);
                    }
                    else
                        return (float) (propertyType.convert(propertyInstance.getValue())) > (float) propertyType.convert(realValue);
                }
                else {
                    throw new RuntimeException("Bt can't be done on non numeric values");
                }
            }
            case ("lt"):{
                if(verifyNumericPropertyType(propertyInstance)){
                    return (float) propertyInstance.getValue() < (float) propertyType.convert(realValue);
                }
                else{
                    throw new RuntimeException("Lt can't be done on non numeric values");
                }
            }
            default:{
                throw new RuntimeException("Invalid operator");
            }
        }
    }
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return
                PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
}
