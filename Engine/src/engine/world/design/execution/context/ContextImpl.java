package engine.world.design.execution.context;

import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.property.PropertyInstance;

public class ContextImpl implements Context {

    private final EntityInstance primaryEntityInstance;

    private final EntityInstance secondaryEntity;
    private final EntityInstanceManager entityInstanceManager;
    private final ActiveEnvironment activeEnvironment;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntity, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondaryEntity = secondaryEntity;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
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
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }
}
