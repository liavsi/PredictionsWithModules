package component.body.resultspage;

import DTOManager.impl.EntityDefinitionDTO;
import engine.world.design.definition.entity.api.EntityDefinition;

public class ShowEntity {

    private String name;
    private Integer population;

    public ShowEntity(EntityDefinitionDTO entityDefinitionDTO) {
        name = entityDefinitionDTO.getName();
        population = entityDefinitionDTO.getPopulation();
    }
}
