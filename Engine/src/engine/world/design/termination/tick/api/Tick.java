package engine.world.design.termination.tick.api;

public interface Tick {

    void setTicks(Integer ticks);
    Integer getTicks();
    public boolean isTerminateReason();
    public void setTerminateReason(boolean terminateReason);

}
