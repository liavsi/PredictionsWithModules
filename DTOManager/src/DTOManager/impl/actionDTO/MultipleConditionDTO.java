package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

import java.util.List;

public class MultipleConditionDTO extends ActionDTO implements ConditionDTO{
    private final List<ConditionDTO> conditions;
    private final String logical;
    public MultipleConditionDTO(String actionType, EntityDefinitionDTO mainEntity, List<ConditionDTO> conditions, String logical) {
        super(actionType, mainEntity);
        this.conditions = conditions;
        this.logical = logical;
    }

    public List<ConditionDTO> getConditions() {
        return conditions;
    }

    public String getLogical() {
        return logical;
    }
}

