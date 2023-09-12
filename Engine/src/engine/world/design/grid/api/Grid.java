package engine.world.design.grid.api;

import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.grid.cell.Cell;
import engine.world.design.grid.cell.Coordinate;

import java.util.Random;

public interface Grid {

    public void initEntityPlace(EntityInstance entityInstance);
    public void moveEntity(EntityInstance entityInstance);

}
