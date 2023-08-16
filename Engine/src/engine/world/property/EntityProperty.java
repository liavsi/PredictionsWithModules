package engine.world.property;

import schema.generated.PRDProperty;
import schema.generated.PRDRange;
import schema.generated.PRDValue;

public class EntityProperty extends Property{
    private RandomInitializer randomInitializer;

    public static EntityProperty createPropertyFromPRD(PRDProperty prdProperty) {
        EntityProperty newProperty = new EntityProperty();
        newProperty.name = prdProperty.getPRDName();
        newProperty.type = Type.valueOf(prdProperty.getType().toUpperCase());// TODO: 03/08/2023 exception(?)
        PRDRange prdRange = prdProperty.getPRDRange();
        newProperty.restrictions = new Restriction(prdRange.getFrom(),prdRange.getTo());
        PRDValue prdValue = prdProperty.getPRDValue();
        newProperty.randomInitializer = new RandomInitializer(prdValue.isRandomInitialize(),prdValue.getInit());
        return newProperty;
    }

    public RandomInitializer getRandomInitializer() {
        return randomInitializer;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Is Random Initialized: " + randomInitializer.toString();
    }

}