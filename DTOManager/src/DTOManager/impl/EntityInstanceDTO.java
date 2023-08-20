package DTOManager.impl;

import java.util.Map;

public class EntityInstanceDTO {
    private final EntityDefinitionDTO entityDefinitionDTO;
    private final Integer id;
    private final Map<String, PropertyInstanceDTO> properties;

    public EntityInstanceDTO(EntityDefinitionDTO entityDefinitionDTO, Integer id, Map<String, PropertyInstanceDTO> properties) {
        this.entityDefinitionDTO = entityDefinitionDTO;
        this.id = id;
        this.properties = properties;
    }
}
