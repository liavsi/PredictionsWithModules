package DTOManager.impl;

import java.awt.*;
import java.util.Map;

public class SimulationOutcomeDTO {

    private final String runDate;
    private final int id;
    private final TerminationDTO terminationDTO;
    private final EntityInstanceManagerDTO entityInstanceDTOS;

    public boolean isPause() {
        return isPause;
    }

    public boolean isStop() {
        return isStop;
    }

    private  boolean isPause;
    private  boolean isStop;
    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, EntityInstanceManagerDTO dto, boolean isPause, boolean isStop) {
        this.isStop = isStop;
        this.isPause = isPause;
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        entityInstanceDTOS = dto;
    }

    public EntityInstanceManagerDTO getEntityInstanceDTOS() {
        return entityInstanceDTOS;
    }

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, EntityInstanceManagerDTO entityInstanceDTOS) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        this.entityInstanceDTOS = entityInstanceDTOS;
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
