package engine;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import engine.world.design.termination.api.Termination;

import java.util.Map;

public class SimulationOutcome {
    private final String runDate;
    private final int id;
    private final Termination termination;
    private final Map<Integer, EntityInstanceManagerDTO> entityInstancDTOS;

    public SimulationOutcome(String runDate, int id, Termination termination, Map<Integer, EntityInstanceManagerDTO> entityInstanceDTOMap) {
        this.runDate = runDate;
        this.id = id;
        this.termination = termination;
        this.entityInstancDTOS = entityInstanceDTOMap;
    }
    public SimulationOutcomeDTO createSimulationOutcomeDTO(){
        return new SimulationOutcomeDTO(runDate,id,termination.createTerminationDTO(), entityInstancDTOS);
    }


//     private TerminationReason terminationReason;


    //private List<Entity> EntityData; //To Do!! change to EntityData class

}
