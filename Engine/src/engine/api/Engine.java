package engine.api;

import engine.SimulationOutcome;
import DTOManager.impl.WorldDTO;
public interface Engine {


    int runNewSimulation();
    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);
    WorldDTO getWorldDTO();
    SimulationOutcome getPastSimulationDTO(int wantedSimulationNumber);
    // TODO: 10/08/2023 SimulationOutComeDTO getSimulationOutComeDTO();
}
