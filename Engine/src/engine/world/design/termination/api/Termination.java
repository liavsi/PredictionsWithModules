package engine.world.design.termination.api;

import DTOManager.impl.TerminationDTO;

public interface Termination {

    // start the clock of the Termination object
    void startTerminationClock();

    // checks if ticks passed the termination object ticks or
    // the time in seconds from the start has passed
    Boolean isTerminated(Integer currentTicks);
    TerminationDTO createTerminationDTO();
    //Object getTerminateReason();

}
