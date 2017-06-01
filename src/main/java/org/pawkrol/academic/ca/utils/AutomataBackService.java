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

    public void init(){
        iteration = 0;
    }

    public void setAutomataResolver(AutomataResolver automataResolver) {
        this.automataResolver = automataResolver;
    }

    public int getIteration() {
        return iteration;
    }

    public boolean isFinished(){
        return automataResolver.getStrategy().isFinished();
    }

    @Override
    protected Task<Grid> createTask() {
        return new Task<Grid>() {
            @Override
            protected Grid call() throws Exception {
                if ( isFinished() ) cancel();
                iteration++;

                return automataResolver.makeStep();
            }
        };
    }
}
