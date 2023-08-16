package engine.world.property;

public class RandomInitializer {

    private boolean isRandomInitialized = false; //default value
    private final Object initValue; // TODO: 03/08/2023 or string(?)

    public RandomInitializer(boolean isRandomInitialized, String value){
        this.isRandomInitialized = isRandomInitialized;
        this.initValue = value;
    }

    @Override
    public String toString() {
        String s = "\nIs Random Initialized: " + isRandomInitialized;
        if(!isRandomInitialized)
            s += initValue.toString();
        return s;
    }

    public boolean getIsRandomInitialized() {
        return isRandomInitialized;
    }
    public  Object getInitValue(){
        return initValue;
    }
}
