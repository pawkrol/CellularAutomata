package org.pawkrol.academic.ca.automate.strategy;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;
import org.pawkrol.academic.ca.automate.neighbourhood.Neighbourhood;

import java.util.List;
import java.util.Random;

/**
 * Created by pawkrol on 2017-05-31.
 */
public class Recrystallization implements Strategy {

    private final double A = 86710969050178.5;
    private final double B = 9.41268203527779;

    private double roPrev;
    private double roSum;

    private long t;
    private int k = 10000;

    private boolean finished;
    private boolean changed;

    private NaiveSeedGrowth naiveSeedGrowth;
    private Grid startGrid;

    @Override
    public void init(Grid grid) {
        t = 0;
        roPrev = getRo(0);
        startGrid = new Grid(grid);
        finished = false;

        naiveSeedGrowth = NaiveSeedGrowth.getInstance();

        grid.forEach(c -> c.setRecrystallized(false) );
    }

    @Override
    public void evaluate(Grid grid, Neighbourhood neighbourhood) {
        t++;

        Grid prevGrid = new Grid(grid);

        double ro = getRo(t);
        double roCell = (ro - roPrev) / grid.getSize();
        double roCrit = getRo(65) / grid.getSize();
        roSum = 0;
        roPrev = ro;

        changed = false;

        grid.forEach(c -> {
            if (c.isRecrystallized()) return;

            List<Cell> neighbours = neighbourhood.neighbours(prevGrid, c);

            for (Cell cn: neighbours) {
                if (cn.isRecrystallized()){
                    c.setState(getMostFrequentState(neighbours));
                    c.setRo(1);
                    c.setRecrystallized(true);

                    changed = true;
                    break;
                }
            }
        });

        grid.forEach(c -> {
            List<Cell> neighbours = neighbourhood.neighbours(startGrid, c);

            if (isOnBorder(neighbours)) {
                c.setRo( c.getRo() + 0.8 * roCell);
                roSum += 0.2 * roCell;
            } else {
                c.setRo( c.getRo() + 0.2 * roCell);
                roSum += 0.8 * roCell;
            }

        });

        addToRandomOnBorder(grid, startGrid, neighbourhood);

        grid.forEach(c -> {
            if (c.isRecrystallized()) return;

            List<Cell> neighbours = neighbourhood.neighbours(startGrid, c);

            if (c.getRo() > roCrit && isOnBorder(neighbours)){
                int type = naiveSeedGrowth.getNewTypes();

                c.setRo(1);
                c.setRecrystallized(true);
                c.setState(type);

                changed = true;

                neighbours = neighbourhood.neighbours(grid, c);

                for ( Cell cn: neighbours ){
                    if (startGrid.getCell(cn.getX(), cn.getY()).getState() ==
                            startGrid.getCell(c.getX(), c.getY()).getState()) {
                        cn.setRo(1);
                        cn.setRecrystallized(true);
                        cn.setState(type);
                    }
                }
            }
        });

     //   if (!changed) finished = true;
    }

    @Override
    public void switchState(Cell cell) {
        cell.setState(naiveSeedGrowth.getNewTypes());
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public int getMostFrequentState(List<Cell> neighbours) {
        int[] freq = new int[naiveSeedGrowth.getTypes() + 1];
        Random random = new Random();

        int max = 0;
        int mostState = 0;
        for (Cell c : neighbours) {
            int state = c.getState();

            freq[state]++;

            if (freq[state] > max && c.isRecrystallized()) {
                max = freq[state];
                mostState = state;
            } else if (freq[state] == max && c.isRecrystallized()) {
                if (random.nextDouble() > .5) {
                    max = freq[state];
                    mostState = state;
                }
            }
        }

        return mostState;
    }

    private boolean isOnBorder(List<Cell> neighbours){
        Cell c = neighbours.get(0);
        for (int i = 1; i < neighbours.size(); i++) {
            if (c.getState() != neighbours.get(i).getState()) {
                return true;
            }
        }

        return false;
    }

    private void addToRandomOnBorder(Grid grid, Grid startGrid, Neighbourhood neighbourhood) {
        double roAdd = roSum / k;
        Random random = new Random();

        for (int i = 0; i < k; ) {
            Cell c = grid.getCell(
                    random.nextInt(grid.getWidth()),
                    random.nextInt(grid.getHeight())
            );

            List<Cell> neighbours = neighbourhood.neighbours(startGrid, c);
            if (isOnBorder(neighbours)) {
                c.setRo( c.getRo() + roAdd );
                i++;
            }
        }
    }

    private double getRo(long t){
        return (A / B) + ( (1.0 - (A / B) ) * Math.exp(-B * ( (double)t / 1000.0)) );
    }

    @Override
    public String toString() {
        return "Recrystallization";
    }
}
