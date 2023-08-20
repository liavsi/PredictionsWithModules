package DTOManager.impl;

public class PropertyInstanceDTO {
    private final PropertyDefinitionDTO propertyDefinitionDTO;
    private final String value;

    public PropertyInstanceDTO(PropertyDefinitionDTO propertyDefinitionDTO, String value) {
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.value = value;
    }
}
