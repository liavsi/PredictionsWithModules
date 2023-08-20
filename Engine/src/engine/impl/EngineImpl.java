package engine.impl;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.world.api.World;
import engine.world.design.reader.api.Reader;
import engine.world.design.reader.impl.ReaderImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO: 10/08/2023 After deleting old World Change to World

public class EngineImpl implements Engine {

    private Reader myReader;
    private World myWorld; // TODO: 19/08/2023 static?
    private Integer countId = 1;
    private Map<Integer, SimulationOutcome> pastSimulations;

    public World getWorld() {
        return myWorld;
    }
    public EngineImpl() {
        myReader = new ReaderImpl();
        pastSimulations = new HashMap<>();
    }
    @Override
    public SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString) {
        Map<Integer, EntityInstanceManagerDTO> currSimulationInstances = myWorld.runSimulation(propertyNameToValueAsString);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        String formattedDate = dateFormat.format(currentDate);
        SimulationOutcome currSimulation = new SimulationOutcome(formattedDate,countId, myWorld.getTermination(),currSimulationInstances);
        pastSimulations.put(countId++, currSimulation);
        return currSimulation.createSimulationOutcomeDTO();
    }
    @Override
    public WorldDTO getWorldDTO() {
         return myWorld.createWorldDTO();
    }
    @Override
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        myReader.readWorldFromXml(XML_PATH, JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
    }
    @Override
    public SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber) {
        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
    }




}
