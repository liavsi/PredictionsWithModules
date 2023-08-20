package DTOManager.impl;

public class PropertyInstanceDTO {
    private final PropertyDefinitionDTO propertyDefinitionDTO;
    private final String value;

    public PropertyDefinitionDTO getPropertyDefinitionDTO() {
        return propertyDefinitionDTO;
    }

    public String getName() {
        return getPropertyDefinitionDTO().getName();
    }
    public String getValue() {
        return value;
    }

    public PropertyInstanceDTO(PropertyDefinitionDTO propertyDefinitionDTO, String value) {
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.value = value;
    }
}
