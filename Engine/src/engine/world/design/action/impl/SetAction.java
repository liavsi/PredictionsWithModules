package engine.world.design.action.impl;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.ExpressionType;
import engine.world.utils.Expression;

public class SetAction extends AbstractAction {

    private String property;
    private String value;
    protected SetAction(EntityDefinition entityDefinition, String property, String value) {
        super(ActionType.SET, entityDefinition);
        this.property = property;
        this.value = value;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        Object type = propertyInstance.getPropertyDefinition().getType();
        if(PropertyType.DECIMAL.equals(type)){
            int result = ExpressionType.DECIMAL.evaluate(value,context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(type)){
            float result = ExpressionType.FLOAT.evaluate(value,context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.STRING.equals(type)){
            String result = ExpressionType.STRING.evaluate(value,context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.BOOLEAN.equals(type)) {
            boolean result = ExpressionType.BOOLEAN.evaluate(value,context);
            propertyInstance.updateValue(result);
        }

    }
}
