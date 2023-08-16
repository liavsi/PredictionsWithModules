package engine.world.design.termination.second;

public class SecondImpl implements Second {

    private Integer seconds;
    public SecondImpl(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @Override
    public Integer getSeconds() {
        return seconds;
    }
}
