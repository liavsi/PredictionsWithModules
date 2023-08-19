package engine.api;

import DTOManager.impl.SimulationOutcomeDTO;
import engine.SimulationOutcome;
import DTOManager.impl.WorldDTO;

import java.util.Map;

public interface Engine {


    SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString);
    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);
    WorldDTO getWorldDTO();
    SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber);
    // TODO: 10/08/2023 SimulationOutComeDTO getSimulationOutComeDTO();
}
