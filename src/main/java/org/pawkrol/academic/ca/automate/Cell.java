package org.pawkrol.academic.ca.automate;

/**
 * Created by pawkrol on 4/28/17.
 */
public class Cell {

    private int x;
    private int y;

    private int state;
    private double ro;
    private boolean recrystallized;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public boolean isRecrystallized() {
        return recrystallized;
    }

    public void setRecrystallized(boolean recrystallized) {
        this.recrystallized = recrystallized;
    }
}
