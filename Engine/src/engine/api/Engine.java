package engine.api;

import DTOManager.impl.SimulationOutcomeDTO;
import engine.SimulationOutcome;
import DTOManager.impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

public interface Engine {


    SimulationOutcomeDTO runNewSimulation(Map<String, Object> propertyNameToValueAsString);
    void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME);
    WorldDTO getWorldDTO();
    SimulationOutcomeDTO getPastSimulationDTO(int wantedSimulationNumber);

    Map<Integer, SimulationOutcomeDTO> getPastSimulationMapDTO();

    SimpleStringProperty fileNameProperty();

    // TODO: 10/08/2023 SimulationOutComeDTO getSimulationOutComeDTO();
}
