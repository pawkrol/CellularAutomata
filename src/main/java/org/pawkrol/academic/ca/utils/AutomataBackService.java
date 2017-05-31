package org.pawkrol.academic.ca.utils;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import org.pawkrol.academic.ca.automate.AutomataResolver;
import org.pawkrol.academic.ca.automate.Grid;

/**
 * Created by pawkrol on 2017-05-18.
 */
public class AutomataBackService extends ScheduledService<Grid> {

    private AutomataResolver automataResolver;

    private int iteration;
    private int steps;

    public void init(){
        iteration = 0;
    }

    public void setAutomataResolver(AutomataResolver automataResolver) {
        this.automataResolver = automataResolver;
    }

    public void setSteps(int steps) {
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
                if (steps != 0 && iteration >= steps) cancel();
                iteration++;

                return automataResolver.makeStep();
            }
        };
    }
}
