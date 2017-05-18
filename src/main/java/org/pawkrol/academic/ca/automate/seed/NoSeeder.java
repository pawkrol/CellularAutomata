package org.pawkrol.academic.ca.automate.seed;

import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

/**
 * Created by pawkrol on 2017-05-18.
 */
public class NoSeeder implements Seeder {

    @Override
    public void seed(Strategy strategy, Grid grid, int... args) {

    }

    @Override
    public String toString() {
        return "No Seeder";
    }
}
