package engine.world.design.world.api;

import engine.SimulationOutcome;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;
import engine.world.design.termination.api.Termination;

import java.util.List;
import java.util.Map;

public interface World {


    EntityDefinition getEntityDefinitionByName(String name);
    EnvVariablesManager getEnvVariables();
    SimulationOutcome runSimulation();

    void setEntities(Map<String, EntityDefinition> entities);

    void setEnvVariablesManager(EnvVariablesManager envVariablesManager);

    void setRules(List<Rule> ruleList);
    public void setTermination(Termination termination);

    }
