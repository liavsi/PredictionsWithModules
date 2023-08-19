package engine;

import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.TerminationDTO;
import engine.world.design.termination.api.Termination;

import java.util.Date;

public class SimulationOutcome {
    private final String runDate;
    private final int id;
    private final Termination termination;

    public SimulationOutcome(String runDate, int id, Termination termination) {
        this.runDate = runDate;
        this.id = id;
        this.termination = termination;
    }
    public SimulationOutcomeDTO createSimulationOutcomeDTO(){
        return new SimulationOutcomeDTO(runDate,id,termination.createTerminationDTO());
    }


//     private TerminationReason terminationReason;


    //private List<Entity> EntityData; //To Do!! change to EntityData class

}
