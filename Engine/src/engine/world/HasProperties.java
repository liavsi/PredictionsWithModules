package engine.world;

import engine.world.property.ActualProperty;

public interface HasProperties {

    public ActualProperty getPropertyByName(String propertyName);
}
