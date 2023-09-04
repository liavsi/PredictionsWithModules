package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

import java.util.List;

public class MultipleConditionDTO implements ConditionDTO{
    private final List<ConditionDTO> conditions;
    private final String logical;
    public MultipleConditionDTO(List<ConditionDTO> conditions, String logical) {
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

