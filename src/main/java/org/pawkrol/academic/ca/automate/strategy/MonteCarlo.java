package org.pawkrol.academic.ca.automate.strategy;

import javafx.scene.paint.Color;
import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.utils.ColorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pawkrol on 2017-06-01.
 */
public class MonteCarlo implements Strategy {

    private Random random;

    @Override
    public void init(Grid grid) {
        ColorHelper.setColor(0, Color.WHITE);
        random = new Random();

        grid.forEach(c -> c.setRecrystallized(false));
    }

    @Override
    public void evaluate(Grid grid, Neighbourhood neighbourhood) {
        List<Cell> neighbours;
        Cell c;

        for (int i = 0; i < grid.getSize(); i++) {
            int cx = random.nextInt(grid.getWidth());
            int cy = random.nextInt(grid.getHeight());

            c = grid.getCell(cx, cy);
            neighbours = neighbourhood.neighbours(grid, c);

            int prevE = getEnergy(c.getState(), neighbours);
//            int newState = random.nextInt(grid.getStates());
            int newState = getNewState(neighbours);
            int newE = getEnergy(newState, neighbours);

            if (newE - prevE <= 0) {
                c.setState(newState);
            }
        }
    }

    @Override
    public void switchState(Cell cell) {

    }

    private int getEnergy(int cellState, List<Cell> neighbours) {
        int buff = 0;
        for (Cell nc: neighbours) {
            if (nc.getState() != cellState) {
                buff++;
            }
        }

        return buff;
    }

    private int getNewState(List<Cell> neighbours) {
        ArrayList<Integer> states = new ArrayList<>();
        for (Cell c: neighbours) {
            states.add(c.getState());
        }

        return states.get( random.nextInt( states.size() ) );
    }

    private boolean isOnBorder(List<Cell> neighbours){
        Cell c = neighbours.get(0);
        for (int i = 1; i < neighbours.size(); i++) {
            if (c.getState() != neighbours.get(i).getState()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Monte Carlo";
    }
}
