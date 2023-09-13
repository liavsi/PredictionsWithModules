package engine.world.design.execution.context;

import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.api.Grid;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private final EntityInstance secondaryEntity;
    private final EntityInstanceManager entityInstanceManager;
    private final ActiveEnvironment activeEnvironment;
    private final Grid grid;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntity, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, Grid grid) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondaryEntity = secondaryEntity;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
    }
    @Override
    public void setPrimaryEntityInstance(EntityInstance primaryEntityInstance) {
        this.primaryEntityInstance = primaryEntityInstance;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public EntityInstance getSecondaryEntity() {
        return secondaryEntity;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }
    @Override
    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }
}
