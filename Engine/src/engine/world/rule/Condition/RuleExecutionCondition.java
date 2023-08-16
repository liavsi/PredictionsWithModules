package engine.world.rule.Condition;

public class RuleExecutionCondition implements Condition {

    Activation activation;

    @Override
    public boolean isConditionTrue() {
        return activation.toActivate();
    }
}
