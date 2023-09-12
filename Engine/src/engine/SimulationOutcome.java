package engine;

import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.termination.api.Termination;

import java.util.ArrayList;

public class SimulationOutcome {
    private final String runDate;
    private final int id;
    private final Termination termination;
    private final EntityInstanceManager entityInstanceManager;
    private final EntityInstanceManagerDTO startPosition;
    private final ActiveEnvironment activeEnvironment;

    public SimulationOutcome(String runDate, int id, Termination termination, EntityInstanceManager entityInstanceDTOMap, EntityInstanceManagerDTO startPosition, ActiveEnvironment activeEnvironment) {
        this.runDate = runDate;
        this.id = id;
        this.termination = termination;
        this.entityInstanceManager = entityInstanceDTOMap;
        this.startPosition = startPosition;
        this.activeEnvironment = activeEnvironment;
    }
    public SimulationOutcomeDTO createSimulationOutcomeDTO(){
        return new SimulationOutcomeDTO(runDate,id,termination.createTerminationDTO(), entityInstanceManager.createDTO());
    }

    public String getRunDate() {
        return runDate;
    }

    public int getId() {
        return id;
    }

    public Termination getTermination() {
        return termination;
    }

    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    public EntityInstanceManagerDTO getStartPosition() {
        return startPosition;
    }

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }
//     private TerminationReason terminationReason;


    //private List<Entity> EntityData; //To Do!! change to EntityData class

}
