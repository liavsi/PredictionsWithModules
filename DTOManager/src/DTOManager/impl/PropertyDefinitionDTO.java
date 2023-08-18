package DTOManager.impl;

public class PropertyDefinitionDTO {

    private final String name;
    private final  String propertyType;
    private Object value;

    public PropertyDefinitionDTO(String name, String propertyType, Object value) {
        this.name = name;
        this.propertyType = propertyType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public Object getValue() {
        return value;
    }
}
