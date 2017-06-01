package org.pawkrol.academic.ca.automate.seed;

import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

import java.util.Random;

/**
 * Created by pawkrol on 2017-06-01.
 */
public class AllRandomSeeder implements Seeder{

    @Override
    public void seed(Strategy strategy, Grid grid, int... args) {
        int maxStates = args[0];

        Random random = new Random();
        grid.forEach(c -> c.setState(random.nextInt(maxStates) + 1));

        grid.setStates(maxStates);
    }

    @Override
    public String toString() {
        return "All Random";
    }
}
