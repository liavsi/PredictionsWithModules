package engine.world.design.action.condition;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

import java.util.List;

public class ConditionAction extends AbstractAction{

    private List<Action> thanActions;
    private List<Action> elseActions;

    private Condition condition;
    public ConditionAction(EntityDefinition entityDefinition,Condition condition) {
        super(ActionType.CALCULATION, entityDefinition);
        this.condition = condition;
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
