package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.expression.ExpressionType;

import java.util.ArrayList;

public class ProximityAction extends AbstractAction {

    private final String ofExpression;
    private final int columns;
    private final int rows;
    private ArrayList<Action> actions;

    public ProximityAction(ActionType actionType, EntityDefinition entityDefinition, EntityDefinition secondEntity, String ofExpression, int columns, int rows, ArrayList<Action> actions) {
        super(actionType, entityDefinition, interactiveEntity, secondEntity);
        this.ofExpression = ofExpression;
        this.columns = columns;
        this.rows = rows;
        this.actions = actions;
    }

    @Override
    public void invoke(Context context) {
        if(isClose(context)){
            for (Action action: actions){
                action.invoke(context);
            }
        }
    }

    @Override
    public ActionDTO createActionDTO() {
        return null;
    }

    private boolean isClose(Context context){
        EntityInstance sourceEntity = context.getPrimaryEntityInstance();
        EntityInstance targetEntity = context.getSecondaryEntity();
        int depth = ExpressionType.DECIMAL.evaluate(ofExpression,context);
        int x = sourceEntity.getCoordinate().getX();
        int y = sourceEntity.getCoordinate().getY();
        int xRight = (x + depth) % columns;
        int xLeft = (x - depth) % columns;
        int yUp = (y + depth) % rows;
        int yDown = (y + depth) % rows;
        int xTarget = targetEntity.getCoordinate().getX();
        int yTarget = targetEntity.getCoordinate().getY();
        if(xTarget < xLeft || xTarget > xRight){
            return false;
        }
        if (yTarget > yUp || yTarget < yDown){
            return false;
        }
        return true;
    }
}
