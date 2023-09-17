package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ReplaceDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;

import java.util.Map;

public class ReplaceAction extends AbstractAction {
    private final String mode;
    private final EntityDefinition createEntity;

    public ReplaceAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String mode, EntityDefinition createEntity) {
        super(ActionType.REPLACE, entityDefinition, interactiveEntity);
        this.mode = mode;
        this.createEntity = createEntity;
    }
    @Override
    public void invoke(Context context) {
        if (mode.equals("scratch")){
            context.removeEntity(context.getPrimaryEntityInstance());
            context.getEntityInstanceManager().create(context.getSecondaryEntity().getEntityDefinition(),context.getGrid());
        } else if (mode.equals("derived")) {
            EntityInstance entityInstance = context.getEntityInstanceManager().create(context.getSecondaryEntity().getEntityDefinition(),context.getGrid());
            for (Map.Entry<String,PropertyInstance> entry: context.getPrimaryEntityInstance().getProperties().entrySet()){
                String key = entry.getKey();
                PropertyInstance value = entry.getValue();
                if(entityInstance.getProperties().containsKey(key)){ // if the create entity has property with the same name of the kill entity
                    PropertyInstance propertyInstance = entityInstance.getPropertyByName(key);
                    if (propertyInstance.getPropertyDefinition().getType().equals(value.getPropertyDefinition().getType())){ // // if the create entity has property with the same kind of the kill entity
                        propertyInstance.updateValue(value.getValue());
                    }
                }
            }
            context.removeEntity(context.getPrimaryEntityInstance());
        }
        else {
            throw new RuntimeException("Not a valid mode in the replace action");
        }
    }
    @Override
    public ActionDTO createActionDTO() {
        return new ReplaceDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),mode);
    }
}
