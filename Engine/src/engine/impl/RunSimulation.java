package engine.impl;

import engine.SimulationOutcome;
import engine.world.design.action.api.Action;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;
import engine.world.design.rule.Rule;
import engine.world.design.termination.api.Termination;
import engine.world.design.world.api.World;

import java.time.Duration;
import java.time.Instant;
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
        try{
        System.out.println(simulationOutcome.getId() + ":start running");
        for (PropertyDefinition envVarDefinition: world.getEnvVariablesManager().getEnvVariables().values()) {
            System.out.println(simulationOutcome.getId() + "in environment setting");
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
            Object value = propertyNameToValueAsString.get(entityDefinition.getName() + "entity");
            double population;
            if (value instanceof Integer) {
                population = ((Integer) value).doubleValue();
            } else if (value instanceof Double) {
                population = (Double) value;
            } else {
                // Handle other cases or provide a default value
                population = 0.0; // Default value, change as needed
            }
            //Double population = (Double) propertyNameToValueAsString.get(entityDefinition.getName()+"entity");
            entityDefinition.setPopulation((int) population);
            for (int i = 0 ;i < entityDefinition.getPopulation(); i++) {
                simulationOutcome.getEntityInstanceManager().create(entityDefinition);
            }
        }
        Termination termination = simulationOutcome.getTermination();
        termination.startTerminationClock();
        Map<Integer, SimulationOutcome> informationSimulation = new HashMap<>();
        // take a picture
        termination.setCurrTick(0);



            // TODO: 15/09/2023 setTicks
//        simulationOutcome.getTermination()
            while (!simulationOutcome.getTermination().isTerminated(simulationOutcome.isStop())) {
                moveEntities();
                ArrayList<Action> activeActions = new ArrayList<>();
                for (Rule rule : world.getRules()) {
                    if (rule.getActivation().isActive(termination.getCurrTick())) {
                        activeActions.addAll(rule.getActionToPreform());
                    }
                }
                for (EntityInstance entityInstance : simulationOutcome.getEntityInstanceManager().getInstances().values()) {
                    for (Action action : activeActions) {
                        if (action.getMainEntity().getName().equals(entityInstance.getEntityDefinition().getName())) { //if the action activate on the entity
                            if (action.getInteractiveEntity() != null) {
                                Context context = new ContextImpl(null, null, simulationOutcome.getEntityInstanceManager(), simulationOutcome.getActiveEnvironment(), simulationOutcome.getGrid());
                                for (EntityInstance secondaryEntity : action.getSecondaryInstances(context)) {
                                    context = new ContextImpl(entityInstance, secondaryEntity, simulationOutcome.getEntityInstanceManager(), simulationOutcome.getActiveEnvironment(), simulationOutcome.getGrid());
                                    action.invoke(context);
                                }
                            } else {
                                Context context = new ContextImpl(entityInstance, null, simulationOutcome.getEntityInstanceManager(), simulationOutcome.getActiveEnvironment(), simulationOutcome.getGrid());
                                action.invoke(context);
                            }
                        }
                    }
                    for (PropertyInstance propertyInstance : entityInstance.getProperties().values()) {
                        if (propertyInstance.getValue().equals(propertyInstance.getOldValue())) {
                            propertyInstance.setTicksSameValue(propertyInstance.getTicksSameValue() + 1);
                        } else {
                            propertyInstance.setOldValue(propertyInstance.getValue());
                            propertyInstance.setTicksSameValue(0);
                        }
                    }
                }
                if (termination.getCurrTick() % 100 == 0) {
                    simulationOutcome.addSimulationForTickDTO(termination.getCurrTick(), simulationOutcome.getEntityInstanceManager().createDTO());
                }
                simulationOutcome.getEntityInstanceManager().killEntities();
                simulationOutcome.getEntityInstanceManager().createEntities();
                termination.setCurrTick(termination.getCurrTick() + 1);
                pauseAndResume();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
//      }
//      cSimulationOutcome currSimulation = engine.getMyWorld().runSimulation(engine.getPropertyNameToValueAsString(),engine.getCountId());
//        engine.setCountId(engine.getCountId() + 1);
//        engine.getPastSimulations().put(engine.getCountId(), currSimulation);
    }
    private void moveEntities(){
        simulationOutcome.
                getEntityInstanceManager().
                getInstances().
                values().
                forEach((entityInstance) -> simulationOutcome.getGrid().moveEntity(entityInstance));
    }
    private void pauseAndResume(){
        synchronized (this) {
            Instant startWait = Instant.now();
            while (simulationOutcome.isPause()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Instant nowTime = Instant.now();
            Duration waitTime = Duration.between(startWait,nowTime);
            simulationOutcome.getTermination().reduceWaitTime(waitTime);
        }
    }

    public void pauseThread() {
        synchronized (this){
            simulationOutcome.getTermination().getByUser().setPaused(true);
            simulationOutcome.setPause(true);
            simulationOutcome.setResume(false);
        }
    }

    public void resumeThread() {
        synchronized (this) {
            simulationOutcome.getTermination().getByUser().setPaused(false);
            simulationOutcome.setResume(true);
            simulationOutcome.setStop(false);
            simulationOutcome.setPause(false);
            this.notify();
        }
    }

    public void StopThread() {
        synchronized (this) {
            simulationOutcome.setStop(true);
            simulationOutcome.setResume(false);
            simulationOutcome.setPause(false);
        }
    }
}
