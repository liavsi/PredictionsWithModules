package engine.world.property;

import schema.generated.PRDProperty;
import schema.generated.PRDRange;
import schema.generated.PRDValue;

public abstract class Property {

    protected String name;
    protected Type type;
    protected Restriction restrictions;
    // TODO: 03/08/2023 constructor(?) 

    @Override
    public String toString() {
        return "\nName: " + name + "\nType: " + type.toString() + "\nRestrictions: " +  restrictions.toString();
    }

    public String getName() {
        return name;
    }

    public Type getType(){
        return type;
    }
}
