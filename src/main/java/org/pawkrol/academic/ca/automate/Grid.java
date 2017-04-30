package org.pawkrol.academic.ca.automate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pawkrol on 4/28/17.
 */
public class Grid {

    private List<Cell> grid;
    private int width;
    private int height;

    private boolean isCyclic;

    public Grid(int width, int height, boolean isCyclic){
        this.width = width;
        this.height = height;
        this.isCyclic = isCyclic;

        initGrid();
    }

    public Grid(Grid other){
        this.width = other.width;
        this.height = other.height;
        this.isCyclic = other.isCyclic;

        this.grid = new ArrayList<>();
        for (Cell c: other.grid){
            Cell nc = new Cell(c.getX(), c.getY());
            nc.setState(c.getState());

            grid.add(nc);
        }
    }

    public void forEach(GridForEach f){
        for (Cell c: grid){
            f.forEach(c);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCell(int x, int y){
        if (isCyclic) {
            return getCellCyclic(x, y);
        } else {
            return getCellNormal(x, y);
        }
    }

    public void setCell(int x, int y, Cell cell){
        grid.set(y * width + x, cell);
    }

    private Cell getCellCyclic(int x, int y){
        if (x >= width) x -= width;
        if (y >= height) y -= height;
        if (x < 0) x += width;
        if (y < 0) y += height;

        return getCellNormal(x, y);
    }

    private Cell getCellNormal(int x, int y){
        if (x >= width || y >= height) return null;
        if (x < 0 || y < 0) return null;

        return grid.get(y * width + x);
    }

    private void initGrid(){
        grid = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                grid.add(new Cell(x, y));
            }
        }
    }
}
