package DTOManager.impl;

public class TerminationDTO {
    private Integer ticks = null;
    private Integer secondsToPast = null;
    private final boolean isTicksTerminate;
    private final boolean isSecondsTerminate;

    public TerminationDTO(Integer ticks, Integer secondsToPast, boolean isTicksTerminate, boolean isSecondsTerminate) {
        this.ticks = ticks;
        this.secondsToPast = secondsToPast;
        this.isTicksTerminate = isTicksTerminate;
        this.isSecondsTerminate = isSecondsTerminate;
    }
    public Integer getTicks() {
        return ticks;

    }
    public Integer getSecondsToPast() {
        return secondsToPast;
    }

    public boolean isTicksTerminate() {
        return isTicksTerminate;
    }

    public boolean isSecondsTerminate() {
        return isSecondsTerminate;
    }
}
