package engine.world.design.termination.api;

import DTOManager.impl.TerminationDTO;

import java.time.Duration;

public interface Termination {

    // start the clock of the Termination object
    void startTerminationClock();

    // checks if ticks passed the termination object ticks or
    // the time in seconds from the start has passed
    Boolean isTerminated(Integer currentTicks,boolean isUserStop);
    TerminationDTO createTerminationDTO();
    public void reduceWaitTime(Duration waitTime);
    //Object getTerminateReason();

}
