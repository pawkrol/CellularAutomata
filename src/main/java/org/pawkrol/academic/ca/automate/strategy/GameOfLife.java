package org.pawkrol.academic.ca.automate.strategy;

import javafx.scene.paint.Color;
import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.utils.ColorHelper;

import java.util.List;
import java.util.Random;

public class GameOfLife implements Strategy {

    private final int DEAD = 0;
    private final int ALIVE = 1;

    @Override
    public void init(Grid grid) {
        grid.forEach(this::initCell);

        ColorHelper.setColor(DEAD, Color.BLACK);
        ColorHelper.setColor(ALIVE, Color.GREENYELLOW);
    }

    @Override
    public void evaluate(Grid grid, Neighbourhood neighbourhood) {
        Grid prevGrid = new Grid(grid);

        grid.forEach( c -> {
            List<Cell> neighbours = neighbourhood.neighbours(prevGrid, c);
            long alive = countState(neighbours, ALIVE);
            int state = c.getState();

            if (state == DEAD) {
                if (alive == 3) c.setState(ALIVE);
            }

            if (state == ALIVE) {
                if (alive > 3 || alive < 2) c.setState(DEAD);
            }
        });
    }

    @Override
    public void switchState(Cell cell) {
        if (cell.getState() == ALIVE)
            cell.setState(DEAD);
        else
            cell.setState(ALIVE);
    }

    private long countState(List<Cell> cells, int state){
        return cells.stream()
                .filter(c -> c.getState() == state)
                .count();
    }

    private void initCell(Cell c){
        c.setState(DEAD);
    }

    @Override
    public String toString() {
        return "Game of Life";
    }

}
