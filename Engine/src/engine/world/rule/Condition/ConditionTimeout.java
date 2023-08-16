package engine.world.rule.Condition;

public class ConditionTimeout implements ConditionEnd{

    private final int secondsToEnd;
    private long realTime;
    private final long startTime;

    public ConditionTimeout(int seconds)
    {
        secondsToEnd = seconds;
        realTime = System.currentTimeMillis()/1000;
        startTime = System.currentTimeMillis()/1000;
    }
    @Override
    public boolean isConditionTrue() {

        return (realTime - startTime > secondsToEnd);
    }
    @Override
    public void updateConditionVars() {

        realTime = System.currentTimeMillis()/1000;
    }
}
