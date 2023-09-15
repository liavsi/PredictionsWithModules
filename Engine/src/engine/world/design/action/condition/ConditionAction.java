package engine.world.design.action.condition;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ConditionActionDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

import java.util.ArrayList;
import java.util.List;

public class ConditionAction extends AbstractAction{

    private final List<Action> thenActions;
    private final List<Action> elseActions;
    private final Condition condition;
    public ConditionAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, Condition condition) {
        super(ActionType.CONDITION, entityDefinition, interactiveEntity);
        this.condition = condition;
        thenActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public List<Action> getThenActions() {
        return thenActions;
    }

    public List<Action> getElseActions() {
        return elseActions;
    }
    @Override
    public void invoke(Context context) {
        if (condition.evaluate(context)) {
            thenActions.forEach(action -> action.invoke(context));
        } else {
            elseActions.forEach(action -> action.invoke(context));
        }
    }
    @Override
    public ActionDTO createActionDTO() {
        return new ConditionActionDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),condition.createConditionDTO(), thenActions.size(), elseActions.size());
    }
}
