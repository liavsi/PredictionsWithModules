package engine.world.design.action.api;

import DTOManager.impl.actionDTO.ActionDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.property.PropertyInstance;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition mainEntity;
    private EntityDefinition secondEntity = null;

    protected AbstractAction(ActionType actionType, EntityDefinition entityDefinition,EntityDefinition secondEntity) {
        this.actionType = actionType;
        this.mainEntity = entityDefinition;
        this.secondEntity = secondEntity;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextEntity() {
        return mainEntity;
    }

    @Override
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return
                PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }

    @Override
    public EntityDefinition getMainEntity() {
        return mainEntity;
    }
}