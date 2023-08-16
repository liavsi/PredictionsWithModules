package engine.world.property;

public class Restriction {

    private float from;
    private float to;

    public Restriction(double from, double to) {
        this.from = (float) from;
        this.to = (float) to;
    }

    public float getFrom(){
        return from;
    }

    public float getTo() {
        return to;
    }
}
