package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class ReplaceDTO extends ActionDTO{
    private final String mode;
    public ReplaceDTO(String actionType, EntityDefinitionDTO mainEntity, String mode) {
        super(actionType, mainEntity);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
