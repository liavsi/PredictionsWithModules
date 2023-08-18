package DTOManager.impl;

public class PropertyDefinitionDTO {

    private final String name;
    private final  String propertyType;
    private final Boolean isRandomInitializer;
     private final Integer from;
     private final Integer to;

    public PropertyDefinitionDTO(String name, String propertyType, Boolean isRandomInitializer, Integer from,Integer to) {
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

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }
}
