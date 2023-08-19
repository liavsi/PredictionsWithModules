package DTOManager.impl;

import java.util.Date;

public class SimulationOutcomeDTO {

    private final String runDate;
    private final int id;
    private final TerminationDTO terminationDTO;

    public SimulationOutcomeDTO(String runDate, int id, TerminationDTO terminationDTO) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
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
