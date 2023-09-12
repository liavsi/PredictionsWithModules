package engine.world.design.action.api;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;

import java.util.ArrayList;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition mainEntity;
    private final InteractiveEntity interactiveEntity;

    protected AbstractAction(ActionType actionType, EntityDefinition entityDefinition, InteractiveEntity interactiveEntity) {
        this.actionType = actionType;
        this.mainEntity = entityDefinition;
        this.interactiveEntity = interactiveEntity;
    }
    @Override
    public InteractiveEntity getInteractiveEntity() {
        return interactiveEntity;
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
    public ArrayList<EntityInstance> getSecondaryInstances(){
        ArrayList<EntityInstance> secondaryInstances = new ArrayList<>();
        if(getInteractiveEntity().getConditionAction() == null){
            int count = getInteractiveEntity().getCount();
            if(count > interactiveEntity.getSecondaryEntity().getPopulation()){
                count = interactiveEntity.getSecondaryEntity().getPopulation();
            }
            for (int i=0; i<count; i++){
               // secondaryInstances.add();
            }
        }
        return secondaryInstances;
    }

    @Override
    public EntityDefinition getMainEntity() {
        return mainEntity;
    }
}