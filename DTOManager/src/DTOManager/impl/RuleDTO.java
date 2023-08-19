package DTOManager.impl;

import java.util.List;

public class RuleDTO {

    private final String name;
    private Integer ticks;
    private Double probability;
    private final List<String> actionsNames;

    public RuleDTO(String name, Integer ticks, Double probability, List<String> actionsNames) {
        this.name = name;
        this.ticks = ticks;
        this.probability = probability;
        this.actionsNames = actionsNames;
    }

    public String getName() {
        return name;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }

    public List<String> getActionsNames() {
        return actionsNames;
    }
}
