package engine.world.rule.Condition;

import schema.generated.PRDActivation;

public class Activation {
    private int ticks = 0;
    private float probability = 1;

    public Activation(PRDActivation prdActivation){
        if(prdActivation.getTicks() != null){
            ticks = prdActivation.getTicks();
        }
        if (prdActivation.getProbability() != null){
            probability = prdActivation.getProbability().floatValue();
        }
    }

    public boolean toActivate() {
        return true;
    }
}
