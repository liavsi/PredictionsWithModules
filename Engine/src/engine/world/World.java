package engine.world;

import engine.SimulationOutcome;
import engine.world.design.execution.entity.Entity;
import engine.world.property.ActualProperty;
import engine.world.property.EnvironmentProperty;
import engine.world.rule.Rule;
import engine.world.utils.Expression;
import schema.generated.*;

import java.util.HashMap;
import java.util.Map;

public class World implements HasProperties {
    Map<String,Entity> entities;// TODO: 03/08/2023 map?
    Map<String,Rule> rules;
    Map<String, EnvironmentProperty> environmentVars;

    @Override
    public String toString() {
        return "Entities: " + entities.toString() + "\nRules: " +rules.toString()
                +"\nEnvironment Variables: " +environmentVars.toString();
    }

    public Entity getEntityByName(String entityName){
        Entity resEntity = null;
        for(Entity entity: entities.values()){
            if(entity.getName() == entityName){
                resEntity = entity;
            }
        }
        if(resEntity == null){
            throw new RuntimeException("did not find This Entity in the world");
        }
        return resEntity;
    }

    @Override
    public ActualProperty getPropertyByName(String propertyName) {
        // TODO: 02/08/2023 change to actual property
        return null;
    }

    public EnvironmentProperty getEnvironmentVarByName(Expression envName) {
        EnvironmentProperty resultProperty = null;
        String propertyName = (String) envName.evaluate();
        if(propertyName == null) {
            throw new RuntimeException("Not a valid string");
        }
        for (EnvironmentProperty environmentVar : environmentVars.values()){
            if (propertyName == environmentVar.getName()) {
                resultProperty = environmentVar;
            }
        }
        if(resultProperty == null){
            throw new RuntimeException("did not find This Environment variable");
        }
        return resultProperty;
    }

    public SimulationOutcome runSimulation() {
        return new SimulationOutcome();
    } // TODO: 03/08/2023

    public void buildWorldFromPRDWorld(PRDWorld prdWorld) {
        buildEntitiesFromPRD(prdWorld.getPRDEntities());
        buildEnvironmentFromPRD(prdWorld.getPRDEvironment());
        buildRulesFromPRD(prdWorld.getPRDRules());
        buildTerminationFromPRD(prdWorld.getPRDTermination());
    }

    private void buildTerminationFromPRD(PRDTermination prdTermination) {
        // TODO: 06/08/2023  
    }

    private void buildRulesFromPRD(PRDRules prdRules) {
        rules = new HashMap<>();
        for (PRDRule prdRule: prdRules.getPRDRule()){
            String ruleName = prdRule.getName();
            Rule rule = new Rule(prdRule,this);
            rules.put(ruleName,rule);
        }
    }

    private void buildEnvironmentFromPRD(PRDEvironment prdEvironment) {
        environmentVars = new HashMap<>();
        for(PRDEnvProperty prdEnvProperty : prdEvironment.getPRDEnvProperty()){
            String name = prdEnvProperty.getPRDName();
            EnvironmentProperty environmentProperty = EnvironmentProperty.createPropertyFromPRD(prdEnvProperty);
            environmentVars.put(name,environmentProperty);
        }
    }

    private void buildEntitiesFromPRD(PRDEntities prdEntities) {
        entities = new HashMap<>();
        for (PRDEntity entity: prdEntities.getPRDEntity()){
            Entity currEntity = Entity.createInstanceFromPRD(entity);
            String entityName = entity.getName();
            entities.put(entityName,currEntity);
        }
    }

}