package engine.world.design.rule.activation.api;

public interface Activation {
    Boolean isActive(int tickNumber);
    void setTick(Integer ticks);

    void setTicks(Integer ticks);

    void setProbability(Float probability);
}
