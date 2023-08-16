package engine.world.design.termination.tick.impl;

import engine.world.design.termination.tick.api.Tick;

public class TickImpl implements Tick {

    private Integer ticks;

    public TickImpl(Integer ticks) {
        this.ticks = ticks;
    }



    @Override
    public Integer getTicks() {
        return ticks;
    }

    public void setTicks(Integer ticks) {
        this.ticks = ticks;
    }
}
