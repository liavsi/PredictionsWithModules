package engine.impl;

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
    private World myWorld;
   // private WorldDTO myWorldDTO;
    private Integer countId = 0;
    private Map<Integer, SimulationOutcome> pastSimulations;

    public World getWorld() {
        return myWorld;
    }

//    public WorldDTO createWorldDTO() {
//        Map<String, EntityDefinition> entityDefinitionMap = new HashMap<>();
//        for (EntityDefinition entityDefinition : myWorld.getNameToEntityDefinition().values()) {
////            EntityDefinition currEntity = entityDefinition.clone();
//////            for (PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
//////                PropertyDefinition newProp = new PropertyDefinition();
//////
//////
//////            }
//////            String entityName = prdEntity.getName();
//////            entities.put(entityName, currEntity);
//////        }
//        }
//        return null;
//    }

    public EngineImpl() {
        myReader = new ReaderImpl();
        pastSimulations = new HashMap<>();
    }

    @Override
    public int runNewSimulation(Map<String, Object> propertyNameToValueAsString) {
        myReader.readEnvironmentPropertiesFromUser(propertyNameToValueAsString);
        SimulationOutcome currSimulation = myWorld.runSimulation(propertyNameToValueAsString);
        pastSimulations.put(countId++, currSimulation);
        //return currSimulation.converToDTO();
        return countId;
    }

    @Override
    public WorldDTO getWorldDTO() {
         return myWorld.createWorldDTO();
    }

    @Override
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        myWorld = new WorldImpl();
        myReader.readWorldFromXml(XML_PATH, JAXB_XML_PACKAGE_NAME);
    }

    @Override
    public SimulationOutcome getPastSimulationDTO(int wantedSimulationNumber) {
        return null;
    }




}
