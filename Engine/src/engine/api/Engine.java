package engine.api;

import engine.SimulationOutcome;

public interface Engine {

    SimulationOutcome runNewSimulation();
    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);

    // TODO: 10/08/2023 WorldDTO getWorldDTO();
    // TODO: 10/08/2023 SimulationOutComeDTO getSimulationOutComeDTO();
}
