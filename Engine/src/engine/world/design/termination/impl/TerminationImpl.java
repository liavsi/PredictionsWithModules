package engine.world.design.termination.impl;

import DTOManager.impl.TerminationDTO;
import engine.world.design.termination.api.Termination;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.tick.api.Tick;

import java.time.Duration;
import java.time.Instant;

public class TerminationImpl implements Termination {

    private Tick ticks = null;
    private Second secondsToPast = null;
    private Instant startTime;
    private Object terminateReason = null;

    public TerminationImpl() {
    }

    public void setTicks(Tick ticks) {
        this.ticks = ticks;
    }

    public void setSecondsToPast(Second secondsToPast) {
        this.secondsToPast = secondsToPast;
    }
    @Override
    public TerminationDTO createTerminationDTO(){
        return new TerminationDTO(ticks.getTicks(),secondsToPast.getSeconds());
    }


    @Override
    public Boolean isTerminated(Integer currentTicks) {
        boolean isTerminate = false;
        if(secondsToPast != null){
            Instant currentTime = Instant.now();
            Duration elapsed = Duration.between(startTime, currentTime);
            if (elapsed.getSeconds() >= secondsToPast.getSeconds()) {
                isTerminate = true;
                terminateReason = secondsToPast;
            }
        }
        if (ticks != null) {
            if(currentTicks >= ticks.getTicks()) {
                isTerminate = true;
                terminateReason = ticks;
            }
        }
        return  isTerminate;
    }

    public Object getTerminateReason() {
        return terminateReason;
    }

    @Override
    public void startTerminationClock() {
        startTime = Instant.now();
    }
}
