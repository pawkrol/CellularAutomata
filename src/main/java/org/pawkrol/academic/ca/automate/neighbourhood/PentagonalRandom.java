package org.pawkrol.academic.ca.automate.neighbourhood;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;

import java.util.List;
import java.util.Random;

/**
 * Created by pawkrol on 5/5/17.
 */
public class PentagonalRandom extends Neighbourhood{

    private Random random;

    private PentagonalLeft pentagonalLeft;
    private PentagonalRight pentagonalRight;
    private PentagonalBottom pentagonalBottom;
    private PentagonalTop pentagonalTop;

    public PentagonalRandom(){
        random = new Random();

        pentagonalLeft = PentagonalLeft.getInstace();
        pentagonalRight = PentagonalRight.getInstace();
        pentagonalBottom = PentagonalBottom.getInstace();
        pentagonalTop = PentagonalTop.getInstance();
    }

    @Override
    public List<Cell> neighbours(Grid grid, Cell cell) {
        int dir = random.nextInt(4);
        List<Cell> cells;

        if (dir == 0) {
            cells = pentagonalLeft.neighbours(grid, cell);
        } else if (dir == 1) {
            cells = pentagonalRight.neighbours(grid, cell);
        } else if (dir == 2) {
            cells = pentagonalBottom.neighbours(grid, cell);
        } else {
            cells = pentagonalTop.neighbours(grid, cell);
        }

        return cells;
    }

    @Override
    public String toString() {
        return "Pentagonal Random";
    }
}
