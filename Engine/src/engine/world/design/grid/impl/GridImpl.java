package engine.world.design.grid.impl;

import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.grid.api.Grid;
import engine.world.design.grid.cell.Cell;
import engine.world.design.grid.cell.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GridImpl implements Grid {
    private Cell[][] grid;
    private final int columns;
    private final int rows;

    public GridImpl(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        grid = new Cell[rows][columns];
        for(int i = 0; i<rows;i++){
            for(int j=0; j<columns; j++){
                Coordinate coordinate = new Coordinate(i,j);
                grid[i][j] = new Cell(null,coordinate);
            }
        }
    }
    @Override
    public void initEntityPlace(EntityInstance entityInstance){
        Cell freeCell = randomFreeCell();
        entityInstance.setCoordinate(freeCell.getCoordinate());
        freeCell.setEntityInstance(entityInstance);
    }
    private Cell randomFreeCell(){
        Random random = new Random();
        int x, y;
        x = random.nextInt(columns);
        y = random.nextInt(rows);
        Cell cell = grid[x][y];
        while(cell.isTaken()){
            x = random.nextInt(columns);
            y = random.nextInt(rows);
            cell = grid[x][y];
        }
        return cell;
    }
    @Override
    public void moveEntity(EntityInstance entityInstance){
        Cell currCell = grid[entityInstance.getCoordinate().getX()][entityInstance.getCoordinate().getY()];
        Cell upCell = upCell(currCell);
        Cell downCell = downCell(currCell);
        Cell rightCell = rightCell(currCell);
        Cell leftCell = leftCell(currCell);
        if(!upCell.isTaken()){
            upCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(upCell.getCoordinate());
        }else if(!downCell.isTaken()){
            downCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(downCell.getCoordinate());
        } else if (!rightCell.isTaken()) {
            rightCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(rightCell.getCoordinate());
        }else if(!leftCell.isTaken()){
            leftCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(leftCell.getCoordinate());
        }
    }
    private Cell upCell(Cell cell){
        return grid[cell.getCoordinate().getX()][(cell.getCoordinate().getY() + 1) % rows];
    }
    private Cell downCell(Cell cell){
        return grid[cell.getCoordinate().getX()][(cell.getCoordinate().getY() - 1) % rows];
    }
    private Cell rightCell(Cell cell){
        return grid[(cell.getCoordinate().getX()+1) % columns][cell.getCoordinate().getY()];
    }
    private Cell leftCell(Cell cell){
        return grid[(cell.getCoordinate().getX() - 1) % columns][cell.getCoordinate().getY()];
    }
}
