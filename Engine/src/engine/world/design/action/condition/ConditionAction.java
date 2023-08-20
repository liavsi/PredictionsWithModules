package engine.world.design.action.condition;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

import java.util.ArrayList;
import java.util.List;

public class ConditionAction extends AbstractAction{

    private final List<Action> thanActions;
    private final List<Action> elseActions;

    private final Condition condition;
    public ConditionAction(EntityDefinition entityDefinition,Condition condition) {
        super(ActionType.CONDITION, entityDefinition);
        this.condition = condition;
        thanActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public List<Action> getThanActions() {
        return thanActions;
    }

    public List<Action> getElseActions() {
        return elseActions;
    }
    @Override
    public void invoke(Context context) {
        if(condition.evaluate(context)){
            thanActions.forEach(action -> action.invoke(context));
        }
        else{
            elseActions.forEach(action -> action.invoke(context));
        }
    }
}
