package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.KillDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;

public class KillAction extends AbstractAction {

    public KillAction(EntityDefinition entityDefinition,EntityDefinition secondEntity) {
        super(ActionType.KILL, entityDefinition,secondEntity);
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
