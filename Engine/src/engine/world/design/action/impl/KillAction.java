package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.KillDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition,EntityDefinition secondEntity) {
        super(ActionType.KILL, entityDefinition, interactiveEntity, secondEntity);
    }

    @Override
    public void invoke(Context context) {
        context.removeEntity(context.getPrimaryEntityInstance());
    }

    @Override
    public ActionDTO createActionDTO() {
        return new KillDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO());
    }

}
