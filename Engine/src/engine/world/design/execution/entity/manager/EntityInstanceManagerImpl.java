package engine.world.design.execution.entity.manager;

import com.sun.javaws.exceptions.InvalidArgumentException;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.entity.impl.EntityInstanceImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private int count;
    private Map<Integer, EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        count = 0;
        instances = new HashMap<>();
    }

    @Override
    public EntityInstance create(EntityDefinition entityDefinition) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
        instances.put(count, newEntityInstance);

        for (PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }

        return newEntityInstance;
    }

    @Override
    public Map<Integer, EntityInstance> getInstances() {
        return instances;
    }

    @Override
    public void killEntity(int id) {
        if(instances.containsKey(id)) {
            instances.remove(id);
        }
        else {
            throw new IllegalArgumentException("this instance is already dead");
        }
    }
}