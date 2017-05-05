package org.pawkrol.academic.ca.automate.neighbourhood;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;

import java.util.List;
import java.util.Random;

/**
 * Created by pawkrol on 5/5/17.
 */
public class HexagonalRandom extends Neighbourhood{

    private Random random;
    private HexagonalLeft hexagonalLeft;
    private HexagonalRight hexagonalRight;

    public HexagonalRandom() {
        random = new Random();
        hexagonalLeft = HexagonalLeft.getInstance();
        hexagonalRight = HexagonalRight.getInstance();
    }

    @Override
    public List<Cell> neighbours(Grid grid, Cell cell) {
        boolean left = random.nextBoolean();
        List<Cell> cells;

        if (left) {
            cells = hexagonalLeft.neighbours(grid, cell);
        } else {
            cells = hexagonalRight.neighbours(grid, cell);
        }

        return cells;
    }

    @Override
    public String toString() {
        return "Hexagonal Random";
    }
}
