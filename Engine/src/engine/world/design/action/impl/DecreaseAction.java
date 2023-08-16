package engine.world.design.action.impl;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.ExpressionType;

public class DecreaseAction extends AbstractAction {

    private final String property;
    private final String byExpression;

    public DecreaseAction(EntityDefinition entityDefinition, String property, String byExpression) {
        super(ActionType.DECREASE, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property);
        }
        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType())){
            Integer v = PropertyType.DECIMAL.convert(propertyInstance.getValue());
            int x = ExpressionType.DECIMAL.evaluate(byExpression,context);
            // actual calculation
            int result = v-x;
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(propertyInstance.getPropertyDefinition().getType())){
            Float v = PropertyType.FLOAT.convert(propertyInstance.getValue());
            float x = ExpressionType.FLOAT.evaluate(byExpression,context);
            // actual calculation
            float result = v-x;
            propertyInstance.updateValue(result);
        }
    }

}
