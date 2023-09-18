package engine.world.design.termination.byuser;

public class ByUser {
    boolean isTerminationReason = false;



    public void setTerminationReason(boolean terminationReason) {
        isTerminationReason = terminationReason;
    }

    public boolean isTerminationReason() {
        return isTerminationReason;
    }
}
