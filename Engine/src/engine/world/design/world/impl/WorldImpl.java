package engine.world.design.world.impl;

import com.sun.javaws.exceptions.InvalidArgumentException;
import engine.SimulationOutcome;
import engine.world.design.termination.api.Termination;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;

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

    @Override
    public EntityDefinition getEntityDefinitionByName(String name) {
        if(!nameToEntityDefinition.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not a name of entity");
        }
        return nameToEntityDefinition.get(name);
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
