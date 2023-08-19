package engine.world.design.definition.property.api;

import DTOManager.impl.PropertyDefinitionDTO;
import engine.world.design.definition.value.generator.api.ValueGenerator;
import engine.world.design.definition.value.generator.fixed.FixedValueGenerator;
import engine.world.design.definition.value.generator.random.impl.numeric.AbstractNumericRandomGenerator;

public abstract class AbstractPropertyDefinition<T>  implements PropertyDefinition {

    private final String name;
    private final PropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;

    public AbstractPropertyDefinition(String name, PropertyType propertyType,ValueGenerator<T> valueGenerator) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
    }
    @Override
    public PropertyDefinitionDTO createPropertyDefinitionTDO(){
        Boolean isRandomInitializer;
        Float from = null;
        Float to = null;

        if(valueGenerator instanceof FixedValueGenerator){
            isRandomInitializer = Boolean.FALSE;
        }
        else {
            isRandomInitializer = Boolean.TRUE;
            if (valueGenerator instanceof AbstractNumericRandomGenerator){
                from = (float)((AbstractNumericRandomGenerator<T>) valueGenerator).getFrom();
                to = (float) ((AbstractNumericRandomGenerator<T>) valueGenerator).getTo();
            }
        }
        return new PropertyDefinitionDTO(name,propertyType.name(),isRandomInitializer,from,to);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }

}
