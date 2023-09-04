package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class ConditionActionDTO extends ActionDTO{

    ConditionDTO conditionDTO;
    public ConditionActionDTO(String actionType, EntityDefinitionDTO mainEntity,ConditionDTO conditionDTO) {
        super(actionType, mainEntity);
    }
}
