package engine.impl;

import engine.SimulationOutcome;
import engine.world.design.action.api.Action;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;
import engine.world.design.rule.Rule;
import engine.world.design.world.api.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunSimulation implements Runnable{

    private final SimulationOutcome simulationOutcome;
    private final World world;
    private final Map<String, Object> propertyNameToValueAsString;

    public RunSimulation(SimulationOutcome simulationOutcome, World world, Map<String, Object> propertyNameToValueAsString) {
        this.simulationOutcome = simulationOutcome;
        this.world = world;
        this.propertyNameToValueAsString = propertyNameToValueAsString;
    }

    @Override
    public void run() {
        for (PropertyDefinition envVarDefinition: world.getEnvVariablesManager().getEnvVariables().values()) {
            String envName = envVarDefinition.getName();
            if(propertyNameToValueAsString.containsKey(envName)) {
                Object value = envVarDefinition.getType().convert(propertyNameToValueAsString.get(envName));
                simulationOutcome.getActiveEnvironment().addPropertyInstance(new PropertyInstanceImpl(envVarDefinition,value));
            }
            else {
                simulationOutcome.getActiveEnvironment().addPropertyInstance(new PropertyInstanceImpl(envVarDefinition, envVarDefinition.generateValue()));
            }
        }
        // showFinalEnvProperties();
        // creating the instance manager
        for (EntityDefinition entityDefinition: world.getNameToEntityDefinition().values()) {
            for (int i = 0 ;i < entityDefinition.getPopulation(); i++) {
                simulationOutcome.getEntityInstanceManager().create(entityDefinition, world.getGrid());
            }
        }
        world.getTermination().startTerminationClock();
        Map<Integer, SimulationOutcome> informationSimulation = new HashMap();
        // take a picture
        int ticks = 0;
        //informationSimulation.put(0, simulationOutcome);
        while (!world.getTermination().isTerminated(ticks)) {
            moveEntities();
            ArrayList<Action> activeActions = new ArrayList<>();
            for (Rule rule: world.getRules()) {
                if (rule.getActivation().isActive(ticks)) {
                    activeActions.addAll(rule.getActionToPreform());
                }
            }
            for (EntityInstance entityInstance: simulationOutcome.getEntityInstanceManager().getInstances().values()) {
                for (Action action: activeActions){
                    if (action.getMainEntity().equals(entityInstance.getEntityDefinition())){
                        if (action.getInteractiveEntity() != null) {
                            for (EntityInstance secondaryEntity : action.getSecondaryInstances()) {
                                Context context = new ContextImpl(entityInstance, secondaryEntity, simulationOutcome.getEntityInstanceManager(), simulationOutcome.getActiveEnvironment());
                                action.invoke(context);
                            }
                        }else{
                            Context context = new ContextImpl(entityInstance, null, simulationOutcome.getEntityInstanceManager(), simulationOutcome.getActiveEnvironment());
                            action.invoke(context);
                        }
                    }
                }
            }
            simulationOutcome.getEntityInstanceManager().killEntities();
            ticks++;
        }
//        SimulationOutcome currSimulation = engine.getMyWorld().runSimulation(engine.getPropertyNameToValueAsString(),engine.getCountId());
//        engine.setCountId(engine.getCountId() + 1);
//        engine.getPastSimulations().put(engine.getCountId(), currSimulation);
    }
    private void moveEntities(){
        simulationOutcome.
                getEntityInstanceManager().
                getInstances().
                values().
                forEach((entityInstance) -> world.getGrid().moveEntity(entityInstance));
    }
}
