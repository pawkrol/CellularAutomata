package org.pawkrol.academic.ca.automate;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

/**
 * Created by pawkrol on 4/29/17.
 */
public class AutomataResolver extends Service<Grid>{

    private Strategy strategy;
    private Neighbourhood neighbourhood;
    private Grid grid;
    private int steps;

    public void init() throws Exception{
        if (grid == null) throw new Exception("Grid not set");
        if (strategy == null) throw new Exception("Strategy not set");
        if (neighbourhood == null) throw new Exception("Neighbourhood not set");

        strategy.init(grid);
    }

    public void seed(int amount) throws Exception{
        if (grid == null) throw new Exception("Grid not set");
        if (strategy == null) throw new Exception("Strategy not set");

        strategy.seed(grid, amount);
    }

    public void switchState(Cell c){
        strategy.switchState(c);
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    @Override
    protected Task<Grid> createTask() {
        return new Task<Grid>() {
            @Override
            protected Grid call() throws Exception {
                for (int s = 0; s < steps; s++) {
                    strategy.evaluate(grid, neighbourhood);
                }

                return grid;
            }
        };
    }
}
