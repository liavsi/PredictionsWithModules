package engine.world.design.execution.entity.api;

import DTOManager.impl.EntityInstanceDTO;
import engine.world.design.execution.property.PropertyInstance;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);

    EntityInstanceDTO createDTO();
}
