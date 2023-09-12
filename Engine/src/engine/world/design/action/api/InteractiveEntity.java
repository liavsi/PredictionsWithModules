package engine.world.design.action.api;

import engine.world.design.definition.entity.api.EntityDefinition;

public class InteractiveEntity {

    private final EntityDefinition secondaryEntity;
    private final int count;
    private final Action conditionAction;

    public InteractiveEntity(EntityDefinition secondaryEntity, int count, Action conditionAction) {
        this.secondaryEntity = secondaryEntity;
        this.count = count;
        this.conditionAction = conditionAction;
    }
    public EntityDefinition getSecondaryEntity() {
        return secondaryEntity;
    }

    public int getCount() {
        return count;
    }

    public Action getConditionAction() {
        return conditionAction;
    }
}
