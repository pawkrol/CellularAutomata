package org.pawkrol.academic.ca.automate.strategy;

import javafx.scene.paint.Color;
import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.utils.ColorHelper;

import java.util.List;

/**
 * Created by pawkrol on 4/30/17.
 */
public class Fredkin implements Strategy {

    private final int DEAD = 0;
    private final int ALIVE = 1;

    @Override
    public void init(Grid grid) {
        grid.forEach(c -> c.setState(DEAD));

        ColorHelper.setColor(DEAD, Color.BLACK);
        ColorHelper.setColor(ALIVE, Color.YELLOW);
    }

    @Override
    public void evaluate(Grid grid, Neighbourhood neighbourhood) {
        Grid prevGrid = new Grid(grid);

        grid.forEach( c -> {
            List<Cell> neighbours = neighbourhood.neighbours(prevGrid, c);
            boolean odd = (countStateAlive(neighbours) % 2) == 1;

            if (odd) c.setState(ALIVE);
            else c.setState(DEAD);
        });

    }

    @Override
    public void seed(Grid grid, int amount) {

    }

    private int countStateAlive(List<Cell> cells){
        int counter = 0;
        for(Cell c: cells){
            if (c.getState() == ALIVE) counter++;
        }
        return counter;
    }

    @Override
    public void switchState(Cell cell) {
        if (cell.getState() == ALIVE)
            cell.setState(DEAD);
        else
            cell.setState(ALIVE);
    }

    @Override
    public String toString() {
        return "Fredkin";
    }
}
