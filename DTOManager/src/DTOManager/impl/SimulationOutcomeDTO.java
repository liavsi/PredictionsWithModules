package DTOManager.impl;

import java.awt.*;
import java.util.Map;

public class SimulationOutcomeDTO {

    private final String runDate;
    private final int id;
    private final TerminationDTO terminationDTO;
    private final EntityInstanceManagerDTO entityInstanceDTOS;

    public EntityInstanceManagerDTO getEntityInstanceDTOS() {
        return entityInstanceDTOS;
    }

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, EntityInstanceManagerDTO entityInstancDTOS) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        this.entityInstanceDTOS = entityInstancDTOS;
    }

    public String getRunDate() {
        return runDate;
    }

    public int getId() {
        return id;
    }

    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }
}
