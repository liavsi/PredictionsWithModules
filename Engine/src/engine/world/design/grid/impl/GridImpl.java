package engine.world.design.grid.impl;

import DTOManager.impl.GridDTO;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.grid.api.Grid;
import engine.world.design.grid.cell.Cell;
import engine.world.design.grid.cell.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GridImpl implements Grid {
    private Cell[][] grid;
    private List<Coordinate> freeCells;
    private final int columns;
    private final int rows;

    public GridImpl(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        grid = new Cell[rows][columns];
        freeCells = new ArrayList<>();
        for(int i = 0; i<rows;i++){
            for(int j=0; j<columns; j++){
                Coordinate coordinate = new Coordinate(i,j);
                grid[i][j] = new Cell(null,coordinate);
                freeCells.add(new Coordinate(i,j));
            }
        }
    }

    public GridDTO createGridDTO() {
        return new GridDTO(columns, rows);
    }
    @Override
    public void initEntityPlace(EntityInstance entityInstance){
        Cell freeCell = randomFreeCell();
        entityInstance.setCoordinate(freeCell.getCoordinate());
        freeCell.setEntityInstance(entityInstance);
    }
    @Override
    public List<Coordinate> getFreeCells() {
        return freeCells;
    }

    private Cell randomFreeCell(){
        Random random = new Random();
        int x, y;
        int size = freeCells.size();
        int index = random.nextInt(size);
        Coordinate coordinate = freeCells.get(index);
        freeCells.remove(index);
//        x = random.nextInt(rows);
//        y = random.nextInt(columns);
//        Cell cell = grid[x][y];
//        while(cell.isTaken()){
//            x = random.nextInt(rows);
//            y = random.nextInt(columns);
//            cell = grid[x][y];
//        }
        return grid[coordinate.getX()][coordinate.getY()];
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
        int newY = (cell.getCoordinate().getY() + 1) % rows;
        if (newY < 0) {
            newY += rows;
        }
        return grid[cell.getCoordinate().getX()][newY];
    }

    private Cell downCell(Cell cell){
        int newY = (cell.getCoordinate().getY() - 1) % rows;
        if (newY < 0) {
            newY += rows;
        }
        return grid[cell.getCoordinate().getX()][newY];
    }

    private Cell rightCell(Cell cell){
        int newX = (cell.getCoordinate().getX() + 1) % columns;
        if (newX < 0) {
            newX += columns;
        }
        return grid[newX][cell.getCoordinate().getY()];
    }

    private Cell leftCell(Cell cell){
        int newX = (cell.getCoordinate().getX() - 1) % columns;
        if (newX < 0) {
            newX += columns;
        }
        return grid[newX][cell.getCoordinate().getY()];
    }

    @Override
    public int getColumns() {
        return columns;
    }
    @Override
    public int getRows() {
        return rows;
    }
}
