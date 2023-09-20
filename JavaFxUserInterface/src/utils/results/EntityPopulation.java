package utils.results;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntityPopulation {
    private final SimpleStringProperty entityName;
    private final SimpleIntegerProperty population;

    public EntityPopulation(String entityName, int population) {
        this.entityName = new SimpleStringProperty(entityName);
        this.population = new SimpleIntegerProperty(population);
    }

    public String getEntityName() {
        return entityName.get();
    }

    public void setEntityName(String name) {
        entityName.set(name);
    }

    public int getPopulation() {
        return population.get();
    }

    public void setPopulation(int count) {
        population.set(count);
    }
}

