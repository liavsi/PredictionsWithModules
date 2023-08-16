package engine.world.design.execution.entity;

import engine.world.property.EntityProperty;
import schema.generated.PRDEntity;
import schema.generated.PRDProperty;

import java.util.HashMap;
import java.util.Map;

public class Entity {

    private String name;
    private int quantityInPopulation;
    private Map<String, EntityProperty> properties;

    public static Entity createInstanceFromPRD(PRDEntity prdEntity) {
        Entity newEntity = new Entity();
        newEntity.properties = new HashMap<>();
        newEntity.name = prdEntity.getName();
        newEntity.quantityInPopulation = prdEntity.getPRDPopulation();
        for (PRDProperty prdProperty:prdEntity.getPRDProperties().getPRDProperty()){
            EntityProperty newProperty = EntityProperty.createPropertyFromPRD(prdProperty);
            String newPropertyName = prdProperty.getPRDName();
            newEntity.properties.put(newPropertyName,newProperty);
        }
        return newEntity;
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\nQuantity in population: " + quantityInPopulation + "\n Properties: " + properties.toString();
    }

    public EntityProperty getPropertyByName(String propertyName) {
        EntityProperty resultProperty = null;
        for (EntityProperty environmentVar : properties.values()) {
            if (environmentVar.getName() == propertyName) {
                resultProperty = environmentVar;
            }
        }
        if (resultProperty == null) {
            throw new RuntimeException("did not find This Environment variable");
        }
        return resultProperty;
    }
    public String getName(){
        return name;
    }
}
