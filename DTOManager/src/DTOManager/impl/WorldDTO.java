package DTOManager.impl;

import java.util.List;
import java.util.Map;

public class WorldDTO {
    private Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO;
    private List<RuleDTO> rulesDTO;
    private List<PropertyDefinitionDTO> envPropertiesDefinitionDTO;
    private final TerminationDTO terminationDTO;

    public WorldDTO(Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO, List<RuleDTO> rulesDTO,
                    TerminationDTO terminationDTO ,List<PropertyDefinitionDTO> envPropertiesDefinitionDTO) {
        this.nameToEntityDefinitionDTO = nameToEntityDefinitionDTO;
        this.rulesDTO = rulesDTO;
        this.terminationDTO = terminationDTO;
        this.envPropertiesDefinitionDTO = envPropertiesDefinitionDTO;
    }

    public Map<String, EntityDefinitionDTO> getNameToEntityDefinitionDTO() {
        return nameToEntityDefinitionDTO;
    }
    public List<RuleDTO> getRulesDTO() {
        return rulesDTO;
    }
    public List<PropertyDefinitionDTO> getEnvPropertiesDefinitionDTO() {
        return envPropertiesDefinitionDTO;
    }
    public WorldDTO(TerminationDTO terminationDTO) {
        this.terminationDTO = terminationDTO;
    }
    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }

    public EntityDefinitionDTO getEntityDefinitionDTOByName(String entityName){
        for (EntityDefinitionDTO entityDefinitionDTO: nameToEntityDefinitionDTO.values()){
            if (entityName.equals(entityDefinitionDTO.getName())){
                return entityDefinitionDTO;
            }
        }
        return null;
    }
}
