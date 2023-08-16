package engine.world.design.definition.entity.impl;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {

    private final String name;
    private final int population;
    private final List<PropertyDefinition> properties;

    public EntityDefinitionImpl(String name, int population) {
        this.name = name;
        this.population = population;
        properties = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getProps() {
        return properties;
    }

}
