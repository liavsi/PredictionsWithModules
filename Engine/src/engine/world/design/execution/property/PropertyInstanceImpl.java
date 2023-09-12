package engine.world.design.execution.property;

import DTOManager.impl.PropertyInstanceDTO;
import engine.world.design.definition.property.api.PropertyDefinition;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDefinition propertyDefinition;
    private Object value;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
    }

    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateValue(Object val) {
        this.value = propertyDefinition.getType().convert(val);
    }

    @Override
    public PropertyInstanceDTO createDTO() {
        return new PropertyInstanceDTO(propertyDefinition.createPropertyDefinitionDTO(), value.toString());
    }
    @Override
    public void setValue(Object value) {
        this.value = value;
    }
}