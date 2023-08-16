package engine.world.property;

import schema.generated.PRDEnvProperty;
import schema.generated.PRDProperty;
import schema.generated.PRDRange;
import schema.generated.PRDValue;

public class EnvironmentProperty extends Property{

    public String toString() {
        return super.toString();
    }

    public static EnvironmentProperty createPropertyFromPRD(PRDEnvProperty prdProperty) {
        EnvironmentProperty newProperty = new EnvironmentProperty();
        newProperty.name = prdProperty.getPRDName();
        newProperty.type = Type.valueOf(prdProperty.getType().toUpperCase());// TODO: 03/08/2023 exception(?)
        PRDRange prdRange = prdProperty.getPRDRange();
        if(prdRange == null){
            newProperty.restrictions = new Restriction(prdRange.getFrom(), prdRange.getTo());
        }
        else{
            newProperty.restrictions = null;
        }
        return newProperty;
    }
}
