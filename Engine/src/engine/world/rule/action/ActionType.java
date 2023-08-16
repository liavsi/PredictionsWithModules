package engine.world.rule.action;

import engine.world.HasProperties;
import engine.world.property.ActualProperty;
import engine.world.utils.BaseExpression;
import engine.world.utils.Expression;
import engine.world.utils.NumericExpression;

import java.util.Map;

public enum ActionType implements Actionable {
    INCREASE{
        @Override
        public void ExecuteAction(Map<String,String> arguments, HasProperties mainEntity) {
            BaseExpression b = new BaseExpression(arguments.get("by"));
            Expression obj = b.expressionType(mainEntity);
            obj.evaluate();
            //Float by = new NumericExpression(arguments.get("by")).evaluate(); //To check if numeric expression
            //ActualProperty property = mainEntity.getPropertyByName(arguments.get("property"));
            //property.increaseValue(by);
        }
    },
    DECREASE {
        @Override
        public void ExecuteAction(Map<String,String> arguments,HasProperties mainEntity) {
            Float by = new NumericExpression(arguments.get("by")).evaluate();
            ActualProperty property = mainEntity.getPropertyByName(arguments.get("property"));
            property.decreaseValue(by);
        }
    };
//    CALCULATION {
//
//    },
//    CONDITION {
//
//    },
//    SET {
//
//    },
//    KILL {
//
//    },
//    REPLACE{
////         EXE 2
//    },
//    PROXIMITY {
////        EXE 2
//    };

    public abstract void ExecuteAction(Map<String, String> arguments, HasProperties mainEntity);
}
