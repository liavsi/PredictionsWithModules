package engine.impl;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.action.api.Action;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;
import engine.world.design.rule.Rule;
import engine.world.design.world.api.World;
import engine.world.design.reader.api.Reader;
import engine.world.design.reader.impl.ReaderImpl;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

// TODO: 10/08/2023 After deleting old World Change to World

public class EngineImpl implements Engine {
    private static final String JAXB_XML_PACKAGE_NAME = "schema2.generated";
    private boolean isLoadedWorld = false;
    private final SimpleStringProperty fileName = new SimpleStringProperty();
    private final Reader myReader;
    private World myWorld; // TODO: 19/08/2023 static?
    private Integer countId = 1;
    private final Map<Integer, SimulationOutcome> pastSimulations;
    private Map<String, Object> propertyNameToValueAsString;
    private ExecutorService threadExecutor;

    public World getWorld() {
        return myWorld;
    }

    public EngineImpl() {
        myReader = new ReaderImpl();
        pastSimulations = new HashMap<>();
    }


    @Override
    public SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString) {
        SimulationOutcome simulation = myWorld.runSimulation(countId);
        pastSimulations.put(countId++, simulation);
//        RunSimulation runSimulation = new RunSimulation(simulation, myWorld, propertyNameToValueAsString);
//        runSimulation.run();
        threadExecutor.submit(new RunSimulation(simulation,myWorld,propertyNameToValueAsString));
        return simulation.createSimulationOutcomeDTO();
//        threadExecutor.submit(() -> {
//            for (PropertyDefinition envVarDefinition : myWorld.getEnvVariablesManager().getEnvVariables().values()) {
//                String envName = envVarDefinition.getName();
//                if (propertyNameToValueAsString.containsKey(envName)) {
//                    Object value = envVarDefinition.getType().convert(propertyNameToValueAsString.get(envName));
//                    simulation.getActiveEnvironment().addPropertyInstance(new PropertyInstanceImpl(envVarDefinition, value));
//                } else {
//                    simulation.getActiveEnvironment().addPropertyInstance(new PropertyInstanceImpl(envVarDefinition, envVarDefinition.generateValue()));
//                }
//            }
//            // showFinalEnvProperties();
//            // creating the instance manager
//            for (EntityDefinition entityDefinition : myWorld.getNameToEntityDefinition().values()) {
//                Double population = (Double) propertyNameToValueAsString.get(entityDefinition.getName() + "entity");
//                entityDefinition.setPopulation(population.intValue());
//                for (int i = 0; i < entityDefinition.getPopulation(); i++) {
//                    simulation.getEntityInstanceManager().create(entityDefinition, myWorld.getGrid());
//                }
//            }
//            simulation.getTermination().startTerminationClock();
//            Map<Integer, SimulationOutcome> informationSimulation = new HashMap<>();
//            // take a picture
//            int ticks = 0;
//            // TODO: 15/09/2023 setTicks
////        simulationOutcome.getTermination()
//            while (ticks < 2) {
//                //!simulationOutcome.getTermination().isTerminated(ticks)) {
//                simulation.
//                        getEntityInstanceManager().
//                        getInstances().
//                        values().
//                        forEach((entityInstance) -> myWorld.getGrid().moveEntity(entityInstance));
//
//                ArrayList<Action> activeActions = new ArrayList<>();
//                for (Rule rule : myWorld.getRules()) {
//                    if (rule.getActivation().isActive(ticks)) {
//                        activeActions.addAll(rule.getActionToPreform());
//                    }
//                }
//                for (EntityInstance entityInstance : simulation.getEntityInstanceManager().getInstances().values()) {
//                    for (Action action : activeActions) {
//                        if (action.getMainEntity().getName().equals(entityInstance.getEntityDefinition().getName())) { //if the action activate on the entity
//                            if (action.getInteractiveEntity() != null) {
//                                Context context = new ContextImpl(null, null, simulation.getEntityInstanceManager(), simulation.getActiveEnvironment(), myWorld.getGrid());
//                                for (EntityInstance secondaryEntity : action.getSecondaryInstances(context)) {
//                                    context = new ContextImpl(entityInstance, secondaryEntity, simulation.getEntityInstanceManager(), simulation.getActiveEnvironment(), myWorld.getGrid());
//                                    action.invoke(context);
//                                }
//                            } else {
//                                Context context = new ContextImpl(entityInstance, null, simulation.getEntityInstanceManager(), simulation.getActiveEnvironment(), myWorld.getGrid());
//                                action.invoke(context);
//                            }
//                        }
//                    }
//                    for (PropertyInstance propertyInstance : entityInstance.getProperties().values()) {
//                        if (propertyInstance.getValue().equals(propertyInstance.getOldValue())) {
//                            propertyInstance.setTicksSameValue(propertyInstance.getTicksSameValue() + 1);
//                        } else {
//                            propertyInstance.setOldValue(propertyInstance.getValue());
//                            propertyInstance.setTicksSameValue(0);
//                        }
//                    }
//                }
//                simulation.getEntityInstanceManager().killEntities();
//                ticks++;
//
//
//            }
//        });
//        return simulation.createSimulationOutcomeDTO();
//    }
//

}
        //SimulationOutcome currSimulation = myWorld.runSimulation(propertyNameToValueAsString,countId);
        //engine.setCountId(engine.getCountId() + 1);
        //engine.getPastSimulations().put(engine.getCountId(), currSimulation);
//        for(){
//
//        }
//        //Date currentDate = new Date();
//        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
//        SimulationOutcome currSimulation = myWorld.runSimulation(propertyNameToValueAsString,countId);
//        //String formattedDate = dateFormat.format(currentDate);
//        //SimulationOutcome currSimulation = new SimulationOutcome(formattedDate,countId, myWorld.getTermination(),currSimulationInstances);
//        pastSimulations.put(countId++, currSimulation);
//        return currSimulation.createSimulationOutcomeDTO();

    @Override
    public WorldDTO getWorldDTO() {
         return myWorld.createWorldDTO();
    }

    @Override
    public SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber) {
        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
    }

    @Override
    public void readWorldFromXml() {
        myReader.readWorldFromXml(fileName.get(), JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
        isLoadedWorld = true;
        threadExecutor = Executors.newFixedThreadPool(myWorld.getNumOfThreads());
        //threadExecutor = Executors.newFixedThreadPool(1);
    }
    @Override
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        myReader.readWorldFromXml(XML_PATH, JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
        isLoadedWorld = true;
    }

    public boolean getIsLoadedWorld() {
        return isLoadedWorld;
    }
//    @Override
//    public Map<Integer,SimulationOutcomeDTO> getPastSimulationDTO(int wantedSimulationNumber) {
//        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
//    }

    @Override
    public Map<Integer, SimulationOutcomeDTO> getPastSimulationMapDTO() {
        Map<Integer,SimulationOutcomeDTO> res = new HashMap<>();
        pastSimulations.forEach((id,pastSimulationOutCome) -> res.put(id, pastSimulationOutCome.createSimulationOutcomeDTO()));
        return res;
    }
    @Override
    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }
    @Override
    public Integer getCountId() {
        return countId;
    }
    @Override
    public Map<Integer, SimulationOutcome> getPastSimulations() {
        return pastSimulations;
    }
    @Override
    public World getMyWorld() {
        return myWorld;
    }
    @Override
    public Map<String, Object> getPropertyNameToValueAsString() {
        return propertyNameToValueAsString;
    }
    @Override
    public void setPropertyNameToValueAsString(Map<String, Object> propertyNameToValueAsString) {
        this.propertyNameToValueAsString = propertyNameToValueAsString;
    }
    @Override
    public void setCountId(Integer countId) {
        this.countId = countId;
    }
    @Override
    public void stopSimulationByID(int id){
        SimulationOutcome simulationOutcome = pastSimulations.get(id);
        simulationOutcome.setStop(true);
        simulationOutcome.setResume(false);
    }
    @Override
    public void resumeSimulationByID(int id){
        SimulationOutcome simulationOutcome = pastSimulations.get(id);
        simulationOutcome.setPause(false);
    }
    @Override
    public void pauseSimulationByID(int id){
        SimulationOutcome simulationOutcome = pastSimulations.get(id);
        simulationOutcome.setPause(true);
    }
}
