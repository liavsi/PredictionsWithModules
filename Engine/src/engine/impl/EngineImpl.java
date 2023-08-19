package engine.impl;

import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.world.api.World;
import engine.world.design.world.impl.WorldImpl;
import engine.world.design.reader.api.Reader;
import engine.world.design.reader.impl.ReaderImpl;

import java.util.HashMap;
import java.util.Map;

// TODO: 10/08/2023 After deleting old World Change to World

public class EngineImpl implements Engine {

    private Reader myReader;
    private World myWorld; // TODO: 19/08/2023 static?
    private Integer countId = 0;
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
        SimulationOutcome currSimulation = myWorld.runSimulation(propertyNameToValueAsString,countId++);
        pastSimulations.put(countId++, currSimulation); // two times ++
        return currSimulation.createSimulationOutcomeDTO();
    }
    @Override
    public WorldDTO getWorldDTO() {
         return myWorld.createWorldDTO();
    }
    @Override
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        myWorld = new WorldImpl();
        myReader.readWorldFromXml(XML_PATH, JAXB_XML_PACKAGE_NAME);
        myWorld = myReader.getWorld();
    }
    @Override
    public SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber) {
        return pastSimulations.get(wantedSimulationNumber).createSimulationOutcomeDTO();
    }




}
