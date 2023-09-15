package engine.impl;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.world.api.World;
import engine.world.design.reader.api.Reader;
import engine.world.design.reader.impl.ReaderImpl;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
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
    private final Map<Integer,SimulationOutcome> pastSimulations;
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
        threadExecutor.submit(new RunSimulation(simulation,myWorld,propertyNameToValueAsString));
        return simulation.createSimulationOutcomeDTO();
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
    }



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
}
