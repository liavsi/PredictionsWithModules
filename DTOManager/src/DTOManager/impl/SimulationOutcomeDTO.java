package DTOManager.impl;

import java.util.Map;

public class SimulationOutcomeDTO {

    private final String runDate;
    private final int id;
    private final TerminationDTO terminationDTO;
    private final Map<Integer, EntityInstanceManagerDTO> entityInstancDTOS;

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO, Map<Integer, EntityInstanceManagerDTO> entityInstancDTOS) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
        this.entityInstancDTOS = entityInstancDTOS;
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
