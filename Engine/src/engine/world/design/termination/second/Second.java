package engine.world.design.termination.second;

public interface Second {

    void setSeconds(Integer seconds);

    Integer getSeconds();
    public boolean isTerminateReason();
    public void setTerminateReason(boolean terminateReason);
}
