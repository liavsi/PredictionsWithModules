package DTOManager.impl;

import java.util.List;
import java.util.Map;

public class WorldDTO {

    private Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO;
    private List<RuleDTO> rulesDTO;
    private TerminationDTO terminationDTO;

    public WorldDTO(Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO, List<RuleDTO> rulesDTO, TerminationDTO terminationDTO) {
        this.nameToEntityDefinitionDTO = nameToEntityDefinitionDTO;
        this.rulesDTO = rulesDTO;
        this.terminationDTO = terminationDTO;
    }

    public Map<String, EntityDefinitionDTO> getNameToEntityDefinitionDTO() {
        return nameToEntityDefinitionDTO;
    }

    public List<RuleDTO> getRulesDTO() {
        return rulesDTO;
    }

    public WorldDTO(TerminationDTO terminationDTO) {
        this.terminationDTO = terminationDTO;
    }
}
