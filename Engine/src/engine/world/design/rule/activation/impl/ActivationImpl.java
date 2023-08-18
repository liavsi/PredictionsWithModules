package engine.world.design.rule.activation.impl;

import engine.world.design.definition.value.generator.random.impl.numeric.RandomFloatGenerator;
import engine.world.design.rule.activation.api.Activation;

public class ActivationImpl implements Activation {

    private Integer ticks = 1;
    private Double probability = 1d;
    private RandomFloatGenerator randomFloatGenerator = new RandomFloatGenerator(0f, 1f);


    public ActivationImpl(){}
    public ActivationImpl(int ticks,double probability) {
        if (ticks != 0){
            this.ticks = ticks;
        }
        this.probability = probability;
    }
    @Override
    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
    @Override
    public void setProbability(Double probability) {
        this.probability = probability;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public double getProbability() {
        return probability;
    }

    @Override
    public Boolean isActive(int tickNumber) {
        if(tickNumber % ticks == 0 && randomFloatGenerator.generateValue() < probability ) {
            return true;
        }
        return false;
    }
}
