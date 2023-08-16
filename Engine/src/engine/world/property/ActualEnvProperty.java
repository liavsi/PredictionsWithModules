package engine.world.property;

public class ActualEnvProperty {

    private EnvironmentProperty environmentProperty;
    private Object value;

    public ActualEnvProperty(EnvironmentProperty environmentProperty) {
        this.environmentProperty = environmentProperty;
    }
    public void setActualEnvProperty(Object value) {
        if(value == null){
            value = environmentProperty.getType().randomValue(environmentProperty.restrictions);
        }
        else{
            this.value = value;
        }
    }
}
