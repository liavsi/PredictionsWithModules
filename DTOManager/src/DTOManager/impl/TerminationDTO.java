package DTOManager.impl;

public class TerminationDTO {
    private int ticks;
    private int secondsToPast;

    public TerminationDTO(int ticks,int secondsToPast) {
        this.ticks = ticks;
        this.secondsToPast = secondsToPast;
    }

    public int getTicks() {
        return ticks;

    }
    public int getSecondsToPast() {
        return secondsToPast;
    }
}
