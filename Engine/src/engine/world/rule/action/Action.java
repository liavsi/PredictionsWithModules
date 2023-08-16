package engine.world.rule.action;

import engine.world.design.execution.entity.Entity;
import schema.generated.PRDAction;

import java.util.Map;

public class Action {

    Map<String,String> arguments;
    ActionType type;
    Entity mainEntity;

    public Action(PRDAction prdAction,Entity mainEntity){

        type = ActionType.valueOf(prdAction.getType());
        this.mainEntity = mainEntity;
        arguments.put("property",prdAction.getProperty());
        arguments.put("by",prdAction.getBy());
        arguments.put("result-prop",prdAction.getResultProp());
        arguments.put("multiply arg1",prdAction.getPRDMultiply().getArg1());
        arguments.put("multiply arg2",prdAction.getPRDMultiply().getArg2());
        arguments.put("divide arg1",prdAction.getPRDDivide().getArg1());
        arguments.put("divide arg2",prdAction.getPRDDivide().getArg2());
        // TODO: 04/08/2023  
    }
}
