package engine.world.design.world.impl;

import DTOManager.impl.*;
import engine.SimulationOutcome;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.entity.manager.EntityInstanceManagerImpl;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.property.PropertyInstanceImpl;
import engine.world.design.termination.api.Termination;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;

import java.text.SimpleDateFormat;
import java.util.*;

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
        List<PropertyDefinitionDTO> envPropertiesDefinitionDTO = new ArrayList<>();
        for (PropertyDefinition propertyDefinition : envVariablesManager.getEnvVariables()){
            envPropertiesDefinitionDTO.add(propertyDefinition.createPropertyDefinitionDTO());
        }
        return new WorldDTO(entityDefinitionDTOMap,rulesDTO,terminationDTO,envPropertiesDefinitionDTO);
    }

    @Override
    public EnvVariablesManager getEnvVariables() {
        return envVariablesManager;
    }

    @Override
    public SimulationOutcome runSimulation(Map<String, Object> propertyNameToValueAsString, Integer countId) {
        // creating the Active Environment - if the user gave the property its value we will use it otherwise generate value
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        for (PropertyDefinition envVarDefinition: envVariablesManager.getEnvVariables()) {
            String envName = envVarDefinition.getName();
            if(propertyNameToValueAsString.containsKey(envName)) {
                Object value = envVarDefinition.getType().convert(propertyNameToValueAsString.get(envName));
                activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(envVarDefinition,value));
            }
            else {
                activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(envVarDefinition, envVarDefinition.generateValue()));
            }
        }
        //showFinalEnvProperties();
        // creating the instance manager
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        for (EntityDefinition entityDefinition: nameToEntityDefinition.values()) {
            for (int i = 0 ;i < entityDefinition.getPopulation(); i++) {
                entityInstanceManager.create(entityDefinition);
            }
        }
        // take a picture
        int ticks = 0; // TODO: 19/08/2023 if ticks = 1 
        termination.startTerminationClock();
        while (!termination.isTerminated(ticks)) {
            for (EntityInstance entityInstance: entityInstanceManager.getInstances().values()) {
                Context context = new ContextImpl(entityInstance, entityInstanceManager, activeEnvironment);
                for (Rule rule: rules) {
                    if (rule.getActivation().isActive(ticks)) {
                        rule.getActionToPreform().forEach(action -> action.invoke(context));
                    }
                }
            }
            entityInstanceManager.killEntities();
            ticks++;
        }
        // take second picture
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        String formattedDate = dateFormat.format(currentDate);
        return new SimulationOutcome(formattedDate,countId,termination);
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
