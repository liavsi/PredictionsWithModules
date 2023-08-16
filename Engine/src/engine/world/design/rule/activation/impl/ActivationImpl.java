package engine.world.design.rule.activation.impl;

import engine.world.design.definition.value.generator.random.impl.numeric.RandomFloatGenerator;
import engine.world.design.rule.activation.api.Activation;

public class ActivationImpl implements Activation {

    private Integer ticks = 1;
    private Float probability = 1f;
    private RandomFloatGenerator randomFloatGenerator = new RandomFloatGenerator(0f, 1f);

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }


    @Override
    public Boolean isActive(int tickNumber) {
        if(tickNumber % ticks == 0 && randomFloatGenerator.generateValue() < probability ) {
            return true;
        }
        return false;
    }
}
