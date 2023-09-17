package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.SetDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.Expression;
import engine.world.design.expression.ExpressionType;

public class SetAction extends AbstractAction {

    private final String property;
    private final String value;

    public SetAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String property, String value) {
        super(ActionType.SET, entityDefinition, interactiveEntity);
        this.property = property;
        this.value = value;
    }

    @Override
    public void invoke(Context context) {
        Expression expression = new Expression();
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        Object type = propertyInstance.getPropertyDefinition().getType();
        if(PropertyType.DECIMAL.equals(type)){
            Object resultObj = expression.evaluate(value,context);
            int result = PropertyType.DECIMAL.convert(resultObj);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(type)){
            Object resultObj = expression.evaluate(value,context);
            float result = PropertyType.FLOAT.convert(resultObj);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.STRING.equals(type)){
            Object resultObj = expression.evaluate(value,context);
            String result = PropertyType.STRING.convert(resultObj);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.BOOLEAN.equals(type)) {
            Object resultObj = expression.evaluate(value,context);
            boolean result = PropertyType.BOOLEAN.convert(resultObj);
            propertyInstance.updateValue(result);
        }

    }

    @Override
    public ActionDTO createActionDTO() {
        return new SetDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),property,value);
    }
}
