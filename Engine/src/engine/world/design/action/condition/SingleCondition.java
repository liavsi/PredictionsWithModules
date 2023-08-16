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
    private String property;
    private EntityDefinition entity;
    //private Operator operaton1;
    private String operator;
    private String value;

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
               if(propertyInstance.getValue() == propertyType.convert(realValue)){
                   return true;
               }
               else {
                   return false;
               }
            //   return (operaton1.runOperator1(propertyInstance.getValue(),propertyInstance.getPropertyDefinition().getType().convert(value)));
            }
            case("!="):{
                if(propertyInstance.getValue() != propertyType.convert(realValue)){
                    return true;
                }
                else {
                    return false;
                }
            }
            case("Bt"):{
                if(verifyNumericPropertyType(propertyInstance)){
                    if((float) propertyInstance.getValue() > (float) propertyType.convert(realValue)){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    throw new RuntimeException("Bt can't be done on non numeric values");
                }
            }
            case ("Lt"):{
                if(verifyNumericPropertyType(propertyInstance)){
                    if((float) propertyInstance.getValue() < (float) propertyType.convert(realValue)){
                        return true;
                    }
                    else {
                        return false;
                    }
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
