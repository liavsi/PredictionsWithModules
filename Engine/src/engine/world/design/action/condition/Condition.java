package engine.world.design.action.condition;

import engine.world.design.action.api.Action;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;

public interface Condition {

    public boolean evaluate(Context context);

}
