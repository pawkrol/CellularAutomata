package org.pawkrol.academic.ca.automate.neighbourhood;

import org.pawkrol.academic.ca.automate.Cell;
import org.pawkrol.academic.ca.automate.Grid;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pawkrol on 5/5/17.
 */
public class PentagonalRight extends Neighbourhood{

    private static PentagonalRight pentagonalRight;

    private PentagonalRight(){}

    @Override
    public List<Cell> neighbours(Grid grid, Cell cell) {
        int cx = cell.getX();
        int cy = cell.getY();

        List<Cell> cells = new LinkedList<>();
        addCellToList( cells, grid.getCell(cx + 1, cy - 1) );
        addCellToList( cells, grid.getCell(cx, cy - 1) );
        addCellToList( cells, grid.getCell(cx + 1, cy) );
        addCellToList( cells, grid.getCell(cx, cy + 1) );
        addCellToList( cells, grid.getCell(cx + 1, cy + 1) );

        return cells;
    }

    public static PentagonalRight getInstace(){
        if (pentagonalRight == null) {
            pentagonalRight = new PentagonalRight();
        }

        return pentagonalRight;
    }

    @Override
    public String toString() {
        return "Pentagonal Right";
    }
}
