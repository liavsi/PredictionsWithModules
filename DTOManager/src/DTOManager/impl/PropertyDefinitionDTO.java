package DTOManager.impl;

public class PropertyDefinitionDTO {

    private final String name;
    private final  String propertyType;
    private final Boolean isRandomInitializer;
     private final Float from;
     private final Float to;

    public PropertyDefinitionDTO(String name, String propertyType, Boolean isRandomInitializer, Float from,Float to) {
        this.name = name;
        this.propertyType = propertyType;
        this.isRandomInitializer = isRandomInitializer;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public Boolean getRandomInitializer() {
        return isRandomInitializer;
    }

    public Float getFrom() {
        return from;
    }

    public Float getTo() {
        return to;
    }
}
