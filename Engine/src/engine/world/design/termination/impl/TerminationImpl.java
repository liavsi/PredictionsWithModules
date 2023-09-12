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
    //private Object terminateReason = null;

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
        boolean isTicksTerminate = false;
        boolean isSecondsTerminate = false;
        Integer numOfTicks = null;
        Integer numOfSeconds = null;
        if (ticks != null) {
            isTicksTerminate = ticks.isTerminateReason();
            numOfTicks = ticks.getTicks();
        }
        if(secondsToPast != null) {
            isSecondsTerminate = secondsToPast.isTerminateReason();
            numOfSeconds = secondsToPast.getSeconds();
        }
        return new TerminationDTO(numOfTicks,numOfSeconds, isTicksTerminate, isSecondsTerminate);
    }
    @Override
    public Boolean isTerminated(Integer currentTicks) {
        boolean isTerminate = false;
        if(secondsToPast != null){
            Instant currentTime = Instant.now();
            Duration elapsed = Duration.between(startTime, currentTime);
            if (elapsed.getSeconds() >= secondsToPast.getSeconds()) {
                isTerminate = true;
                secondsToPast.setTerminateReason(true);
                //terminateReason = secondsToPast;
            }
        }
        if (ticks != null) {
            if(currentTicks >= ticks.getTicks()) {
                isTerminate = true;
                ticks.setTerminateReason(true);
                //terminateReason = ticks;
            }
        }
        return  isTerminate;
    }

//    public Object getTerminateReason() {
//        return terminateReason;
//    }

    @Override
    public void startTerminationClock() {
        if (ticks != null) {
            ticks.setTerminateReason(false);
        }
        if (secondsToPast != null){
            secondsToPast.setTerminateReason(false);
        }
        startTime = Instant.now();
    }
}
