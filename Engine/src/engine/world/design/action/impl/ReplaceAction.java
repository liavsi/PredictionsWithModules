package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ReplaceDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

public class ReplaceAction extends AbstractAction {
    private final String mode;


    public ReplaceAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String mode) {
        super(ActionType.REPLACE, entityDefinition, interactiveEntity);
        this.mode = mode;
    }

    @Override
    public void invoke(Context context) {

    }

    @Override
    public ActionDTO createActionDTO() {
        return new ReplaceDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),mode);
    }
}
