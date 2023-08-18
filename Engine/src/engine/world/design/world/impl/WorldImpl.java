package engine.world.design.world.impl;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.TerminationDTO;
import DTOManager.impl.WorldDTO;
import com.sun.javaws.exceptions.InvalidArgumentException;
import engine.SimulationOutcome;
import engine.world.design.termination.api.Termination;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldImpl implements World {

    private Map<String, EntityDefinition> nameToEntityDefinition;
    private EnvVariablesManager envVariablesManager;
    private List<Rule> rules;
    private Termination termination;

// TODO: 10/08/2023 List<Termination> terminationConditions;
    public WorldImpl() {
    }
    public Map<String, EntityDefinition> getNameToEntityDefinition() {
        return nameToEntityDefinition;
    }
    public Termination getTermination() {
        return termination;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public EntityDefinition getEntityDefinitionByName(String name) {
        if(!nameToEntityDefinition.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not a name of entity");
        }
        return nameToEntityDefinition.get(name);
    }
    @Override
    public WorldDTO createWorldDTO(){
        Map<String, EntityDefinitionDTO> entityDefinitionDTOMap= new HashMap<>();
        for (EntityDefinition entityDefinition: nameToEntityDefinition.values()){
            entityDefinitionDTOMap.put(entityDefinition.getName(),entityDefinition.createEntityDefinitionDTO());
        }
        List<RuleDTO> rulesDTO = new ArrayList<>();
        for (Rule rule:rules){
            rulesDTO.add(rule.createRuleDTO());
        }
        TerminationDTO terminationDTO = termination.createTerminationDTO();
        return new WorldDTO(entityDefinitionDTOMap,rulesDTO,terminationDTO);
    }

    @Override
    public EnvVariablesManager getEnvVariables() {
        return null;
    }

    @Override
    public SimulationOutcome runSimulation() {

        return null;
    }

    @Override
    public void setEntities(Map<String, EntityDefinition> entities) {
        nameToEntityDefinition = entities;
    }

    @Override
    public void setEnvVariablesManager(EnvVariablesManager envVariablesManager) {
        this.envVariablesManager = envVariablesManager;
    }

    @Override
    public void setRules(List<Rule> ruleList) {
        this.rules = ruleList;
    }


    @Override
    public void setTermination(Termination termination) {
        this.termination = termination;
    }
}
