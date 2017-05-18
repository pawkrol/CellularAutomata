package org.pawkrol.academic.ca.automate;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;
import org.pawkrol.academic.ca.automate.seed.Seeder;
import org.pawkrol.academic.ca.automate.strategy.Strategy;

public class AutomataResolver extends Service<Grid>{

    private Strategy strategy;
    private Neighbourhood neighbourhood;
    private Grid grid;
    private Seeder seeder;

    private int steps;
    private int iteration;

    public void init() throws Exception{
        if (grid == null) throw new Exception("Grid not set");
        if (strategy == null) throw new Exception("Strategy not set");
        if (neighbourhood == null) throw new Exception("Neighbourhood not set");

        iteration = 0;

        strategy.init(grid);
    }

    public void seed(int n, int r) throws Exception{
        if (grid == null) throw new Exception("Grid not set");
        if (strategy == null) throw new Exception("Strategy not set");

        if (seeder != null) {
            seeder.seed(strategy, grid, n, r);
        }
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

    public void setSeeder(Seeder seeder) {
        this.seeder = seeder;
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

    public int getIteration() {
        return iteration;
    }

    @Override
    protected Task<Grid> createTask() {
        return new Task<Grid>() {
            @Override
            protected Grid call() throws Exception {
                for (int s = 0; s < steps; s++) {
                    strategy.evaluate(grid, neighbourhood);
                    iteration++;
                }

                return grid;
            }
        };
    }
}
