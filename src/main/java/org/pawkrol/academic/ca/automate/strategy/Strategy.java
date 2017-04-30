package org.pawkrol.academic.ca.automate.strategy;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;

/**
 * Created by pawkrol on 4/29/17.
 */
public interface Strategy {
    void init(Grid grid);
    void evaluate(Grid grid, Neighbourhood neighbourhood);
    void seed(Grid grid, int amount);
    void switchState(Cell cell);
}
