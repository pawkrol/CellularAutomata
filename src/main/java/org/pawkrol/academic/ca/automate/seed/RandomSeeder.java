package org.pawkrol.academic.ca.automate.seed;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

import java.util.Random;

/**
 * Created by pawkrol on 2017-05-18.
 */
public class RandomSeeder implements Seeder {

    @Override
    public void seed(Strategy strategy, Grid grid, int... args) {
        int n = args[0];

        Random random = new Random();

        int x, y;
        Cell c;
        for (int i = 0; i < n; i++) {
            x = random.nextInt(grid.getWidth());
            y = random.nextInt(grid.getHeight());
            c = grid.getCell(x, y);

            strategy.switchState(c);
        }
    }

    @Override
    public String toString() {
        return "Random";
    }
}
