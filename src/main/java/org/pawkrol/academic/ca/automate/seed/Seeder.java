package org.pawkrol.academic.ca.automate.seed;

import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

/**
 * Created by pawkrol on 5/5/17.
 */
public interface Seeder {
    void seed(Strategy strategy, Grid grid, int... args);
}
