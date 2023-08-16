package engine.world.rule.Condition;

public class ConditionTicks implements ConditionEnd{

    private final int ticksToEnd;
    private int ticksPass;

    public ConditionTicks(int numOfTicks){
        ticksToEnd = numOfTicks;
        ticksPass = 0;
    }
    @Override
    public boolean isConditionTrue() {

        return (ticksPass - ticksToEnd >= 0);
    }
    @Override
    public void updateConditionVars(){
        ticksPass += 1;
    }

}
