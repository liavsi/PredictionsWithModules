package engine.world.design.definition.property.api;

import DTOManager.impl.PropertyDefinitionDTO;

public interface PropertyDefinition {
    String getName();
    PropertyType getType();
    PropertyDefinitionDTO createPropertyDefinitionTDO();
    Object generateValue();
}
