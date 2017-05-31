package org.pawkrol.academic.ca.automate.strategy;

import javafx.scene.paint.Color;
import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.utils.ColorHelper;

import java.util.List;
import java.util.Random;

/**
 * Created by pawkrol on 2017-05-18.
 */
public class NaiveSeedGrowth implements Strategy {

    private int types;

    @Override
    public void init(Grid grid) {
        types = 0;
        ColorHelper.setColor(types, Color.WHITE);
    }

    @Override
    public void evaluate(Grid grid, Neighbourhood neighbourhood) {
        Grid prevGrid = new Grid(grid);

        grid.forEach( c -> {
            List<Cell> neighbours = neighbourhood.neighbours(prevGrid, c);

            if (c.getState() == 0 && anyNeighbourIsSeed(neighbours)) {
                int state = getMostFrequentState(neighbours);
                c.setState(state);
            }
        });
    }

    @Override
    public void switchState(Cell cell) {
        if (cell.getState() == 0) {
            cell.setState(++types);
        }
    }

    private boolean anyNeighbourIsSeed(List<Cell> neighbours) {
        for (Cell c : neighbours) {
            if (c.getState() != 0)
                return true;
        }

        return false;
    }

    private int getMostFrequentState(List<Cell> neighbours) {
        int[] freq = new int[types + 1];
        Random random = new Random();

        int max = 0;
        int mostState = 0;
        for (Cell c: neighbours) {
            int state = c.getState();

            freq[state]++;

            if (freq[state] > max && state != 0) {
                max = freq[state];
                mostState = state;
            } else if (freq[state] == max && state != 0) {
                if (random.nextDouble() > .5) {
                    max = freq[state];
                    mostState = state;
                }
            }
        }

        return mostState;
    }

    @Override
    public String toString() {
        return "Naive Seed Growth";
    }
}
