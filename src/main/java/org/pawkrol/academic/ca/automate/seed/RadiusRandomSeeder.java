package org.pawkrol.academic.ca.automate.seed;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

import java.util.Random;

/**
 * Created by pawkrol on 2017-05-18.
 */
public class RadiusRandomSeeder implements Seeder {

    private Grid markGrid;
    private int freeCells;

    @Override
    public void seed(Strategy strategy, Grid grid, int... args) {
        int n = args[0];
        int r = args[1];

        markGrid = new Grid(grid.getWidth(), grid.getHeight(), grid.isCyclic());
        markGrid.forEach(cell -> cell.setState(0));
        freeCells = markGrid.getWidth() * markGrid.getHeight();

        Random random = new Random();

        int x, y;
        Cell c;
        for (int i = 0; i < n; ) {
            x = random.nextInt(grid.getWidth());
            y = random.nextInt(grid.getHeight());
            c = markGrid.getCell(x, y);

            if (!checkInRadius(c)){
                markCells(c, r);
                i++;
            }

            if (freeCells <= 0) break;
        }

        grid.forEach( cell -> {
            Cell mc = markGrid.getCell(cell.getX(), cell.getY());
            int state = mc.getState();

            if (state == 2) {
                strategy.switchState(cell);
            }
        });
    }

    private boolean checkInRadius(Cell c) {
        return c.getState() == 1 || c.getState() == 2;
    }

    private void markCells(Cell mc, int r) {
        markGrid.forEach( c -> {
            double dist = Math.sqrt( (c.getX() - mc.getX()) * (c.getX() - mc.getX())
                    + (c.getY() - mc.getY()) * (c.getY() - mc.getY()) );

            if (dist <= r && c.getState() != 2 && c.getState() != 1) {
                c.setState(1);
                freeCells--;
            }
        });

        mc.setState(2);
    }

    @Override
    public String toString() {
        return "Radius Random";
    }
}
