package engine;

import DTOManager.impl.TerminationDTO;

import java.util.Date;

public class SimulationOutcome {
    private final Date runDate;
    private final int id;
    private final TerminationDTO terminationDTO;

    public SimulationOutcome(Date runDate, int id, TerminationDTO terminationDTO) {
        this.runDate = runDate;
        this.id = id;
        this.terminationDTO = terminationDTO;
    }


//     private TerminationReason terminationReason;


    //private List<Entity> EntityData; //To Do!! change to EntityData class

}
