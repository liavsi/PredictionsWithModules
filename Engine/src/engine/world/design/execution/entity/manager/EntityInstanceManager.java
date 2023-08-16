package engine.world.design.execution.entity.manager;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {

    EntityInstance create(EntityDefinition entityDefinition);
    Map<Integer, EntityInstance> getInstances();

    void killEntity(int id);


}
